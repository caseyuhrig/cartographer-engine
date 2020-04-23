package test.cartographer.engine.function;

import static org.junit.Assert.assertTrue;

import cartographer.engine.function.REPLACE;

import org.junit.jupiter.api.Test;


public class TestFunctionREPLACE
{
    @Test
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("abcde", REPLACE.class, "-abcde", "\"-\",\"\""));
    }


    @Test
    public void test2()
    {
        assertTrue(TestUtils.executeFunction("hello", REPLACE.class, "hello", "2,"));
    }


    @Test
    public void test21()
    {
        assertTrue(TestUtils.executeFunction("hello", REPLACE.class, "hello", ","));
    }


    @Test
    public void test22()
    {
        assertTrue(TestUtils.executeFunction("hello", REPLACE.class, "hello", "2"));
    }


    @Test
    public void test23()
    {
        assertTrue(TestUtils.executeFunction("hello", REPLACE.class, "hello", ""));
    }


    @Test
    public void test24()
    {
        assertTrue(TestUtils.executeFunction("hello", REPLACE.class, "hello", (String[]) null));
    }


    @Test
    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", REPLACE.class, "", ""));
    }


    @Test
    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", REPLACE.class, (String) null, ""));
    }


    @Test
    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", REPLACE.class, "", (String[]) null));
    }
}
