package engine.ari.engine_main.modding.javascript;

import engine.ari.engine_main.*;
import engine.ari.engine_main.map.Map;
import engine.ari.engine_main.rendering.*;
import engine.ari.engine_main.entity.Character;
import engine.ari.engine_main.entity.Entity;

import javax.script.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Timer;
import java.util.function.Consumer;

public class javascript extends Engine {
    private engine.ari.engine_main.modding.javascript.js_engine js_engine;
    public Character character;
    private Input js_input = new Input();
    public ScriptEngineManager factory = new ScriptEngineManager();
    public ScriptEngine engine = factory.getEngineByName("nashorn");
    public javascript(String file) {
        js_engine = new js_engine() {
            @Override
            public String getTitle() {
                if(file.length() > 0) {
                    return file;
                }
                return "[Unknown File]";
            }
            @Override
            public javascript.js_console getJSConsole() {
                return new js_console();
            }
        };
        js_engine.javascript = this;
    }
    public js_console js_console = new js_console();
    public ScriptEngine getEngine() {
        return engine;
    }
    /*public Texture texture(String texture) { // I didn't get the point of why I made this anyway
        try {
            return new Texture(texture);
        } catch(Exception e) {
            js_console.error(e.toString() + ": " + e.getMessage());
        }
        return null;
    }*/
    public Object eval(String eval, String file, Boolean execMain) {
        if(file == null) {
            js_console.warn("TS file is null? This isn't suppose to happen...");
            return null;
        }
        String func = file.substring(0, file.indexOf("."));
        try {
            //eval = eval.replace("com.ariEngine", ""); // lol
            eval = eval.replace("new Material", "new com.badlogic.gdx.graphics.g3d.Material");
            eval = eval.replace("ColorAttribute.", "com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute.");
            eval = eval.replace("new Texture", "new com.badlogic.gdx.graphics.Texture");
            eval = eval.replace("Color.", "com.badlogic.gdx.graphics.Color.");
            eval = eval.replace("Button", "com.badlogic.gdx.scenes.scene2d.ui.Button");
            eval = eval.replace("Runnable", "java.lang.Runnable");
            engine.eval(eval);
            if(execMain) {
                Invocable invocable = (Invocable) engine;
                return invocable.invokeFunction("main_"+func);
            }
        } catch (ScriptException e) {
            js_console.error(e.toString());
        } catch (NoSuchMethodException e) {
            if(e.toString().contains("No such function"))
                js_console.warn("There is no main_"+func+" function located in the script, ignoring instead...");
            else
                js_console.error(e.toString() + ", " + e.getMessage());
        }
        return null;
    }

    private static Boolean require(String file) {

        return false;
    }

    public static class Script {
        public Script(Object a) {
            Console.warn(a);
        }
    }

    public static class js_timer extends Engine {
        private Timer timer = new Timer();
        public int tick = 0;
        //public js_timer() {
        //    js_console.log(new Date().getTime() + ", created a new Timer");
        //}
        public void start(final long delay, Integer ticks, Runnable runnable) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(ticks != null && (ticks != 0) && (tick >= ticks)) {
                        this.cancel();
                        return;
                    }
                    runnable.run();
                    tick++;
                }
            }, 1, delay);
        }
        public void end() {
            timer.cancel();
        }
    }

    public class js_console {
        private Console c;
        public void open_external() { c.open_external(); }
        public void close_external() { c.close_external(); }
        public void log(Object ...objects) {
            String string = "";
            for(int i=0;i < objects.length;i++) {
                string = string + objects[i] + " ";
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String time = (localDateTime.getHour() > 12 ? localDateTime.getHour()-12 : localDateTime.getHour())+":"+localDateTime.getMinute()+":"+localDateTime.getSecond();
            String s = "   ["+time+ " " +js_engine.getTitle()+", LOG]: " + string;
            System.out.println(s);
            Console.addToStringList(s);
            //Gdx.files.local("./console.txt").writeString(s + "\r\n", true);
        }
        public void warn(Object ...objects) {
            String string = "";
            for(int i=0;i < objects.length;i++) {
                string = string + objects[i] + " ";
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String time = (localDateTime.getHour() > 12 ? localDateTime.getHour()-12 : localDateTime.getHour())+":"+localDateTime.getMinute()+":"+localDateTime.getSecond();
            String s = Console.getFontCode("YELLOW")+"   ["+time+ " " +js_engine.getTitle()+", WARN]"+ Console.getFontCode("RESET")+": " + string;
            System.out.println(s);
            Console.addToStringList(s);
            //Gdx.files.local("./console.txt").writeString(s + "\r\n", true);
        }
        public void error(Object ...objects) {
            String string = "";
            for(int i=0;i < objects.length;i++) {
                string = string + objects[i] + " ";
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String time = (localDateTime.getHour() > 12 ? localDateTime.getHour()-12 : localDateTime.getHour())+":"+localDateTime.getMinute()+":"+localDateTime.getSecond()+":";
            String s = Console.getFontCode("RED")+"   ["+time+ " " +js_engine.getTitle()+", ERROR]: " + string + Console.getFontCode("RESET");
            System.out.println(s);
            Console.addToStringList(s);
            //Gdx.files.local("./console.txt").writeString(s + "\r\n", true);
        }
    }

    public class Render extends engine.ari.engine_main.rendering.render {

    }
    public class ArrayList {
        public java.util.ArrayList create() {
            java.util.ArrayList<Object> arrayList = new java.util.ArrayList<>();
            return arrayList;
        }
    }

    public class exports {
        public java.util.ArrayList<Object> exports = new java.util.ArrayList<>();
        public void add() {

        }
        public Object get(Object obj) {
            if(exports.contains(obj)) {

            }
            return null;
        }
    }
    public void setBindings() {
        ArrayList arrayList = new ArrayList();
        Networking networking = new Networking();
        Render render = new Render();
        Textures textures = new Textures();
        js_timer timer = new js_timer();
        Map map = new Map();
        Entity entity = new Entity();
        Models models = new Models();
        Environment environment = new Environment();
        //Script script = javascript::Script;
        //DirectionalLight directionalLight = new DirectionalLight();
        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.remove("print");
        //bindings.put("Script", script);
        bindings.put("Dialog", new Dialog());
        bindings.put("Sound", new Sound());
        bindings.put("Networking", networking);
        bindings.put("Character", character);
        //bindings.put("Camera", character.camera);
        bindings.put("Render", render);
        bindings.put("Textures", textures);
        bindings.put("Engine", js_engine);
        bindings.put("Window", js_engine.Window);
        bindings.put("Console", js_engine.getJSConsole());
        bindings.put("ScreenText", new Screen_Text());
        bindings.put("Timer", timer);
        bindings.put("Map", map);
        bindings.put("Input", js_input);
        bindings.put("Entity", entity);
        bindings.put("Model", models);
        bindings.put("Environment", environment);
        //bindings.put("DirectionalLight", directionalLight);
        bindings.put("require", (Consumer<String>)javascript::require);
        bindings.put("ArrayList", arrayList);
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
    }
}
