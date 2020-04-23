package cartographer.engine.importer;

import cartographer.engine.Conversion;
import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.format.TypeFormatter;
import cartographer.engine.io.ProgressBar;
import cartographer.engine.io.reader.SingleLineFixedLengthArrayReader;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.common.util.ListUtils;
import nuberplex.store.Store;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Imports fixed-length single record type files. Currently tested with data that is all on one line
 * of a file.
 * @author casey
 */
public class FixedLengthSingleLineImporter extends AbstractImporter
{
    // private final static Logger LOG = LogManager.getLogger(FixedLengthImporter.class);
    public FixedLengthSingleLineImporter(final Conversion conversion)
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
        final var data = getConversion().getSourceData();
        data.setDataSize(file.length());
        data.save();
        // since the entire file is a single record type we need the definition for the record.
        // final Row row = dictionary.firstRow();
        // try (AbstractArrayReader reader = new DelimitedArrayReader(file,
        // dictionary.getColumnDelimiter(), progressBar))
        try (var reader = new SingleLineFixedLengthArrayReader(dictionary, file, progressBar))
        {
            ArrayList<String> array = null;
            while ((array = reader.readArray()) != null)
            {
                final var row = dictionary.firstRow();
                final var dataRow = new DataRow();
                dataRow.setDataID(data.getID());
                dataRow.setDictionaryID(dictionary.getID());
                dataRow.setRowID(row.getID());
                // FIXME data_row.line NEEDS TO BE REMOVED FROM DB
                // dataRow.setLine(line);
                dataRow.setLineIndex(reader.getLineNumber());
                dataRow.setSkipRow(false);
                dataRow.save();
                int n = 0;
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
                    final var value = ListUtils.GET(array, n).trim();
                    dataField.setOriginalValue(value);
                    dataField.setValue(TypeFormatter.INSTANCE.format(dataField.getDataType(), field.getDataFormat(), value));
                    dataField.save();
                    n++;
                }
                Store.COMMIT();
            }
        }
        return this;
    }
}
