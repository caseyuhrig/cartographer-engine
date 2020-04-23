package cartographer.engine.format;

import nuberplex.common.util.DoubleUtils;

import java.text.DecimalFormat;
import java.text.ParseException;


public class CurrencyFormatter implements Formatter
{
    public static CurrencyFormatter DEFAULT = new CurrencyFormatter();


    @Override
    public String format(final String pattern, final String value)
            throws ParseException
    {
        if (pattern != null && !pattern.isBlank())
        {
            final var formatter = new DecimalFormat(pattern);
            formatter.setDecimalSeparatorAlwaysShown(true);
            return formatter.format(DoubleUtils.PARSE(value));
        }
        return value;
    }
}
