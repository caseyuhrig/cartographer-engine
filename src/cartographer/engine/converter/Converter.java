package cartographer.engine.converter;

import cartographer.engine.io.ProgressBar;


public interface Converter
{
    public Converter convertData(ProgressBar progressBar)
            throws Exception;
}
