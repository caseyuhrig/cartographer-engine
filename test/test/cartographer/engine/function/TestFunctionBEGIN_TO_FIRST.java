package test.cartographer.engine.function;

import static org.junit.Assert.assertTrue;

import cartographer.engine.function.BEGIN_TO_FIRST;

import org.junit.jupiter.api.Test;


public class TestFunctionBEGIN_TO_FIRST
{
    @Test
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("aaa", BEGIN_TO_FIRST.class, "aaabaaabaaabaaa", "b"));
    }


    @Test
    public void test2()
    {
        assertTrue(TestUtils.executeFunction("aaabaaabaaabaaa", BEGIN_TO_FIRST.class, "aaabaaabaaabaaa", ""));
    }


    @Test
    public void test3()
    {
        assertTrue(TestUtils.executeFunction("aaabaaabaaabaaa", BEGIN_TO_FIRST.class, "aaabaaabaaabaaa", (String[]) null));
    }


    @Test
    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", BEGIN_TO_FIRST.class, null, "b"));
    }
}
