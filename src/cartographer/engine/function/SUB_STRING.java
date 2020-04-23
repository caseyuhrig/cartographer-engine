package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class SUB_STRING implements Function
{
    public SUB_STRING()
    {
        // TODO Auto-generated constructor stub
    }


    @Override
    public String execute(final String value, final String... args)
    {
        if (StringUtils.IS_EMPTY(value) == true)
        {
            return "";
        }
        if (args != null && args.length == 1)
        {
            final String[] a = args[0].split(",");
            if (a != null && a.length == 2)
            {
                final Integer index = Integer.valueOf(a[0]) - 1;
                final Integer length = Integer.valueOf(a[1]);
                if (index > -1 && value.length() > index + length)
                {
                    return value.substring(index, index + length);
                }
            }
        }
        return value;
    }
}
