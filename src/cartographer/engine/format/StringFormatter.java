package cartographer.engine.format;

import java.text.ParseException;


public class StringFormatter implements Formatter
{
    public static StringFormatter DEFAULT = new StringFormatter();


    @Override
    public String format(final String format, final String value)
            throws ParseException
    {
        if (format != null && !format.isBlank())
        {
            return String.format(format, value);
        }
        return value;
    }
}
