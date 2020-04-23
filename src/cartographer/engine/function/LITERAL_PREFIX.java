package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class LITERAL_PREFIX implements Function
{
    @Override
    public String execute(final String value, final String... args)
    {
        if (args != null)
        {
            if (args.length > 0)
            {
                return StringUtils.COALESCE(args[0], "") + StringUtils.COALESCE(value, "");
            }
        }
        return StringUtils.COALESCE(value, "");
    }
}
