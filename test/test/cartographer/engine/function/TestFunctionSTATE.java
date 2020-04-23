package test.cartographer.engine.function;

import cartographer.engine.function.STATE;
import junit.framework.TestCase;


public class TestFunctionSTATE extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("WA", STATE.class, "Vancouver, WA  98683", (String[]) null));
    }


    public void test2()
    {
        assertTrue(TestUtils.executeFunction("WA", STATE.class, "Vancouver, WA  98683-1234", (String[]) null));
    }


    public void test3()
    {
        assertTrue(TestUtils.executeFunction("WA", STATE.class, "Vancouver,WA98683-1234", (String[]) null));
    }


    public void test31()
    {
        assertTrue(TestUtils.executeFunction("WA", STATE.class, "Vancouver ,WA98683-1234", (String[]) null));
    }


    public void test32()
    {
        assertTrue(TestUtils.executeFunction("WA", STATE.class, " Vancouver,WA98683", (String[]) null));
    }


    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", STATE.class, null, "doesn't take parameters anyways"));
    }


    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", STATE.class, null, (String[]) null));
    }
}
