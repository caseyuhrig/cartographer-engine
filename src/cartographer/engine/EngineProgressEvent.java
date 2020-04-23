package cartographer.engine;

public class EngineProgressEvent extends EngineEvent
{
    private Long max = null;

    private Long position = null;


    public EngineProgressEvent(final Long max, final Long position)
    {
        this.max = max;
        this.position = position;
    }


    public Long getPosition(final Long base)
    {
        final Long progress = base * position / max;
        return progress;
    }
}
