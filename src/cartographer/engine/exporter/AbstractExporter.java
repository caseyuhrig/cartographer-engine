package cartographer.engine.exporter;

import cartographer.engine.Conversion;
import cartographer.engine.io.ProgressBar;


public abstract class AbstractExporter implements Exporter
{
    private Conversion conversion = null;


    public AbstractExporter(final Conversion conversion)
    {
        this.conversion = conversion;
    }


    @Override
    public abstract Exporter exportData(final ProgressBar progressBar)
            throws Exception;


    public Conversion getConversion()
    {
        return conversion;
    }
}
