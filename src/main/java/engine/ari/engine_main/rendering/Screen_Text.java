package engine.ari.engine_main.rendering;

import engine.ari.engine_main.Console;

import java.util.ArrayList;
import java.util.HashMap;

public class Screen_Text {
    public static ArrayList<Integer> screen_text_list = new ArrayList<>();
    public static HashMap<Integer, String> screen_texts = new HashMap<>();
    //public static HashMap<Integer, Color> screen_text_color = new HashMap<>();
    public static HashMap<Integer, Integer> screen_text_x = new HashMap<>();
    public static HashMap<Integer, Integer> screen_text_y = new HashMap<>();
    public static HashMap<Integer, Float> screen_scale = new HashMap<>();
    public static HashMap<Integer, String> screen_text_font = new HashMap<>();
    public static HashMap<Integer, Boolean> screen_text_bold = new HashMap<>();
    public static HashMap<Integer, Boolean> screen_text_italic = new HashMap<>();

    public class screenTextClass {
        public Integer id;
        public screenTextClass setPosition(int x, int y) {
            screen_text_x.put(id, x);
            screen_text_y.put(id, y);
            return this;
        }
        public screenTextClass setText(String text) {
            screen_texts.put(id, text);
            return this;
        }
        /*public screenTextClass setColor(Color color) {
            screen_text_color.put(id, color);
            return this;
        }*/
        public screenTextClass setScale(float size) {
            screen_scale.put(id, size);
            return this;
        }
        public screenTextClass setBold(Boolean b) {
            screen_text_bold.put(id, b);
            screen_text_italic.put(id, false);
            return this;
        }
        public screenTextClass setItalic(Boolean b) {
            screen_text_bold.put(id, false);
            screen_text_italic.put(id, b);
            return this;
        }
        public void delete() {
            screen_text_list.remove(id);
            screen_texts.remove(id);
            //screen_text_color.remove(id);
            screen_text_x.remove(id);
            screen_text_y.remove(id);
        }
    }

    public screenTextClass addScreenText(Integer id) {
        screenTextClass screenTextClass = new screenTextClass();
        screen_text_font.put(id, "DroidSerif");
        screen_text_list.add(screenTextClass.id = id);
        Console.log("Created a new screen_text:", id);
        Console.warn("No font was given, set to default: \"DroidSerif\"");
        return screenTextClass;
    }
    public screenTextClass addScreenText(Integer id, String font) {
        screenTextClass screenTextClass = new screenTextClass();
        screen_text_font.put(id, font);
        screen_text_list.add(screenTextClass.id = id);
        Console.log("Created a new screen_text:", id);
        return screenTextClass;
    }
}
