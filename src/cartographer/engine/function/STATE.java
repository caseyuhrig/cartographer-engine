package cartographer.engine.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nuberplex.common.util.StringUtils;


public class STATE implements Function
{
    private static final Pattern PATTERN_STATE = Pattern.compile("^.*?,(?:[ ]+)?([A-z]+{2}).*?$");


    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        final Matcher matcher = PATTERN_STATE.matcher(value);
        if (matcher.matches() == true)
        {
            return StringUtils.COALESCE(matcher.group(1), "").trim();
        }
        return "";
    }
}
