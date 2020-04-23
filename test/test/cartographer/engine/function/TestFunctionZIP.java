package test.cartographer.engine.function;

import cartographer.engine.function.ZIP;
import junit.framework.TestCase;


public class TestFunctionZIP extends TestCase
{
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("98683", ZIP.class, "Vancouver, WA  98683", (String[]) null));
    }


    public void test11()
    {
        assertTrue(TestUtils.executeFunction("986831234", ZIP.class, "Vancouver, WA  986831234", (String[]) null));
    }


    public void test12()
    {
        assertTrue(TestUtils.executeFunction("986831234", ZIP.class, "Vancouver, WA 986831234", (String[]) null));
    }


    public void test13()
    {
        assertTrue(TestUtils.executeFunction("98683", ZIP.class, "Vancouver, WA 98683", (String[]) null));
    }


    public void test2()
    {
        assertTrue(TestUtils.executeFunction("98683-1234", ZIP.class, "Vancouver, WA  98683-1234", (String[]) null));
    }


    public void test3()
    {
        assertTrue(TestUtils.executeFunction("98683-1234", ZIP.class, "Vancouver,WA98683-1234", (String[]) null));
    }


    public void test31()
    {
        assertTrue(TestUtils.executeFunction("98683-1234", ZIP.class, "Vancouver ,WA98683-1234", (String[]) null));
    }


    public void test32()
    {
        assertTrue(TestUtils.executeFunction("98683", ZIP.class, " Vancouver,WA98683", (String[]) null));
    }


    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", ZIP.class, null, "doesn't take parameters anyways"));
    }


    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", ZIP.class, null, (String[]) null));
    }
}
