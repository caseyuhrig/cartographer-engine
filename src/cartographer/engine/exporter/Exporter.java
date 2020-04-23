package cartographer.engine.exporter;

import cartographer.engine.io.ProgressBar;


public interface Exporter
{
    public Exporter exportData(ProgressBar progressBar)
            throws Exception;
}
