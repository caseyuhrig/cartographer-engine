package test.cartographer.engine.function;

import cartographer.engine.function.LITERAL;
import junit.framework.TestCase;


public class TestFunctionLITERAL extends TestCase
{
    /**
     * Make sure the LITERAL is appended to the incoming value;
     */
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("helloTEST", LITERAL.class, "hello", "TEST"));
    }


    /**
     * Check that null doesn't print out null.
     */
    public void test2()
    {
        assertTrue(TestUtils.executeFunction("TEST", LITERAL.class, null, "TEST"));
    }


    /**
     * Check that null doesn't print out null.
     */
    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", LITERAL.class, (String) null, (String[]) null));
    }
}
