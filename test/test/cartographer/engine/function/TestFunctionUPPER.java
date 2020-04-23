package test.cartographer.engine.function;

import cartographer.engine.function.UPPER;
import junit.framework.TestCase;


public class TestFunctionUPPER extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("HELLO", UPPER.class, "hello", ""));
    }


    public void test2()
    {
        assertTrue(TestUtils.executeFunction("", UPPER.class, (String) null, (String[]) null));
    }


    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", UPPER.class, "", ""));
    }


    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", UPPER.class, (String) null, ""));
    }


    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", UPPER.class, "", (String[]) null));
    }


    public void test6()
    {
        assertTrue(TestUtils.executeFunction("HELLO", UPPER.class, "HELLO", (String[]) null));
    }
}
