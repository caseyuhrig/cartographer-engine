package cartographer.engine.importer;

import cartographer.engine.Conversion;
import cartographer.engine.io.ProgressBar;


public abstract class AbstractImporter implements Importer
{
    private Conversion conversion = null;


    public AbstractImporter(final Conversion conversion)
    {
        this.conversion = conversion;
    }


    @Override
    public abstract Importer importData(ProgressBar progressBar)
            throws Exception;


    public Conversion getConversion()
    {
        return conversion;
    }
}
