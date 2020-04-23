package cartographer.engine;

public class EngineExceptionEvent extends EngineEvent
{
    private Throwable throwable = null;


    public EngineExceptionEvent setThrowable(final Throwable throwable)
    {
        this.throwable = throwable;
        return this;
    }


    public Throwable getThrowable()
    {
        return throwable;
    }
}
