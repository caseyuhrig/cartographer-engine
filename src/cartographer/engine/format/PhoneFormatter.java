package cartographer.engine.format;

import java.text.ParseException;


public class PhoneFormatter implements Formatter
{
    public static PhoneFormatter DEFAULT = new PhoneFormatter();


    @Override
    public String format(final String pattern, final String value)
            throws ParseException
    {
        if (pattern != null && !pattern.isBlank())
        {
            switch (pattern) {
                case "###-###-####" -> {
                    final var digits = value.replaceAll("[^0-9]", "");
                    if (digits.length() == 10)
                    {
                        return String.format("%s-%s-%s", digits.substring(0, 3), digits.substring(3, 6), digits.substring(6, 10));
                    }
                }
                case "(###) ###-####" -> {
                    final var digits = value.replaceAll("[^0-9]", "");
                    if (digits.length() == 10)
                    {
                        return String.format("(%s) %s-%s", digits.substring(0, 3), digits.substring(3, 6), digits.substring(6, 10));
                    }
                }
                case "##########" -> {
                    final var digits = value.replaceAll("[^0-9]", "");
                    if (digits.length() == 10)
                    {
                        return String.format("%s%s%s", digits.substring(0, 3), digits.substring(3, 6), digits.substring(6, 10));
                    }
                }
                default -> {
                    throw new UnsupportedOperationException(String.format("Invalid Format '%s' for PHONE type.", pattern));
                }
            }
        }
        return value;
    }
}
