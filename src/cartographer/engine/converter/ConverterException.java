package cartographer.engine.converter;

public class ConverterException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ConverterException(final String message)
    {
        super(message);
    }


    public ConverterException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
