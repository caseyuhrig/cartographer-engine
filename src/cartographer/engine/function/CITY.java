package cartographer.engine.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nuberplex.common.util.StringUtils;


public class CITY implements Function
{
    public static final Pattern PAT_CITY = Pattern.compile("^(.*?),.*?$");


    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        final Matcher matcher = PAT_CITY.matcher(value);
        if (matcher.matches() == true)
        {
            return StringUtils.COALESCE(matcher.group(1), "").trim();
        }
        return "";
    }
}
