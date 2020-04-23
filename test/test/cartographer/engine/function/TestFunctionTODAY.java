package test.cartographer.engine.function;

import java.text.SimpleDateFormat;

import cartographer.engine.function.TODAY;
import junit.framework.TestCase;
import nuberplex.common.lang.DateTime;


public class TestFunctionTODAY extends TestCase
{
    public void test1()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TODAY.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TODAY.class, now.toTimestamp().toString(), "MM-dd-yyyy"));
    }


    public void test2()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TODAY.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TODAY.class, now.toTimestamp().toString(), ""));
    }


    public void test3()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TODAY.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TODAY.class, now.toTimestamp().toString(), (String[]) null));
    }


    public void test5()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, yyyy");
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TODAY.class, now.toTimestamp().toString(), "EEE, MMM d, yyyy"));
    }


    public void test6()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TODAY.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TODAY.class, "hello", (String[]) null));
    }


    public void test7()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TODAY.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TODAY.class, "", ""));
    }


    public void test8()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TODAY.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TODAY.class, (String) null, ""));
    }


    public void test9()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TODAY.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TODAY.class, "", (String[]) null));
    }
}
