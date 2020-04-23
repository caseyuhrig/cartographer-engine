package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class UPPER implements Function
{
    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        return value.toUpperCase();
    }
}
