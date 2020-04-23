package test.cartographer.engine.function;

import cartographer.engine.function.SUB_STRING;
import junit.framework.TestCase;


public class TestFunctionSUB_STRING extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("bcde", SUB_STRING.class, "abcdefgh", "2,4"));
    }


    public void test2()
    {
        assertTrue(TestUtils.executeFunction("hello", SUB_STRING.class, "hello", "2,"));
    }


    public void test21()
    {
        assertTrue(TestUtils.executeFunction("hello", SUB_STRING.class, "hello", ","));
    }


    public void test22()
    {
        assertTrue(TestUtils.executeFunction("hello", SUB_STRING.class, "hello", "2"));
    }


    public void test23()
    {
        assertTrue(TestUtils.executeFunction("hello", SUB_STRING.class, "hello", ""));
    }


    public void test24()
    {
        assertTrue(TestUtils.executeFunction("hello", SUB_STRING.class, "hello", (String[]) null));
    }


    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", SUB_STRING.class, "", ""));
    }


    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", SUB_STRING.class, (String) null, ""));
    }


    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", SUB_STRING.class, "", (String[]) null));
    }
}
