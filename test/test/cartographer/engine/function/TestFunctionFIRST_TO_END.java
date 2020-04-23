package test.cartographer.engine.function;

import cartographer.engine.function.FIRST_TO_END;
import junit.framework.TestCase;


public class TestFunctionFIRST_TO_END extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("defgh", FIRST_TO_END.class, "abcdefgh", "d"));
    }


    public void test10()
    {
        assertTrue(TestUtils.executeFunction("", FIRST_TO_END.class, (String) null, (String[]) null));
    }


    public void test11()
    {
        assertTrue(TestUtils.executeFunction("", FIRST_TO_END.class, (String) null, "d"));
    }


    public void test12()
    {
        assertTrue(TestUtils.executeFunction("abc", FIRST_TO_END.class, "abc", (String[]) null));
    }
}
