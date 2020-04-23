package cartographer.engine.importer;

import cartographer.engine.Conversion;
import cartographer.engine.Data;
import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.Dictionary;
import cartographer.engine.Field;
import cartographer.engine.Row;
import cartographer.engine.format.TypeFormatter;
import cartographer.engine.io.ProgressBar;
import cartographer.engine.io.reader.AbstractArrayReader;
import cartographer.engine.io.reader.ExcelXmlArrayReader;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.store.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class ExcelXmlImporter extends AbstractImporter
{
    private static Logger LOG = LogManager.getLogger(ExcelXmlImporter.class);


    public ExcelXmlImporter(final Conversion conversion)
    {
        super(conversion);
    }


    @Override
    public Importer importData(final ProgressBar progressBar)
            throws Exception
    {
        final File file = getConversion().getSourceData().getDataFile();
        if (file == null)
        {
            throw new RequiredParameterException("file");
        }
        if (file.exists() == false)
        {
            throw new FileNotFoundException(file.getPath());
        }
        final Dictionary dictionary = getConversion().getSourceDictionary();
        final Data data = getConversion().getSourceData();
        data.setDataSize(file.length());
        data.save();
        if (progressBar != null)
        {
            final Integer length = Long.valueOf(file.length()).intValue();
            progressBar.updateMaximum(length);
            progressBar.updatePosition(0);
        }
        try (AbstractArrayReader reader = new ExcelXmlArrayReader(file, progressBar))
        {
            ArrayList<String> array = null;
            while ((array = reader.readArray()) != null)
            {
                final Row row = findRow(dictionary, array);
                final DataRow dataRow = new DataRow();
                dataRow.setDataID(data.getID());
                dataRow.setDictionaryID(dictionary.getID());
                dataRow.setRowID(row.getID());
                // dataRow.setLine(line);
                dataRow.setLineIndex(reader.getLineNumber());
                dataRow.setSkipRow(false);
                dataRow.save();
                for (final Field field : row.getFields())
                {
                    final DataField dataField = new DataField();
                    dataField.setDictionaryID(row.getDictionaryID());
                    dataField.setDataTypeID(field.getDataTypeID());
                    // dataFile.setDataFormat(field.getDataFormat()); WHY MISSING?
                    dataField.setDataRowID(dataRow.getID());
                    dataField.setFieldID(field.getID());
                    dataField.setCreateUser(dataRow.getCreateUser());
                    dataField.setUpdateUser(dataRow.getUpdateUser());
                    String value = "";
                    try
                    {
                        value = array.get(field.getPosition().intValue() - 1).trim();
                    }
                    catch (final IndexOutOfBoundsException e)
                    {
                        LOG.debug(e.getLocalizedMessage(), e);
                    }
                    dataField.setOriginalValue(value);
                    dataField.setValue(TypeFormatter.INSTANCE.format(dataField.getDataType(), field.getDataFormat(), value));
                    dataField.save();
                }
            }
            Store.COMMIT();
        }
        if (progressBar != null)
        {
            progressBar.updatePosition(0);
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
