package test.cartographer.engine.function;

import cartographer.engine.function.LITERAL_PREFIX;

import junit.framework.TestCase;


public class TestFunctionLITERAL_PREFIX extends TestCase
{
    /**
     * Make sure the LITERAL is appended to the incoming value;
     */
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("helloTEST", LITERAL_PREFIX.class, "TEST", "hello"));
    }


    /**
     * Check that null doesn't print out null.
     */
    public void test2()
    {
        assertTrue(TestUtils.executeFunction("TEST", LITERAL_PREFIX.class, null, "TEST"));
    }


    /**
     * Check that null doesn't print out null.
     */
    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", LITERAL_PREFIX.class, (String) null, (String[]) null));
    }
}
