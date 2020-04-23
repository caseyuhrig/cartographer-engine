package cartographer.engine.io.reader;

import cartographer.engine.Dictionary;
import cartographer.engine.io.ProgressBar;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.common.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public abstract class AbstractArrayReader extends FilterInputStream implements ArrayReader
{
    protected ProgressBar progressBar = null;
    protected Long lineNumber = 0L;


    public AbstractArrayReader(final InputStream in, final ProgressBar progressBar)
    {
        super(in);
        if (in == null)
        {
            throw new RequiredParameterException("in");
        }
        lineNumber = 0L;
        this.progressBar = progressBar;
    }


    @Override
    public abstract ArrayList<String> readArray()
            throws IOException;


    @Override
    public Long getLineNumber()
    {
        return lineNumber;
    }


    @Override
    public ProgressBar getProgressBar()
    {
        return progressBar;
    }


    /**
     * FIXME HACK This should be some place else since only certain types of files can have the
     * dictionary generated automatically.
     * @param dictionary
     * @param file
     * @return
     * @throws IOException
     */
    public static AbstractArrayReader CREATE(final Dictionary dictionary, final File file)
            throws IOException
    {
        if (file == null)
        {
            throw new RequiredParameterException("file");
        }
        if (file.exists() == false)
        {
            throw new FileNotFoundException(file.getPath());
        }
        if (dictionary == null)
        {
            throw new RequiredParameterException("dictionary");
        }
        if (dictionary.getDynamic() == true)
        {
            throw new UnsupportedOperationException("Please contact your software provider for dynamic dictionary support.");
        }
        else if (StringUtils.ENDS_WITH(file.getPath(), ".xls") == true)
        {
            return new ExcelArrayReader(file, null);
        }
        else if (StringUtils.ENDS_WITH(file.getPath(), ".xlsx") == true)
        {
            return new ExcelXmlArrayReader(file, null);
        }
        else if (StringUtils.IS_NOT_EMPTY(dictionary.getColumnDelimiter()) == true)
        {
            if (StringUtils.IS_NOT_EMPTY(dictionary.getFieldDelimiter()) == true)
            {
                throw new UnsupportedOperationException(String.format("Field level delimiter not implemented"));
            }
            else
            {
                return new DelimitedArrayReader(file, dictionary.getColumnDelimiter(), null);
            }
        }
        else if (dictionary.getXml() == true)
        {
            throw new UnsupportedOperationException("Please contact software provider for XML support.");
        }
        else
        {
            throw new UnsupportedOperationException(String.format("Reader cannot be found for: %s", file.getPath()));
        }
    }
}
