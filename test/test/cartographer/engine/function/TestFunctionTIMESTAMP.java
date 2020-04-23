package test.cartographer.engine.function;

import java.text.SimpleDateFormat;

import cartographer.engine.function.TIMESTAMP;
import junit.framework.TestCase;
import nuberplex.common.lang.DateTime;


public class TestFunctionTIMESTAMP extends TestCase
{
    public void test1()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, now.toTimestamp().toString(), "yyyyMMddhhmmss"));
    }


    public void test2()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, now.toTimestamp().toString(), ""));
    }


    public void test3()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, now.toTimestamp().toString(), (String[]) null));
    }


    public void test5()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        final String value = formatter.format(now.toJavaDate());
        // System.out.println("NOW: " + now.toTimestamp());
        // System.out.println("VAL: " + value);
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, now.toTimestamp().toString(), "MM-dd-yyyy"));
    }


    public void test6()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, "hello", (String[]) null));
    }


    public void test7()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, "", ""));
    }


    public void test8()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, (String) null, ""));
    }


    public void test9()
    {
        final DateTime now = DateTime.now();
        final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP.DEFAULT_FORMAT);
        final String value = formatter.format(now.toJavaDate());
        assertTrue(TestUtils.executeFunction(value, TIMESTAMP.class, "", (String[]) null));
    }
}
