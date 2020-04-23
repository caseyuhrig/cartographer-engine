package cartographer.engine.io.reader;

import cartographer.engine.io.ProgressBar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class DelimitedArrayReader extends AbstractArrayReader
{
    private final static Logger LOG = LogManager.getLogger(DelimitedArrayReader.class);

    private String columnDelimiter = null;

    private BufferedReader reader = null;

    public DelimitedArrayReader(final InputStream input, final String columnDelimiter, final ProgressBar progressBar)
            throws IOException
    {
        super(input, progressBar);
        reader = new BufferedReader(new InputStreamReader(this));
        this.columnDelimiter = columnDelimiter;
    }


    public DelimitedArrayReader(final File file, final String columnDelimiter, final ProgressBar progressBar)
            throws IOException
    {
        this(new FileInputStream(file), columnDelimiter, progressBar);
    }


    @Override
    public ArrayList<String> readArray()
            throws IOException
    {
        final String line = reader.readLine();
        if (line == null)
        {
            return null;
        }
        final ArrayList<String> results = new ArrayList<>();
        final String[] tokens = line.split(columnDelimiter);
        for (final String token : tokens)
        {
            if (token == null)
            {
                results.add("");
            }
            else
            {
                results.add(token.trim());
            }
        }
        lineNumber++;
        return results;
    }


    @Override
    public void close()
            throws IOException
    {
        if (reader != null)
        {
            try
            {
                reader.close();
            }
            catch (final Throwable throwable)
            {
                // do nothing
            }
        }
        super.close();
        try
        {
            if (progressBar != null)
            {
                progressBar.updatePosition(0);
            }
        }
        catch (final Throwable throwable)
        {
            LOG.error(throwable.getLocalizedMessage(), throwable);
        }
    }
}
