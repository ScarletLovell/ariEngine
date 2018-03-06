package engine.ari.engine_main.modding.javascript;

import engine.ari.engine_main.Engine;
import jdk.nashorn.internal.objects.NativeJavaImporter;
import jdk.nashorn.internal.runtime.ScriptFunction;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class js_engine extends Engine {
    public static javascript javascript = null;
    public static String getFunction(ScriptFunction script) {
        String s = script.safeToString();
        s = s.substring(s.indexOf("{")+1, s.length());
        final String ss = s.substring(0, s.length()-1);
        return ss;
    }
    public String getTitle(){
        return null;
    }
    public javascript.js_console getJSConsole() {
        return null;
    }
    public Boolean isPaused() {
        return this.getPaused();
    }
    /*@Override
    public void pause(Boolean pause, Boolean was_already_paused) {
        if(pause == null || was_already_paused == null) {
            getJSConsole().error("Was not paused! Pause(Boolean pause, Boolean was_already_paused)");
            return;
        }
        super.pause(pause, was_already_paused);
    }*/
    public class FloatArray extends ArrayList<Float> {
        public Float[] toFloatArray() {
            Float[] floats = new Float[this.size()];
            for(int i=0;i < this.size();i++) {
                floats[i] = this.get(i);
            }
            return floats;
        }
    }
    public FloatArray NewFloat(float ...floats) {
        FloatArray array = new FloatArray();
        if(floats != null && floats.length > 0) {
            for(float arg : floats) {
                array.add(arg);
            }
        }
        return array;
    }
    public String getMethodsOfClass(Object cl) {
        Class c;

        try {
            c = Class.forName(cl.toString().substring(0, cl.toString().indexOf("@")));
        } catch (ClassNotFoundException e) {
            getJSConsole().error("Doesn't seem to be a class: " + cl.toString());
            return null;
        }
        String s = "\n\nSHOWING METHODS OF " + c.getSimpleName() + ":\n";
        for(Method method : c.getDeclaredMethods()) {
            s = s + method.getName();
            if(method.getParameters().length > 0) {
                s = s + " ( ";
                for (Parameter parameter : method.getParameters()) {
                    s = s + parameter.getType().getSimpleName();
                    s = s + ", ";
                }
                s = s.substring(0, s.length() - 2) + " )\n";
            } else {
                s = s + " ( )\n";
            }
        }
        s = s + "\n";
        return s;
    }
    public String getSuperClassOfClass(Object cl) {
        Class c;
        try {
            c = Class.forName(cl.toString().substring(0, cl.toString().indexOf("@")));
            return "Extends off " + c.getSuperclass().getSimpleName();
        } catch (ClassNotFoundException e) {
            getJSConsole().error("Doesn't seem to be a class: " + cl.toString());
            return null;
        }

    }
    public Runnable Run(ScriptFunction function) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                javascript.eval(function.toString(), null, null);
            }
        };
        run.run();
        return null;
    }
    public class Window {
        public int getHeight() {
            return Engine.windowHeight;
        }
        public int getWidth() {
            return Engine.windowWidth;
        }
    }
    public Window Window = new Window();
}
