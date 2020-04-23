package test.cartographer.engine.function;

import cartographer.engine.function.LAST_TO_END;
import junit.framework.TestCase;


public class TestFunctionLAST_TO_END extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("baaa", LAST_TO_END.class, "aaabaaabaaabaaa", "b"));
    }


    public void test2()
    {
        assertTrue(TestUtils.executeFunction("a", LAST_TO_END.class, "baaa", "a"));
    }


    public void test3()
    {
        assertTrue(TestUtils.executeFunction("aaabaaabaaabaaa", LAST_TO_END.class, "aaabaaabaaabaaa", (String[]) null));
    }


    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", LAST_TO_END.class, null, "b"));
    }
}
