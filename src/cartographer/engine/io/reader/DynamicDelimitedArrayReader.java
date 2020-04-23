package cartographer.engine.io.reader;

import cartographer.engine.Dictionary;
import cartographer.engine.io.ProgressBar;

import nuberplex.common.lang.exception.RequiredParameterException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;


public class DynamicDelimitedArrayReader extends AbstractArrayReader
{
    private final static Logger LOG = LogManager.getLogger(DynamicDelimitedArrayReader.class);
    private final BufferedReader reader;
    private final Dictionary dictionary;
    private ArrayList<String> header = null;


    public DynamicDelimitedArrayReader(final InputStream input, final Dictionary dictionary, final ProgressBar progressBar)
            throws IOException
    {
        super(input, progressBar);
        this.dictionary = dictionary;
        if (dictionary == null)
        {
            throw new RequiredParameterException("dictionary");
        }
        if (dictionary.getHeaderLineNumber() == null)
        {
            throw new RequiredParameterException("headerLineNumber");
        }
        if (dictionary.getColumnDelimiter() == null || dictionary.getColumnDelimiter().isBlank())
        {
            throw new RequiredParameterException("columnDelimiter");
        }
        reader = new BufferedReader(new InputStreamReader(this));
    }


    public DynamicDelimitedArrayReader(final File file, final Dictionary dictionary, final ProgressBar progressBar)
            throws IOException
    {
        this(new FileInputStream(file), dictionary, progressBar);
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
        if (lineNumber == dictionary.getHeaderLineNumber())
        {
            header = new ArrayList<>(Arrays.stream(line.split(dictionary.getColumnDelimiter())).map(s -> {
                return s == null ? "" : s.strip();
            }).collect(Collectors.toList()));
            header.forEach(label -> {
                System.out.println(String.format("H: %s", label));
            });
        }
        // create an empty map to hold our data which is based on the header in the file. This may
        // not be what is mapped in our dictionary. Since this is a dynamic dictionary.
        final var map = new LinkedHashMap<String, String>();
        for (final var label : header)
        {
            map.put(label, "");
        }
        final var results = new ArrayList<>(Arrays.stream(line.split(dictionary.getColumnDelimiter())).map(s -> {
            return s == null ? "" : s.strip();
        }).collect(Collectors.toList()));
        for (int n = 0; n < results.size(); n++)
        {
            final var label = header.get(n);
            final var value = results.get(n);
            map.put(label, value);
        }
        // rearrange the data to it matches out row fields order.
        final var output = new ArrayList<String>();
        final var row = dictionary.firstRow();
        row.getFields().forEach(field -> {
            final var label = field.getLabel();
            final var value = map.get(label);
            if (value == null)
            {
                throw new RuntimeException(String.format("No value found for field %s.", label));
            }
            output.add(value);
        });
        lineNumber++;
        return output;
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
