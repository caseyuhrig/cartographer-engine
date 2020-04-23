/**
 * (c) Copyright 2012, Columbia Dynamics, All Rights Reserved.
 * http://www.columbiadynamics.com
 * casey.uhrig@columbiadynamics.com
 */
package cartographer.engine.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Update a progress bar as a file is read.
 */
public class FileProgressInputStream extends FileInputStream
{
    protected static final Logger LOG = LogManager.getLogger(FileProgressInputStream.class);

    private ProgressBar progressBar = null;


    public FileProgressInputStream(final File file, final ProgressBar progressBar)
            throws IOException
    {
        super(file);
        this.progressBar = progressBar;
        if (progressBar != null)
        {
            final Long max = file.length();
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    progressBar.setMinimum(0);
                    progressBar.setMaximum(max.intValue());
                    progressBar.setValue(0);
                }
            });
        }
    }


    @Override
    public int read()
            throws IOException
    {
        final int value = super.read();
        handleProgressUpdate();
        return value;
    }


    @Override
    public int read(final byte[] bytes)
            throws IOException
    {
        final int value = super.read(bytes);
        handleProgressUpdate();
        return value;
    }


    @Override
    public int read(final byte[] bytes, final int off, final int len)
            throws IOException
    {
        final int value = super.read(bytes, off, len);
        handleProgressUpdate();
        return value;
    }


    /**
     * Handle when to update the progress bar. Only updates the progress bar every 1%
     */
    protected void handleProgressUpdate()
    {
        if (progressBar != null)
        {
            try
            {
                final Long pos = getChannel().position();
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressBar.setValue(pos.intValue());
                    }
                });
            }
            catch (final Throwable throwable)
            {
                LOG.error(throwable.getLocalizedMessage(), throwable);
            }
        }
    }
}
