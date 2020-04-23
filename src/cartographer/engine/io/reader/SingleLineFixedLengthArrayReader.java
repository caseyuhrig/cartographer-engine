package cartographer.engine.io.reader;

import cartographer.engine.Dictionary;
import cartographer.engine.Row;
import cartographer.engine.io.ProgressBar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class SingleLineFixedLengthArrayReader extends FileInputStream implements ArrayReader
{
    private final static Logger LOG = LogManager.getLogger(SingleLineFixedLengthArrayReader.class);

    private ProgressBar progressBar = null;

    private Row row = null;

    private FileChannel channel = null;

    private ByteBuffer buffer = null;

    private Charset charset = null;

    public SingleLineFixedLengthArrayReader(final Dictionary dictionary, final File file, final ProgressBar progress)
            throws IOException
    {
        super(file);
        this.row = dictionary.firstRow();
        this.progressBar = progress;
        // this.lengths = row.getFields().stream().map(field ->
        // field.getLength().intValue()).collect(Collectors.toList());
        this.channel = getChannel();
        final Long bufferSize = dictionary.getRowLength();
        this.buffer = ByteBuffer.allocateDirect(bufferSize.intValue());
        this.charset = Charset.forName("ASCII");
        if (progressBar != null)
        {
            progressBar.updatePosition(0);
            progressBar.updateMaximum(Long.valueOf(channel.size()).intValue());
        }
    }


    @Override
    public ArrayList<String> readArray()
            throws IOException
    {
        final int bytesRead = channel.read(buffer);
        if (bytesRead > 0)
        {
            // int n = 0;
            final var record = new ArrayList<String>();
            final var chars = charset.decode(buffer.flip());
            for (final var field : row.getFields())
            {
                final String value = chars.slice(field.getPosition() - 1, field.getLength().intValue()).toString();
                // System.out.println(String.format("%s: %s", n, value));
                record.add(value);
                // n++;
            }
            if (progressBar != null)
            {
                // System.out.println(String.format("Channel Position: %s", channel.position()));
                progressBar.updatePosition(Long.valueOf(channel.position()).intValue());
            }
            buffer.clear();
            return record;
        }
        else
        {
            return null;
        }
    }


    /**
     * FIXME We should be saving the line number along with the byte positions being read for files
     * like this.
     */
    @Override
    public Long getLineNumber()
    {
        return 1L;
    }


    @Override
    public ProgressBar getProgressBar()
    {
        return progressBar;
    }


    @Override
    public void close()
            throws IOException
    {
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
