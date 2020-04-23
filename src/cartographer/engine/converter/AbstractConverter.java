package cartographer.engine.converter;

import cartographer.engine.Conversion;
import cartographer.engine.io.ProgressBar;


public abstract class AbstractConverter implements Converter
{
    private Conversion conversion = null;


    public AbstractConverter(final Conversion conversion)
    {
        this.conversion = conversion;
    }


    @Override
    public abstract Converter convertData(ProgressBar progressBar)
            throws Exception;


    public Conversion getConversion()
    {
        return conversion;
    }
}
