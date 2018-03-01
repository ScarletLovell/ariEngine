module input {
    export interface inputKeys {
    // https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/Input.Keys.html
    // Some of these button names I don't like tbh, I might change them a bit
    // also i'm not making all of the numbers per key, maybe another time, not any time soon
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
    export interface inputClass {
        runnable; pressed; key;
        setInput(runnable: Runnable): inputClass;
        setPressType(Boolean): inputClass;
            remove();
    }
    export interface Input {
        keys: inputKeys;
        onKeyPressed(Key: number, runnable: Runnable): inputClass;
        onKeyPressed(Key: string, runnable: Runnable): inputClass;
        onKeyJustPressed(Key: number, runnable: Runnable): inputClass;
        onKeyJustPressed(Key: string, runnable: Runnable): inputClass;
        onKeyReleased(Key: number, runnable: Runnable): inputClass;
        onKeyReleased(Key: string, runnable: Runnable): inputClass;
        onMouseWheelUp(runnable: Runnable): void;
        onMouseWheelDown(runnable: Runnable): void;
        getInputList(): string;
    }
}