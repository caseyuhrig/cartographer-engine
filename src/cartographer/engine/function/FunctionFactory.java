package cartographer.engine.function;

import java.util.HashMap;


public class FunctionFactory
{
    private final HashMap<String, Function> functions;
    private static FunctionFactory DEFAULT;


    public static FunctionFactory DEFAULT()
    {
        if (DEFAULT == null)
        {
            DEFAULT = new FunctionFactory();
        }
        return DEFAULT;
    }


    public FunctionFactory()
    {
        super();
        functions = new HashMap<>();
        functions.put("BEGIN_TO_FIRST", new BEGIN_TO_FIRST());
        functions.put("BEGIN_TO_LAST", new BEGIN_TO_LAST());
        functions.put("FIRST_TO_END", new FIRST_TO_END());
        functions.put("LAST_TO_END", new LAST_TO_END());
        functions.put("FIRST_TO_LAST", new FIRST_TO_LAST());
        functions.put("SUB_STRING", new SUB_STRING());
        functions.put("REPLACE", new REPLACE());
        functions.put("LITERAL", new LITERAL());
        functions.put("LITERAL_PREFIX", new LITERAL_PREFIX());
        functions.put("REGEX", new REGEX());
        functions.put("CITY", new CITY());
        functions.put("STATE", new STATE());
        functions.put("ZIP", new ZIP());
        functions.put("TODAY", new TODAY());
        functions.put("TIMESTAMP", new TIMESTAMP());
        functions.put("LOWER", new LOWER());
        functions.put("UPPER", new UPPER());
        functions.put("FIRST", null);
        functions.put("LAST", null);
        functions.put("MIDDLE", null);
        functions.put("PREFIX", null);
        functions.put("SUFFIX", null);
        functions.put("COMPANY_NAME", null);
    }


    public Function getFunction(final String identifier)
    {
        final Function function = functions.get(identifier);
        if (function == null)
        {
            throw new FunctionException("Function: %s does not exist.", identifier);
        }
        return function;
    }
}
