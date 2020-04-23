package cartographer.engine.importer;

import cartographer.engine.io.ProgressBar;


public interface Importer
{
    public Importer importData(ProgressBar progressBar)
            throws Exception;
}
