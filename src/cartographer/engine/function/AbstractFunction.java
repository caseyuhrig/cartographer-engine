package cartographer.engine.function;

public abstract class AbstractFunction implements Function
{
    @Override
    public abstract String execute(String value, String... args);
}
