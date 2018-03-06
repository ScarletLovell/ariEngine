package engine.ari.engine_main;

import java.util.*;
import java.util.List;

public class Input extends Engine {

    public enum keys {
        ANY_KEY,
        SPACE,
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, Y, X, Z,
        F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F12,
        NUM, NUM_0, NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8, NUM_9,
        NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9,
        PAGE_UP, PAGE_DOWN, SOFT_LEFT, SOFT_RIGHT,
        DOWN, UP, LEFT, RIGHT,
        ALT_LEFT, ALT_RIGHT, CONTROL_LEFT, CONTROL_RIGHT, SHIFT_LEFT, SHIFT_RIGHT,
        LEFT_BRACKET, RIGHT_BRACKET,
        BUTTON_A, BUTTON_B, BUTTON_C, BUTTON_X, BUTTON_Y, BUTTON_Z,
        BUTTON_CIRCLE, BUTTON_SELECT, BUTTON_START, BUTTON_THUMBL, BUTTON_THUMBR,
        BUTTON_R1, BUTTON_R2, BUTTON_L1, BUTTON_L2,
        DPAD_CENTER, DPAD_UP, DPAD_DOWN, DPAD_RIGHT, DPAD_LEFT,
        COLON, COMMA, PERIOD, SEMICOLON, STAR,
        GRAVE, TAB, APOSTROPHE, AT, BACKSPACE,
        MINUS, PLUS, POWER, POUND, SLASH, BACKSLASH,
        DEL, END, HOME, ENTER, FORWARD_DEL, INSERT,
        ESCAPE,
        UNKNOWN,
    }
    public Input() {
    }

