package test.cartographer.engine.function;

import cartographer.engine.function.Function;
import cartographer.engine.function.FunctionException;
import cartographer.engine.function.FunctionFactory;


public class TestUtils
{
    public static Boolean executeFunction(final String correctResult,
                                          final Class<?> clazz,
                                          final String value,
                                          final String... args)
    {
        final Function function = FunctionFactory.DEFAULT().getFunction(clazz.getSimpleName());
        final String result = function.execute(value, args);
        if (result == null)
        {
            throw new FunctionException(String.format("NULL reuslt from function %", clazz.getSimpleName()));
        }
        if (result.equals(correctResult) == false)
        {
            System.out.println(String.format("%s: %s = %s", clazz.getSimpleName(), result, correctResult));
            return false;
        }
        return true;
    }
}
