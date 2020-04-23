package cartographer.engine.exporter;

import java.io.File;
import java.io.IOException;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.common.util.StringUtils;

import cartographer.engine.Conversion;
import cartographer.engine.Dictionary;


public class ExporterFactory
{
    public static Exporter FIND(final Conversion conversion)
            throws IOException
    {
        final File file = conversion.getTargetData().getDataFile();
        if (file == null)
        {
            throw new RequiredParameterException("file");
        }
        // if (file.exists() == false)
        // {
        // throw new FileNotFoundException(file.getPath());
        // }
        final Dictionary dictionary = conversion.getTargetDictionary();
        if (dictionary.getDynamic() == true)
        {
            throw new IOException("Please contact software provider for Dynamic support.");
        }
        else if (StringUtils.ENDS_WITH(file.getPath(), ".xls") == true)
        {
            throw new IOException("Please contact software provider for XLS support.");
        }
        else if (StringUtils.ENDS_WITH(file.getPath(), ".xlsx") == true)
        {
            throw new IOException("Please contact software provider for XLSX support.");
        }
        else if (StringUtils.IS_NOT_EMPTY(dictionary.getColumnDelimiter()) == true)
        {
            if (StringUtils.IS_NOT_EMPTY(dictionary.getFieldDelimiter()) == true)
            {
                throw new IOException(String.format("Field level delimiter not implemented"));
            }
            else
            {
                return new TabDelimitedDataExporter(conversion);
            }
        }
        else if (dictionary.getXml() == true)
        {
            throw new IOException("Please contact software provider for XML support.");
        }
        else
        {
            throw new IOException(String.format("Reader cannot be found for: %s", file.getPath()));
        }
    }
}
