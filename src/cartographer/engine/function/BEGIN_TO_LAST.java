package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class BEGIN_TO_LAST implements Function
{
    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        if (args == null || args.length < 1)
        {
            return value;
        }
        if (StringUtils.IS_NOT_EMPTY(args[0]) == true)
        {
            final Integer index = value.lastIndexOf(args[0]);
            if (index > -1)
            {
                return value.substring(0, index);
            }
        }
        return value;
    }
}
