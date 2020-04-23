package test.cartographer.engine.function;

import cartographer.engine.function.FIRST_TO_LAST;
import junit.framework.TestCase;


public class TestFunctionFIRST_TO_LAST extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("cde", FIRST_TO_LAST.class, "abcdefgh", "c,f"));
    }


    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", FIRST_TO_LAST.class, "", ""));
    }


    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", FIRST_TO_LAST.class, (String) null, ""));
    }


    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", FIRST_TO_LAST.class, "", (String[]) null));
    }
}
