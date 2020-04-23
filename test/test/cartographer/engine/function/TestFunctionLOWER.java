package test.cartographer.engine.function;

import cartographer.engine.function.LOWER;
import junit.framework.TestCase;


public class TestFunctionLOWER extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("hello", LOWER.class, "HELLO", ""));
    }


    public void test2()
    {
        assertTrue(TestUtils.executeFunction("", LOWER.class, (String) null, (String[]) null));
    }


    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", LOWER.class, "", ""));
    }


    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", LOWER.class, (String) null, ""));
    }


    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", LOWER.class, "", (String[]) null));
    }


    public void test6()
    {
        assertTrue(TestUtils.executeFunction("hello", LOWER.class, "hello", (String[]) null));
    }
}
