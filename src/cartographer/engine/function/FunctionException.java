package cartographer.engine.function;

public class FunctionException extends RuntimeException
{
    private static final long serialVersionUID = 1L;


    public FunctionException(final String message)
    {
        super(message);
    }


    public FunctionException(final String message, final Object... values)
    {
        super(String.format(message, values));
    }


    public FunctionException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
