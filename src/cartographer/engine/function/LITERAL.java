package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class LITERAL implements Function
{
    @Override
    public String execute(final String value, final String... args)
    {
        if (args != null)
        {
            if (args.length > 0)
            {
                return StringUtils.COALESCE(value, "") + StringUtils.COALESCE(args[0], "");
            }
        }
        return StringUtils.COALESCE(value, "");
    }
}
