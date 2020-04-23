package cartographer.engine.converter;

import cartographer.engine.Conversion;
import cartographer.engine.ConversionException;
import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.DataType;
import cartographer.engine.format.TypeFormatter;
import cartographer.engine.io.ProgressBar;

import nuberplex.store.Store;


public class MultiRecordTypeToSingleConverter extends AbstractConverter
{
    public MultiRecordTypeToSingleConverter(final Conversion conversion)
    {
        super(conversion);
    }


    @Override
    public Converter convertData(final ProgressBar progressBar)
            throws Exception
    {
        System.out.println(String.format("Convert: %s", getClass().getName()));
        final var sourceDictionary = getConversion().getSourceDictionary();
        final var sourceData = getConversion().getSourceData();
        final var map = getConversion().getMap();
        final var targetDictionary = getConversion().getTargetDictionary();
        final var targetData = getConversion().getTargetData();
        // Start the recordIndex at 0 because the first record will increment it to 1.
        Long recordIndex = 0L;
        Long lineIndex = 1L;
        if (progressBar != null)
        {
            progressBar.updateMaximum(sourceData.getDataRows().size());
        }
        // FIXME ACTUALLY need the first mapping and find what source it's mapped to.
        final var firstRecordType = sourceDictionary.firstRow().getLabel();
        String currentKeyValue = null;
        final var targetRow = targetDictionary.firstRow();
        for (final var sourceDataRow : sourceData.getDataRows())
        {
            // System.out.println(lineIndex);
            if (lineIndex % 10 == 0 && progressBar != null)
            {
                progressBar.updatePosition(lineIndex.intValue());
            }
            if (sourceDataRow.getSkipRow() == false)
            {
                // Get the data type field from the source record.
                final var sourceRecordType = sourceDataRow.getRow().getLabel();
                // TODO Guessing a DB query would be faster.
                final var currentLineIndex = lineIndex;
                final var sourceKeyDataField = sourceDataRow.getDataFields().stream()
                        .filter(field -> field.getDataType().type() == DataType.Type.KEY).findFirst()
                        .orElseThrow(() -> new ConversionException(String.format("Missing source KEY field for record[%s]: %s",
                                                                                 currentLineIndex,
                                                                                 sourceDataRow.getRow().getLabel())));
                final var keyValue = sourceKeyDataField.getValue();
                // We need the first record type that is being mapped. Records are skipped until
                // this one is found. FIXME, currently using a key field to determine if we are in a
                // block of records to process. Should just use the record type since not all
                // records might not have a key, in this case we do.
                if (sourceRecordType.equals(firstRecordType))
                {
                    currentKeyValue = keyValue;
                    recordIndex++;
                }
                // This will ignore any records until we find a first record to map.
                if (keyValue.equals(currentKeyValue))
                {
                    final var targetDataRow = new DataRow().loadBy_Data_Dictionary_Row(targetData.getID(),
                                                                                       targetData.getDictionaryID(),
                                                                                       targetRow.getID(),
                                                                                       recordIndex);
                    if (targetDataRow.isNew())
                    {
                        targetDataRow.setDataID(targetData.getID());
                        targetDataRow.setDictionaryID(targetRow.getDictionaryID());
                        targetDataRow.setRowID(targetRow.getID());
                        targetDataRow.setSkipRow(false);
                        targetDataRow.setLineIndex(recordIndex);
                        targetDataRow.save();
                    }
                    // Do we have any target data fields that were mapped from a previous record?
                    for (final var targetField : targetRow.getFields())
                    {
                        final var targetDataField = new DataField().load(targetDataRow.getID(), targetField.getID());
                        if (targetDataField.isNew())
                        {
                            targetDataField.setDictionaryID(targetDictionary.getID());
                            targetDataField.setDataTypeID(targetField.getDataTypeID());
                            targetDataField.setFieldID(targetField.getID());
                            targetDataField.setDataRowID(targetDataRow.getID());
                            // targetDataField.setValue("");
                            targetDataField.save();
                        }
                        final var mappings = map.getMappingByTargetFieldID(targetField.getID());
                        if (mappings.size() > 0)
                        {
                            final var value = new StringBuilder();
                            value.append(clean(targetDataField.getValue()));
                            for (final var mapping : mappings)
                            {
                                final var sourceDataField = new DataField().load(sourceDataRow.getID(), mapping.getSourceFieldID());
                                final var sourceValue = mapping.parse(sourceDataField.getValue());
                                value.append(clean(sourceValue));
                            }
                            final var formattedValue = clean(TypeFormatter.INSTANCE
                                    .format(targetField.getDataType(), targetField.getDataFormat(), clean(value.toString())));
                            targetDataField.setOriginalValue(clean(value.toString()));
                            targetDataField.setValue(formattedValue);
                            targetDataField.save();
                        }
                    }
                }
            }
            Store.COMMIT();
            lineIndex++;
        }
        return this;
    }


    private String clean(final String value)
    {
        if (value == null)
        {
            return "";
        }
        if (value.equals("null"))
        {
            return "";
        }
        return value;
    }
}
