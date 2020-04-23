package cartographer.engine.function;

import nuberplex.common.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class REGEX implements Function
{
    // private static Logger LOG = LogManager.getLogger(REGEX.class);
    @Override
    public String execute(final String value, final String... args)
    {
        if (value == null || value.isBlank())
        {
            return "";
        }
        if (args != null && args.length > 0)
        {
            // Parse comma quite delimited line.
            final String[] tokens = args[0].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            // for (final String t : tokens)
            // {
            // System.out.println("REGEX ARG TOKEN:> " + t);
            // }
            if (tokens.length > 0)
            {
                if (tokens.length == 1)
                {
                    final Matcher matcher = Pattern.compile(tokens[0]).matcher(value);
                    if (matcher.matches() == true)
                    {
                        return matcher.group(1);
                    }
                }
                else if (tokens.length == 2)
                {
                    final String pattern = StringUtils.TRIM(tokens[0], "\"");
                    final String text = StringUtils.TRIM(tokens[1], "\"");
                    // System.out.println("REGEX Pattern: " + pattern);
                    // System.out.println("REXEX Text: " + text);
                    return value.replaceAll(pattern, text).trim();
                }
                else
                {
                    throw new FunctionException("Found %d togens in REGEX argument, only 1 or 2 are accpted.");
                }
            }
        }
        return value;
    }
}
