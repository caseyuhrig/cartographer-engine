package cartographer.engine.converter;

import cartographer.engine.AggregateDefinition;
import cartographer.engine.Conversion;
import cartographer.engine.Data;
import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.DataType;
import cartographer.engine.Dictionary;
import cartographer.engine.Field;
import cartographer.engine.Map;
import cartographer.engine.Mapping;
import cartographer.engine.Row;
import cartographer.engine.format.TypeFormatter;
import cartographer.engine.io.ProgressBar;

import nuberplex.store.StorableException;
import nuberplex.store.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class DelimitedRollupConverter extends AbstractConverter
{
    private static Logger LOG = LogManager.getLogger(DelimitedRollupConverter.class);


    public DelimitedRollupConverter(final Conversion conversion)
    {
        super(conversion);
    }


    @Override
    public Converter convertData(final ProgressBar progressBar)
            throws Exception
    {
        final Dictionary sourceDictionary = getConversion().getSourceDictionary();
        final Data sourceData = getConversion().getSourceData();
        final Map map = getConversion().getMap();
        final Dictionary targetDictionary = getConversion().getTargetDictionary();
        final Data targetData = getConversion().getTargetData();
        Long lineIndex = 1L;
        final Row defaultRow = sourceDictionary.firstRow();
        // SANITY_CHECKS
        // Perform some checks to make sure we have everything we need setup to do a file roll.
        if (defaultRow.hasAggregateDefinition() == false)
        {
            throw new ConverterException(String.format("NO aggregate definitions found for row.id = %s", defaultRow.getID()));
        }
        final ArrayList<HashMap<String, String>> sourceDataRows = getRolledSourceDataRows(sourceData, defaultRow);
        // FIXME fireEngineEvent
        if (progressBar != null)
        {
            progressBar.updateMaximum(sourceDataRows.size());
        }
        for (final HashMap<String, String> sourceDataRow : sourceDataRows)
        {
            System.out.println("Line Index: " + lineIndex);
            if (progressBar != null)
            {
                System.out.println("Update position.");
                progressBar.updatePosition(lineIndex.intValue());
            }
            final Row targetRow = targetDictionary.firstRow();
            for (final Field targetField : targetRow.getFields())
            {
                // Find the targetDataRow for our field.
                final DataRow targetDataRow = new DataRow()
                        .loadBy_Data_Dictionary_Row(targetData.getID(), targetData.getDictionaryID(), targetRow.getID(), lineIndex);
                // Create the targetDataRow if one does not exist.
                if (targetDataRow.isNew() == true)
                {
                    targetDataRow.setDataID(targetData.getID());
                    targetDataRow.setDictionaryID(targetRow.getDictionaryID());
                    targetDataRow.setRowID(targetRow.getID());
                    targetDataRow.setSkipRow(false);
                    targetDataRow.setLineIndex(lineIndex);
                    targetDataRow.save();
                }
                // Get all the mappings that map to the target field.
                final ArrayList<Mapping> mappings = map.findSourceMappings(targetField.getID());
                if (mappings.size() > 0)
                {
                    // Loop through the mapping building up any source field data that needs to
                    // be combined to create our target data fields data.
                    String value = "";
                    for (final Mapping mapping : mappings)
                    {
                        final Field sourceField = mapping.getSourceField();
                        value += sourceDataRow.get(sourceField.getLabel());
                    }
                    // Now format the resulting combined source fields for the targetDataField.
                    final String formattedValue = TypeFormatter.INSTANCE
                            .format(targetField.getDataType(), targetField.getDataFormat(), value);
                    // System.out.println("FormattedValue: " + formattedValue);
                    // Save the completed targetDataField.
                    final DataField targetDataField = new DataField();
                    targetDataField.setDictionaryID(targetDictionary.getID());
                    targetDataField.setDataTypeID(targetField.getDataTypeID());
                    targetDataField.setFieldID(targetField.getID());
                    targetDataField.setDataRowID(targetDataRow.getID());
                    targetDataField.setOriginalValue(value);
                    targetDataField.setValue(formattedValue);
                    targetDataField.save();
                }
                else
                {
                    // If no mapping exists, create a blank targetDataField.
                    final DataField targetDataField = new DataField();
                    targetDataField.setDictionaryID(targetDictionary.getID());
                    targetDataField.setDataTypeID(targetField.getDataTypeID());
                    targetDataField.setFieldID(targetField.getID());
                    targetDataField.setDataRowID(targetDataRow.getID());
                    targetDataField.setOriginalValue("");
                    targetDataField.setValue("");
                    targetDataField.save();
                }
            }
            Store.COMMIT();
            lineIndex++;
        }
        return this;
    }


    private ArrayList<HashMap<String, String>> getRolledSourceDataRows(final Data sourceData, final Row row)
    {
        final String sql = createSQL(sourceData, row);
        LOG.info(sql);
        if (LOG.isDebugEnabled() == true)
        {
            LOG.debug(sql);
        }
        final ArrayList<HashMap<String, String>> dataRows = new ArrayList<>();
        try (final ResultSet rs = Store.selectList(sql))
        {
            LOG.info("DONE");
            while (rs.next())
            {
                dataRows.add(createDataRow(row, rs));
            }
            LOG.info("DONE2");
        }
        catch (final SQLException sqle)
        {
            throw new StorableException(sqle.getLocalizedMessage(), sqle);
        }
        return dataRows;
    }


    public HashMap<String, String> createDataRow(final Row row, final ResultSet rs)
            throws SQLException
    {
        final HashMap<String, String> dataRow = new HashMap<>();
        for (final Field field : row.getFields())
        {
            final String value = rs.getString(field.getLabel());
            dataRow.put(field.getLabel(), value);
        }
        return dataRow;
    }


    private String createSQL(final Data sourceData, final Row row)
    {
        final Field gbField = row.getFieldByAggregateDefinition(AggregateDefinition.Identifier.GROUP_BY);
        Integer n = 0;
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        // Do we need an embedded count for each grouped record?
        // count(*) AS "Count",
        sb.append(String.format("a.value AS \"%s\", ", gbField.getLabel()));
        for (final Field field : row.getFields())
        {
            if (field.getLabel().contentEquals(gbField.getLabel()) == false)
            {
                if (n > 0)
                {
                    sb.append(", ");
                }
                if (field.getAggregateDefinitionID() == null)
                {
                    sb.append(String.format("MAX(f%d.value) AS \"%s\"", n, field.getLabel()));
                }
                else
                {
                    final String identifier = field.getAggregateDefinition().getIdentifier();
                    if (field.getDataType().getIdentifier().equals(DataType.Type.CURRENCY.toString()) == true)
                    {
                        sb.append(String.format("%s(f%d.value::MONEY::NUMERIC) AS \"%s\"", identifier, n, field.getLabel()));
                    }
                    else
                    {
                        sb.append(String.format("%s(f%d.value) AS \"%s\"", identifier, n, field.getLabel()));
                    }
                }
            }
            n++;
        }
        sb.append(String
                .format(" FROM (SELECT data_field.data_row_id, data_field.value FROM data_field, field, data_row WHERE field.id = %d AND data_row.data_id = %d AND data_field.data_row_id = data_row.id AND  data_field.field_id = field.id AND data_field.value != field.label) AS a,",
                        gbField.getID(),
                        sourceData.getID()));
        n = 0;
        for (final Field field : row.getFields())
        {
            if (field.getLabel().contentEquals(gbField.getLabel()) == false)
            {
                if (n > 0)
                {
                    sb.append(", ");
                }
                sb.append(String.format("data_field AS f%d", n));
            }
            n++;
        }
        sb.append(" WHERE ");
        n = 0;
        for (final Field field : row.getFields())
        {
            if (field.getLabel().contentEquals(gbField.getLabel()) == false)
            {
                if (n > 0)
                {
                    sb.append(" AND ");
                }
                sb.append(String.format("f%d.data_row_id = a.data_row_id AND f%d.field_id = %d", n, n, field.getID()));
            }
            n++;
        }
        sb.append(" GROUP BY a.value");
        return sb.toString();
    }
}