    private static List<inputClass> inputs = new ArrayList<>();
    private List<String> pressingKeys = new ArrayList<>();
    private int getKeyFromGdx(String key) {
        /*int gdxKey = Keys.valueOf(key.toUpperCase());
        if(gdxKey == -1) {
            switch (key) {
                case "CTRL_LEFT":
                case "CONTROL_LEFT":
                case "LEFT_CTRL":
                case "LEFT_CONTROL":
                    return Keys.CONTROL_LEFT;
                case "CTRL_RIGHT":
                case "CONTROL_RIGHT":
                case "RIGHT_CTRL":
                case "RIGHT_CONTROL":
                    return Keys.CONTROL_RIGHT;
                default:
                    return -1;
            }
        } else
            return gdxKey;*/
        return -1;
    }
    public void input()
    { // LibGDX is very weird about inputs, and since this is the only reliable solution, here it is.
      // Releasing inputs is located down below in a listener method.
        if(paused)
            return;
        int keyPressing;
        boolean cont = true;
        for (inputClass inputClass : inputs) {
            int pressed = inputClass.pressed;
            String[] keys = inputClass.keys.toUpperCase().split(", ");
            keyPressing = 0;
            for (int i = 0; i < keys.length; i++) {
                int key;
                if((key = getKeyFromGdx(keys[i])) == -1) {
                    Console.error("Key '"+keys[i]+"' does not exist!");
                    continue;
                }
                //if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ANY_KEY)) {
                /*if (pressed < 2 && Gdx.input.isKeyPressed(key) || pressed == 2 && !inputClass.isBeingHeld) {
                    keyPressing++;
                } else {
                    cont = false;
                    break;
                }*/
                /*} else if(pressingKeys.contains(keys[i])) {
                    if(pressed == 2)
                        inputClass.runnable.run();
                    if(pressed == 1 && inputClass.isBeingHeld)
                        inputClass.isBeingHeld = false;
                    pressingKeys.remove(keys[i]);
                    cont = false;
                    break;
                }*/
            }
            if(!cont || keyPressing < keys.length) {
                continue;
            }
            if (pressed == 0 || pressed > 2 || pressed < 0) {
                inputClass.runnable.run();
            } else if (pressed == 1 && !inputClass.isBeingHeld) {
                inputClass.runnable.run();
            }
            inputClass.isBeingHeld = true;
        }
    }
    public class inputClass {
        public Boolean isBeingHeld = false;
        public String keys;
        public Integer pressed;
        public Runnable runnable;
        public inputClass setInput(Runnable runnable) {
            if(runnable == null) {
                Console.error("Error in setInput: Runnable is null");
                return this;
            }
            this.runnable = runnable;
            return this;
        }
        public inputClass setPressType(Integer pressed) {
            if(pressed > 2 || pressed < 0) {
                Console.warn("Press type for \'" + this.keys + "\' was reset due to improper int");
                pressed = 0;
            }
            this.pressed = pressed;
            return this;
        }
        public void remove() {
            inputs.remove(this);
        }
    }
    public String getInputList() {
        String list = "";
        if(inputs.size() > 0) {
            for (int i = 0; i < inputs.size(); i++) {
                inputClass inputClass = inputs.get(i);
                list = list + i + ": (" + inputClass.keys + ") - Press Type: " + inputClass.pressed;
                //if(i != inputs.size())
                    list = list + "\n\t";
            }
        } else
            list = list + "No modded inputs found";
        return list;
    }
    public inputClass onKeyPressed(String keys, Runnable runnable) {
        String[] key = keys.split(", ");
        for(int i=0;i < key.length;i++) {
            if(getKeyFromGdx(key[i]) == -1) {
                Console.error("Key \'"+key[i]+"\' is an invalid key from: "+keys);
                return null;
            }
        }
        inputClass inputClass = new inputClass();
        inputClass.keys = keys;
        inputClass.runnable = runnable;
        inputClass.pressed = 0;
        inputs.add(inputClass);
        return inputClass;
    }
    public inputClass onKeyPressed(keys keys, Runnable runnable) {
        String key = keys.toString();
        if(getKeyFromGdx(key) == -1) {
            Console.error("Key \'"+key+"\' is an invalid key");
            return null;
        }
        inputClass inputClass = new inputClass();
        inputClass.keys = key;
        inputClass.runnable = runnable;
        inputClass.pressed = 0;
        inputs.add(inputClass);
        return inputClass;
    }
    public inputClass onKeyJustPressed(String keys, Runnable runnable) {
        String[] key = keys.split(", ");
        for(int i=0;i < key.length;i++) {
            if(getKeyFromGdx(key[i]) == -1) {
                Console.error("Key \'"+key[i]+"\' is invalid for: "+keys);
                return null;
            }
        }
        inputClass inputClass = new inputClass();
        inputClass.keys = keys;
        inputClass.runnable = runnable;
        inputClass.pressed = 1;
        inputs.add(inputClass);
        return inputClass;
    }
    /*public inputClass onKeyJustPressed(int keys, Runnable runnable) {
        String key = Keys.toString(keys);
        inputClass inputClass = new inputClass();
        inputClass.keys = key;
        inputClass.runnable = runnable;
        inputClass.pressed = 1;
        inputs.add(inputClass);
        return inputClass;
    }*/
    public inputClass onKeyReleased(String keys, Runnable runnable) {
        String[] key = keys.split(", ");
        for(int i=0;i < key.length;i++) {
            if(getKeyFromGdx(key[i]) == -1) {
                Console.error("Key \'"+key[i]+"\' is invalid for: "+keys);
                return null;
            }
        }
        inputClass inputClass = new inputClass();
        inputClass.keys = keys;
        inputClass.runnable = runnable;
        inputClass.pressed = 2;
        inputs.add(inputClass);
        return inputClass;
    }
    /*public inputClass onKeyReleased(int keys, Runnable runnable) {
        String key = Keys.toString(keys);
        inputClass inputClass = new inputClass();
        inputClass.keys = key;
        inputClass.runnable = runnable;
        inputClass.pressed = 2;
        inputs.add(inputClass);
        return inputClass;
    }*/
    private static List<Runnable> mouseWheelUpInput = new ArrayList<>();
    public void onMouseWheelUp(Runnable runnable) {
        mouseWheelUpInput.add(runnable);
    }
    private static List<Runnable> mouseWheelDownInput = new ArrayList<>();
    public void onMouseWheelDown(Runnable runnable) {
        mouseWheelDownInput.add(runnable);
    }
    public void removeInput(Integer id) {
        if(inputs.size() < id || id > inputs.size()) {
            Console.error("No such input id " + id + ", instead use getInputList() to see all inputs");
            return;
        }
        inputs.get(id).remove();
    }

    public boolean scrolled(int amount) {
        if(amount == -1) {
            for(Runnable runnable : mouseWheelUpInput) {
                runnable.run();
            }
        } else if(amount == 1) {
            for(Runnable runnable : mouseWheelDownInput) {
                runnable.run();
            }
        }
        return false;
    }

    public boolean keyDown(int keyCode) {  return false; }
    public boolean keyUp(int keycode) {
        for (inputClass inputClass : inputs) {
            if(inputClass.pressed < 1)
                return false;
            int pressed = inputClass.pressed;
            String[] keys = inputClass.keys.toUpperCase().split(", ");
            for (int i = 0; i < keys.length; i++) {
                if(getKeyFromGdx(keys[i]) == -1) {
                    Console.error("Key '"+keys[i]+"' does not exist!");
                    continue;
                }
                if(pressed == 2)
                    inputClass.runnable.run();
                inputClass.isBeingHeld = false;
                pressingKeys.remove(keys[i]);
            }
        }
        return false;
    }

    // Could be useful for in-game camera movement.
    public boolean mouseMoved(int x, int y) { return false; }

    // I'll use this later for text boxes, but not now.
    public boolean keyTyped(char character) { return false; }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
}
