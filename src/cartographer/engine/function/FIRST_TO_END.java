package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class FIRST_TO_END implements Function
{
    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        if (args == null)
        {
            return StringUtils.COALESCE(value, "");
        }
        if (args.length > 0)
        {
            final Integer index = value.indexOf(args[0]);
            if (index > -1)
            {
                return value.substring(index).trim();
            }
        }
        return value;
    }
}
