package engine.ari.engine_main;

import engine.ari.engine_main.external.External_Console;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Console {
    private static External_Console external = new External_Console();
    public static PrintStream error;
    public static PrintWriter writer;

    public Console() {
        if(error == null) {
            try {
                error = new PrintStream(new File("console.log")) {
                    @Override
                    public void println(String msg) {
                        Console.log(msg);
                        super.println(msg);
                    }
                };
            } catch (FileNotFoundException e) {
                Console.error("err creating PrintStream, this shouldn't happen!");
            }
        }
        if(writer == null) {
            try {
                writer = new PrintWriter("console.log", "UTF-8");
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                Console.error("err creating PrintWriter, this shouldn't happen!");
            }
        }
    }



    public static void open_external() {
        external.Initialize();
    }
    public static void close_external() {
        external.Deinitalize();
    }

    public static void addToStringList(String s) {
        external.addToConsole(s);
    }

    public String convertColorFromAnsi(String string) {
        string = "<font color=\"white\" size=\"20\">" + string + "</font>";
        string = string.replace("\u001B[33m", "<font color=\"yellow\" size=\"20\">");
        /*switch(string) {
            case "\u001B[30m": return Color.BLACK;
            case "\u001B[31m": return Color.RED;
            case "\u001B[32m": return Color.GREEN;
            case "\u001B[33m": return Color.YELLOW;
            case "\u001B[34m": return Color.BLUE;
            case "\u001B[35m": return new Color(145, 73, 180);
            case "\u001B[36m": return Color.CYAN;
            case "\u001B[37m": return Color.WHITE;
            case "\u001B[0m": return Color.WHITE;
            default: return Color.WHITE;
        }*/
        return string;
    }
    private static String returnFontCodes(String[] codes) {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("ITALIC", "\u001B[3m");
        hash.put("UNDERLINE", "\u001B[4m");
        hash.put("BLINK", "\u001B[5m");
        hash.put("BLACK", "\u001B[30m");
        hash.put("RED", "\u001B[31m");
        hash.put("GREEN", "\u001B[32m");
        hash.put("YELLOW", "\u001B[33m");
        hash.put("BLUE", "\u001B[34m");
        hash.put("PURPLE", "\u001B[35m");
        hash.put("CYAN", "\u001B[36m");
        hash.put("WHITE", "\u001B[37m");
        hash.put("RESET", "\u001B[0m");
        String code = "";
        for(int i=0;i < codes.length;i++) {
            code += hash.get(codes[i].toUpperCase());
        }
        return code;
    }

    private static String stripFontCodes(String string) {
        String c = "3 4 5 30 31 32 33 34 35 36 37 0";
        for(String code : c.split(" ")) {
            string = string.replace("\u001B["+code+"m", "");
        }
        return string;
    }
    private static String prepareStringForLog(String string) {
        string = string.replace("\u001B", "    ");
        return string;
    }

    public static String getFontCode(String... codes) {
        return returnFontCodes(codes);
    }

    public static void log(Object ...objects) {
        String string = "";
        for(int i=0;i < objects.length;i++) {
            string = string + objects[i] + " ";
        }
        String s = "[LOG]: " + string;
        System.out.println(s);
        addToStringList(s);

        s = prepareStringForLog(stripFontCodes(s));

        writer.println(s);
        //Gdx.files.local("./console.txt").writeString(s + "\r\n", true);
    }
    public static void warn(Object ...objects) {
        String string = "";
        for(int i=0;i < objects.length;i++) {
            string = string + objects[i] + " ";
        }
        String s = getFontCode("YELLOW")+"[WARN]"+getFontCode("RESET")+": " + string;
        System.out.println(s);
        addToStringList(s);

        s = prepareStringForLog(stripFontCodes(s));
        writer.println(s);
    }
    public static void error(Object ...objects) {
        String string = "";
        for(int i=0;i < objects.length;i++) {
            string = string + objects[i] + " ";
        }
        String s = getFontCode("RED")+"[ERROR]: " + string + getFontCode("RESET");
        System.out.print(s + "\n");
        addToStringList(s);

        s = prepareStringForLog(stripFontCodes(s));
        writer.println(s);
    }
}
