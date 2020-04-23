package cartographer.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TextDocument extends DefaultStyledDocument
{
    private static final long serialVersionUID = 1L;

    protected static Logger LOG = LogManager.getLogger(TextDocument.class);


    public TextDocument()
    {
        super();
    }


    public void setContent(final InputStream input)
            throws IOException
    {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(input)))
        {
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                try
                {
                    append(line);
                    append("\n");
                }
                catch (final BadLocationException e)
                {
                    throw new IOException(e.getLocalizedMessage(), e);
                }
            }
            reader.close();
        }
    }


    public void append(final String value)
            throws BadLocationException
    {
        this.insertString(getLength(), value, null);
    }
}
