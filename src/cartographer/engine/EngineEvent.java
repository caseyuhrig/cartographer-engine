package cartographer.engine;

public class EngineEvent
{
    private Engine engine = null;

    public EngineEvent()
    {
        super();
    }


    public Engine getEngine()
    {
        return engine;
    }


    public void setEngine(final Engine engine)
    {
        this.engine = engine;
    }
}
