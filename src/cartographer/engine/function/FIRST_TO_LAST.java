package cartographer.engine.function;

import nuberplex.common.util.StringUtils;


public class FIRST_TO_LAST implements Function
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
            return value;
        }
        if (args.length > 0)
        {
            if (args.length == 1)
            {
                final String arg = args[0];
                final String[] chs = arg.split(",");
                if (chs.length == 1)
                {
                    final Integer beginIndex = value.indexOf(chs[0]);
                    if (beginIndex > -1)
                    {
                        return value.substring(beginIndex);
                    }
                }
                else if (chs.length == 2)
                {
                    final Integer beginIndex = value.indexOf(chs[0]);
                    final Integer lastIndex = value.lastIndexOf(chs[1]);
                    if (beginIndex > -1 && lastIndex > -1 && lastIndex > beginIndex)
                    {
                        return value.substring(beginIndex, lastIndex);
                    }
                }
                else
                {
                    throw new FunctionException("Cannot use more then 1 comma in arguemnt for FIRST_TO_LAST");
                }
            }
            else if (args.length == 2)
            {
                final Integer beginIndex = value.indexOf(args[0]);
                final Integer lastIndex = value.lastIndexOf(args[1]);
                if (beginIndex > -1 && lastIndex > -1 && lastIndex > beginIndex)
                {
                    return value.substring(beginIndex, lastIndex);
                }
            }
            else
            {
                throw new FunctionException("Cannot have more then 2 arguments in FIRST_TO_LAST");
            }
        }
        return value;
    }
}
