package engine.ari.engine_main;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import engine.ari.engine_main.rendering.Screen_Text;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Engine {
    public static Integer contrast = 1;
    public static engine.ari.engine_main.rendering.render render;
    //public static Textures textures = new Textures();
    public static Screen_Text screen_text = new Screen_Text();
    public static Boolean paused = false;
    public static Boolean was_already_paused = false;
    public static Integer windowHeight = null;
    public static Integer windowWidth = null;
    public Boolean getPaused() {
        return paused;
    }
    public Boolean getWasAlreadyPaused() { return was_already_paused; }
    public void pause(Boolean pause, Boolean wp) {
        Gdx.graphics.setContinuousRendering(pause);
    }

    public int getFPS() {
        return Gdx.app.getGraphics().getFramesPerSecond();
    }
    public void setIcon(String file) {
        // i actually have no idea how to do this
    }

    public void setTitle(String title) {
        Gdx.graphics.setTitle(title);
        Console.log("Set game title to: " + title);
    }
    public void setWindow(Integer width, Integer height) {
        Gdx.graphics.setWindowedMode(width, height);
        Console.log("Set window to " + width + "x" + height);
        windowHeight = height;
        windowWidth = width;
    }
    public void setCursor(Cursor cursor) {
        Gdx.graphics.setCursor(cursor);
    }
    public void VSync(Boolean b) {
        Gdx.graphics.setVSync(b);
        Console.log((b ? "Enabled" : "Disabled") + " VSync");
    }

    public static class config {
        private StringBuilder jsonFile = new StringBuilder();
        public void build() {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("config.json").getFile());
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    jsonFile.append(line).append("\n");
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        public Boolean contains(String value) {
            try {
                JSONObject obj = (JSONObject) new JSONParser().parse(jsonFile.toString());
                if(obj.containsKey(value))
                    return true;
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        public Object get(String value) {
            try {
                JSONObject obj = (JSONObject) new JSONParser().parse(jsonFile.toString());
                if(obj.containsKey(value))
                    return obj.get(value);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
