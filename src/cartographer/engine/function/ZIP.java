package cartographer.engine.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nuberplex.common.util.StringUtils;


public class ZIP implements Function
{
    private static final Pattern PATTERN_ZIP = Pattern.compile("^.*?([0-9]{5}(?:[-])?(?:[0-9]{4})?).*?$");


    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        final Matcher matcher = PATTERN_ZIP.matcher(value.trim());
        if (matcher.matches() == true)
        {
            return StringUtils.COALESCE(matcher.group(1), "").trim();
        }
        return "";
    }
}
