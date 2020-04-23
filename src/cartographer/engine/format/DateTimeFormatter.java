package cartographer.engine.format;

import nuberplex.common.lang.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateTimeFormatter implements Formatter
{
    public static DateTimeFormatter DEFAULT = new DateTimeFormatter();


    @Override
    public String format(final String pattern, final String value)
            throws ParseException
    {
        if (pattern != null && !pattern.isBlank())
        {
            final var dateTime = DateTime.valueOf(value);
            final var formatter = new SimpleDateFormat(pattern);
            return formatter.format(dateTime.toJavaDate());
        }
        return value;
    }
}
