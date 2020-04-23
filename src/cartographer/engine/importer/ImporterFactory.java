package cartographer.engine.importer;

import cartographer.engine.Conversion;

import java.io.FileNotFoundException;
import java.io.IOException;


public class ImporterFactory
{
    public static Importer FIND(final Conversion conversion)
            throws IOException
    {
        final var file = conversion.getSourceData().getDataFile();
        if (file == null)
        {
            throw new FileNotFoundException(conversion.getSourceData().getDataPath());
        }
        if (file.exists() == false)
        {
            throw new FileNotFoundException(file.getPath());
        }
        final var dictionary = conversion.getSourceDictionary();
        if (dictionary.getRowLength() != null)
        {
            // Single record type fixed importer.
            return new FixedLengthSingleLineImporter(conversion);
        }
        else if (file.getPath().toLowerCase().endsWith(".xls"))
        {
            return new ExcelImporter(conversion);
        }
        else if (file.getPath().toLowerCase().endsWith(".xlsx"))
        {
            return new ExcelXmlImporter(conversion);
        }
        else if (dictionary.getColumnDelimiter() != null && !dictionary.getColumnDelimiter().isBlank())
        {
            if (dictionary.getFieldDelimiter() != null && !dictionary.getFieldDelimiter().isBlank())
            {
                throw new UnsupportedOperationException(
                        "Field level delimiter support not implemented, please contact your software provider.");
            }
            else
            {
                if (dictionary.getDynamic())
                {
                    return new DynamicDelimitedImporter(conversion);
                }
                else
                {
                    return new DelimitedImporter(conversion);
                }
            }
        }
        else if (dictionary.getXml() == true)
        {
            throw new IOException("Please contact NuberPlex (360) 597-3031 for XML support.");
        }
        else if (dictionary.getRowLength() == null)
        {
            return new MultiRecordTypeFixedLengthImporter(conversion);
        }
        else
        {
            throw new IOException(String.format("Importer cannot be found for: %s", file.getPath()));
        }
    }
}
