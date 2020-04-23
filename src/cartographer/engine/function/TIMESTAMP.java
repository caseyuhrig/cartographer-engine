package cartographer.engine.function;

import java.text.SimpleDateFormat;

import nuberplex.common.lang.DateTime;
import nuberplex.common.util.StringUtils;


public class TIMESTAMP implements Function
{
    public static String DEFAULT_FORMAT = "yyyyMMddhhmmss";


    @Override
    public String execute(final String value, final String... args)
    {
        final DateTime now = DateTime.now();
        if (args != null && args.length == 1)
        {
            if (StringUtils.IS_NOT_EMPTY(args[0]) == true)
            {
                return new SimpleDateFormat(args[0]).format(now.toJavaDate());
            }
        }
        return new SimpleDateFormat(DEFAULT_FORMAT).format(now.toJavaDate());
    }
}
