package test.cartographer.engine.function;

import static org.junit.Assert.assertTrue;

import cartographer.engine.function.BEGIN_TO_LAST;

import org.junit.jupiter.api.Test;


public class TestFunctionBEGIN_TO_LAST
{
    @Test
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("aaabaaabaaa", BEGIN_TO_LAST.class, "aaabaaabaaabaaa", "b"));
    }


    @Test
    public void test2()
    {
        assertTrue(TestUtils.executeFunction("aaabaaabaaabaaa", BEGIN_TO_LAST.class, "aaabaaabaaabaaa", ""));
    }


    @Test
    public void test3()
    {
        assertTrue(TestUtils.executeFunction("aaabaaabaaabaaa", BEGIN_TO_LAST.class, "aaabaaabaaabaaa", (String[]) null));
    }


    @Test
    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", BEGIN_TO_LAST.class, null, "b"));
    }
}
