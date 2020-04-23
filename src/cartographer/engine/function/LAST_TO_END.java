package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class LAST_TO_END implements Function
{
    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        if (args == null || args.length == 0)
        {
            return value;
        }
        if (args.length > 0)
        {
            final Integer index = value.lastIndexOf(args[0]);
            return StringUtils.COALESCE(value.substring(index), "");
        }
        return value;
    }
}
