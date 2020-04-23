package test.cartographer.engine.function;

import static org.junit.Assert.assertTrue;

import cartographer.engine.function.REGEX;

import org.junit.jupiter.api.Test;


public class TestFunctionREGEX
{
    @Test
    public void test1()
    {
        assertTrue(TestUtils.executeFunction("Casey Uhrig", REGEX.class, "Uhrig, Casey", "\"^(.*?), (.*?)$\",\"$2 $1\""));
    }


    @Test
    public void test11()
    {
        assertTrue(TestUtils.executeFunction("Uhrig, Casey", REGEX.class, "Casey Uhrig", "\"^(.*?) (.*?)$\",\"$2, $1\""));
    }


    @Test
    public void test2()
    {
        assertTrue(TestUtils.executeFunction("", REGEX.class, (String) null, (String[]) null));
    }


    @Test
    public void test3()
    {
        assertTrue(TestUtils.executeFunction("", REGEX.class, "", ""));
    }


    @Test
    public void test4()
    {
        assertTrue(TestUtils.executeFunction("", REGEX.class, (String) null, ""));
    }


    @Test
    public void test5()
    {
        assertTrue(TestUtils.executeFunction("", REGEX.class, "", (String[]) null));
    }


    @Test
    public void test6()
    {
        assertTrue(TestUtils.executeFunction("HELLO", REGEX.class, "HELLO", (String[]) null));
    }


    @Test
    public void test7()
    {
        assertTrue(TestUtils.executeFunction("HELLO", REGEX.class, "ELLO", "\"^(.*?)$\",\"H$1\""));
    }


    @Test
    public void test8()
    {
        assertTrue(TestUtils.executeFunction("98683-1234", REGEX.class, "Vancouver, WA  98683-1234", "^.*?([0-9-]+)$"));
    }
}
