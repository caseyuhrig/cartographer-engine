package cartographer.engine;

import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;


public class Script
{
    public Script()
    {
        // TODO Auto-generated constructor stub
    }


    public static void main(final String... args)
    {
        final HashMap<String, String> source = new HashMap<String, String>();
        source.put("hello", "world");
        final HashMap<String, String> target = new HashMap<String, String>();
        System.out.println("target: " + target);
        // bring Rihno
        final Context cx = Context.enter();
        final Scriptable scope = cx.initStandardObjects();
        // scope.put("foo", scope, Context.toObject(foo, scope));
        ScriptableObject.putProperty(scope, "source", Context.javaToJS(source, scope));
        ScriptableObject.putProperty(scope, "target", Context.javaToJS(target, scope));
        ScriptableObject.putProperty(scope, "value", Context.javaToJS(new String(), scope));
        final Object result = cx
                .evaluateString(scope,
                                "if (source.get(\"hello\") == \"world_not\") value = \"hi\"; else value = \"bad\"; target.put(\"value\",\"abc\"); target;",
                                "<cmd>",
                                1,
                                null);
        System.out.println("R: " + result);
        System.out.println(Context.toString(result));
        final Object[] objs = cx.getElements(scope);
        for (final Object obj : objs)
        {
            System.out.println("O: " + obj);
        }
        // Object t = scope.get("target", scope);
        final Object t = ScriptableObject.getProperty(scope, "target");
        final Object t2 = Context.jsToJava(t, HashMap.class);
        System.out.println("T: " + t);
        System.out.println("T2: " + t2.getClass().getName());
        System.out.println("target: " + target);
    }
}
