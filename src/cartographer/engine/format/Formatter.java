package cartographer.engine.format;

import java.text.ParseException;


public interface Formatter
{
    public abstract String format(String pattern, String value)
            throws ParseException;
}
