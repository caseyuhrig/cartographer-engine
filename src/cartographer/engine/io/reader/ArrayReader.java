package cartographer.engine.io.reader;

import cartographer.engine.io.ProgressBar;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;


public interface ArrayReader extends Closeable
{
    ArrayList<String> readArray()
            throws IOException;


    Long getLineNumber();


    /**
     * HACK The progress bar needs to be done with listeners.
     */
    ProgressBar getProgressBar();
}
