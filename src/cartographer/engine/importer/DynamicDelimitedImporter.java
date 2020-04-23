package cartographer.engine.importer;

import cartographer.engine.Conversion;
import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.Dictionary;
import cartographer.engine.Row;
import cartographer.engine.format.TypeFormatter;
import cartographer.engine.io.ProgressBar;
import cartographer.engine.io.reader.AbstractArrayReader;
import cartographer.engine.io.reader.DynamicDelimitedArrayReader;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.common.util.ListUtils;
import nuberplex.store.Store;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class DynamicDelimitedImporter extends AbstractImporter
{
    // private final static Logger LOG = LogManager.getLogger(DelimitedImporter.class);
    public DynamicDelimitedImporter(final Conversion conversion)
    {
        super(conversion);
    }


    @Override
    public Importer importData(final ProgressBar progressBar)
            throws Exception
    {
        final var file = getConversion().getSourceData().getDataFile();
        if (file == null)
        {
            throw new RequiredParameterException("file");
        }
        if (file.exists() == false)
        {
            throw new FileNotFoundException(file.getPath());
        }
        final var dictionary = getConversion().getSourceDictionary();
        System.out.println(String.format("Header Line: %s", dictionary.getHeaderLineNumber()));
        final var data = getConversion().getSourceData();
        data.setDataSize(file.length());
        data.save();
        try (AbstractArrayReader reader = new DynamicDelimitedArrayReader(file, dictionary, progressBar))
        {
            ArrayList<String> array = null;
            while ((array = reader.readArray()) != null)
            {
                final var row = findRow(dictionary, array);
                final var dataRow = new DataRow();
                dataRow.setDataID(data.getID());
                dataRow.setDictionaryID(dictionary.getID());
                dataRow.setRowID(row.getID());
                // FIXME data_row.line NEEDS TO BE REMOVED FROM DB
                // dataRow.setLine(line);
                dataRow.setLineIndex(reader.getLineNumber());
                dataRow.setSkipRow(false);
                dataRow.save();
                for (final var field : row.getFields())
                {
                    final var dataField = new DataField();
                    dataField.setDictionaryID(row.getDictionaryID());
                    dataField.setDataTypeID(field.getDataTypeID());
                    dataField.setDataFormat(field.getDataFormat());
                    dataField.setDataRowID(dataRow.getID());
                    dataField.setFieldID(field.getID());
                    dataField.setCreateUser(dataRow.getCreateUser());
                    dataField.setUpdateUser(dataRow.getUpdateUser());
                    final var value = ListUtils.GET(array, field.getPosition().intValue() - 1).trim();
                    dataField.setOriginalValue(value);
                    dataField.setValue(TypeFormatter.INSTANCE.format(dataField.getDataType(), field.getDataFormat(), value));
                    dataField.save();
                }
                Store.COMMIT();
            }
        }
        return this;
    }


    protected Row findRow(final Dictionary dictionary, final ArrayList<String> array)
    {
        // Check if we have a "type" field set anywhere in the dictionary.
        if (dictionary.hasTypesRows() == false)
        {
            return dictionary.firstRow();
        }
        // FIXME Loop through the rows and find the type field that matches.
        throw new RuntimeException(String.format("Cannot find row for dictionary: %s", dictionary.getLabel()));
    }
}
