package cartographer.engine.function;

import java.util.regex.Pattern;


public class REPLACE implements Function
{
    private static Pattern PATTERN = Pattern.compile("^\"(.*?)\",\"(.*?)\"");


    @Override
    public String execute(final String value, final String... args)
    {
        if (value == null || value.isBlank())
        {
            return "";
        }
        if (args != null && args.length == 1)
        {
            final var matcher = PATTERN.matcher(args[0]);
            if (matcher.matches())
            {
                final var str = matcher.group(1);
                final var with = matcher.group(2);
                return value.replaceAll(str, with);
            }
        }
        return value;
    }
}
