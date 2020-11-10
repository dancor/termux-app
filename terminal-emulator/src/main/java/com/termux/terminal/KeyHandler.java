package com.termux.terminal;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.KeyEvent.KEYCODE_BREAK;
import static android.view.KeyEvent.KEYCODE_DEL;
import static android.view.KeyEvent.KEYCODE_DPAD_CENTER;
import static android.view.KeyEvent.KEYCODE_DPAD_DOWN;
import static android.view.KeyEvent.KEYCODE_DPAD_LEFT;
import static android.view.KeyEvent.KEYCODE_DPAD_RIGHT;
import static android.view.KeyEvent.KEYCODE_DPAD_UP;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.KeyEvent.KEYCODE_ESCAPE;
import static android.view.KeyEvent.KEYCODE_F1;
import static android.view.KeyEvent.KEYCODE_F10;
import static android.view.KeyEvent.KEYCODE_F11;
import static android.view.KeyEvent.KEYCODE_F12;
import static android.view.KeyEvent.KEYCODE_F2;
import static android.view.KeyEvent.KEYCODE_F3;
import static android.view.KeyEvent.KEYCODE_F4;
import static android.view.KeyEvent.KEYCODE_F5;
import static android.view.KeyEvent.KEYCODE_F6;
import static android.view.KeyEvent.KEYCODE_F7;
import static android.view.KeyEvent.KEYCODE_F8;
import static android.view.KeyEvent.KEYCODE_F9;
import static android.view.KeyEvent.KEYCODE_FORWARD_DEL;
import static android.view.KeyEvent.KEYCODE_INSERT;
import static android.view.KeyEvent.KEYCODE_MOVE_END;
import static android.view.KeyEvent.KEYCODE_MOVE_HOME;
import static android.view.KeyEvent.KEYCODE_NUMPAD_0;
import static android.view.KeyEvent.KEYCODE_NUMPAD_1;
import static android.view.KeyEvent.KEYCODE_NUMPAD_2;
import static android.view.KeyEvent.KEYCODE_NUMPAD_3;
import static android.view.KeyEvent.KEYCODE_NUMPAD_4;
import static android.view.KeyEvent.KEYCODE_NUMPAD_5;
import static android.view.KeyEvent.KEYCODE_NUMPAD_6;
import static android.view.KeyEvent.KEYCODE_NUMPAD_7;
import static android.view.KeyEvent.KEYCODE_NUMPAD_8;
import static android.view.KeyEvent.KEYCODE_NUMPAD_9;
import static android.view.KeyEvent.KEYCODE_NUMPAD_ADD;
import static android.view.KeyEvent.KEYCODE_NUMPAD_COMMA;
import static android.view.KeyEvent.KEYCODE_NUMPAD_DIVIDE;
import static android.view.KeyEvent.KEYCODE_NUMPAD_DOT;
import static android.view.KeyEvent.KEYCODE_NUMPAD_ENTER;
import static android.view.KeyEvent.KEYCODE_NUMPAD_EQUALS;
import static android.view.KeyEvent.KEYCODE_NUMPAD_MULTIPLY;
import static android.view.KeyEvent.KEYCODE_NUMPAD_SUBTRACT;
import static android.view.KeyEvent.KEYCODE_NUM_LOCK;
import static android.view.KeyEvent.KEYCODE_PAGE_DOWN;
import static android.view.KeyEvent.KEYCODE_PAGE_UP;
import static android.view.KeyEvent.KEYCODE_SPACE;
import static android.view.KeyEvent.KEYCODE_SYSRQ;
import static android.view.KeyEvent.KEYCODE_TAB;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;
import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;

public final class KeyHandler {

    public static final int KEYMOD_ALT = 0x80000000;
    public static final int KEYMOD_CTRL = 0x40000000;
    public static final int KEYMOD_SHIFT = 0x20000000;

    private static final Map<String, Integer> TERMCAP_TO_KEYCODE = new HashMap<>();

    static {
        // terminfo: http://pubs.opengroup.org/onlinepubs/7990989799/xcurses/terminfo.html
        // termcap: http://man7.org/linux/man-pages/man5/termcap.5.html
        TERMCAP_TO_KEYCODE.put("%i", KEYMOD_SHIFT | KEYCODE_DPAD_RIGHT);
        TERMCAP_TO_KEYCODE.put("#2", KEYMOD_SHIFT | KEYCODE_MOVE_HOME); // Shifted home
        TERMCAP_TO_KEYCODE.put("#4", KEYMOD_SHIFT | KEYCODE_DPAD_LEFT);
        TERMCAP_TO_KEYCODE.put("*7", KEYMOD_SHIFT | KEYCODE_MOVE_END); // Shifted end key

        TERMCAP_TO_KEYCODE.put("k1", KEYCODE_F1);
        TERMCAP_TO_KEYCODE.put("k2", KEYCODE_F2);
        TERMCAP_TO_KEYCODE.put("k3", KEYCODE_F3);
        TERMCAP_TO_KEYCODE.put("k4", KEYCODE_F4);
        TERMCAP_TO_KEYCODE.put("k5", KEYCODE_F5);
        TERMCAP_TO_KEYCODE.put("k6", KEYCODE_F6);
        TERMCAP_TO_KEYCODE.put("k7", KEYCODE_F7);
        TERMCAP_TO_KEYCODE.put("k8", KEYCODE_F8);
        TERMCAP_TO_KEYCODE.put("k9", KEYCODE_F9);
        TERMCAP_TO_KEYCODE.put("k;", KEYCODE_F10);
        TERMCAP_TO_KEYCODE.put("F1", KEYCODE_F11);
        TERMCAP_TO_KEYCODE.put("F2", KEYCODE_F12);
        TERMCAP_TO_KEYCODE.put("F3", KEYMOD_SHIFT | KEYCODE_F1);
        TERMCAP_TO_KEYCODE.put("F4", KEYMOD_SHIFT | KEYCODE_F2);
        TERMCAP_TO_KEYCODE.put("F5", KEYMOD_SHIFT | KEYCODE_F3);
        TERMCAP_TO_KEYCODE.put("F6", KEYMOD_SHIFT | KEYCODE_F4);
        TERMCAP_TO_KEYCODE.put("F7", KEYMOD_SHIFT | KEYCODE_F5);
        TERMCAP_TO_KEYCODE.put("F8", KEYMOD_SHIFT | KEYCODE_F6);
        TERMCAP_TO_KEYCODE.put("F9", KEYMOD_SHIFT | KEYCODE_F7);
        TERMCAP_TO_KEYCODE.put("FA", KEYMOD_SHIFT | KEYCODE_F8);
        TERMCAP_TO_KEYCODE.put("FB", KEYMOD_SHIFT | KEYCODE_F9);
        TERMCAP_TO_KEYCODE.put("FC", KEYMOD_SHIFT | KEYCODE_F10);
        TERMCAP_TO_KEYCODE.put("FD", KEYMOD_SHIFT | KEYCODE_F11);
        TERMCAP_TO_KEYCODE.put("FE", KEYMOD_SHIFT | KEYCODE_F12);

        TERMCAP_TO_KEYCODE.put("kb", KEYCODE_DEL); // backspace key

        TERMCAP_TO_KEYCODE.put("kd", KEYCODE_DPAD_DOWN); // terminfo=kcud1, down-arrow key
        TERMCAP_TO_KEYCODE.put("kh", KEYCODE_MOVE_HOME);
        TERMCAP_TO_KEYCODE.put("kl", KEYCODE_DPAD_LEFT);
        TERMCAP_TO_KEYCODE.put("kr", KEYCODE_DPAD_RIGHT);

        // K1=Upper left of keypad:
        // t_K1 <kHome> keypad home key
        // t_K3 <kPageUp> keypad page-up key
        // t_K4 <kEnd> keypad end key
        // t_K5 <kPageDown> keypad page-down key
        TERMCAP_TO_KEYCODE.put("K1", KEYCODE_MOVE_HOME);
        TERMCAP_TO_KEYCODE.put("K3", KEYCODE_PAGE_UP);
        TERMCAP_TO_KEYCODE.put("K4", KEYCODE_MOVE_END);
        TERMCAP_TO_KEYCODE.put("K5", KEYCODE_PAGE_DOWN);

        TERMCAP_TO_KEYCODE.put("ku", KEYCODE_DPAD_UP);

        TERMCAP_TO_KEYCODE.put("kB", KEYMOD_SHIFT | KEYCODE_TAB); // termcap=kB, terminfo=kcbt: Back-tab
        TERMCAP_TO_KEYCODE.put("kD", KEYCODE_FORWARD_DEL); // terminfo=kdch1, delete-character key
        TERMCAP_TO_KEYCODE.put("kDN", KEYMOD_SHIFT | KEYCODE_DPAD_DOWN); // non-standard shifted arrow down
        TERMCAP_TO_KEYCODE.put("kF", KEYMOD_SHIFT | KEYCODE_DPAD_DOWN); // terminfo=kind, scroll-forward key
        TERMCAP_TO_KEYCODE.put("kI", KEYCODE_INSERT);
        TERMCAP_TO_KEYCODE.put("kN", KEYCODE_PAGE_UP);
        TERMCAP_TO_KEYCODE.put("kP", KEYCODE_PAGE_DOWN);
        TERMCAP_TO_KEYCODE.put("kR", KEYMOD_SHIFT | KEYCODE_DPAD_UP); // terminfo=kri, scroll-backward key
        TERMCAP_TO_KEYCODE.put("kUP", KEYMOD_SHIFT | KEYCODE_DPAD_UP); // non-standard shifted up

        TERMCAP_TO_KEYCODE.put("@7", KEYCODE_MOVE_END);
        TERMCAP_TO_KEYCODE.put("@8", KEYCODE_NUMPAD_ENTER);
    }

    static String getCodeFromTermcap(String termcap, boolean cursorKeysApplication, boolean keypadApplication) {
        Integer keyCodeAndMod = TERMCAP_TO_KEYCODE.get(termcap);
        if (keyCodeAndMod == null) return null;
        int keyCode = keyCodeAndMod;
        int keyMod = 0;
        if ((keyCode & KEYMOD_SHIFT) != 0) {
            keyMod |= KEYMOD_SHIFT;
            keyCode &= ~KEYMOD_SHIFT;
        }
        if ((keyCode & KEYMOD_CTRL) != 0) {
            keyMod |= KEYMOD_CTRL;
            keyCode &= ~KEYMOD_CTRL;
        }
        if ((keyCode & KEYMOD_ALT) != 0) {
            keyMod |= KEYMOD_ALT;
            keyCode &= ~KEYMOD_ALT;
        }
        return getCode(keyCode, keyMod, cursorKeysApplication, keypadApplication);
    }

    public static long last_key_time = -1;
    public static String prev_s = "";
    public static String lol(String s) {
        long cur_time = System.currentTimeMillis();
        if (cur_time < last_key_time + 100) return null;
        last_key_time = cur_time;
        if (prev_s == "") {
            if (s == "f") return " ";
            //((keyMode & KEYMOD_ALT) == 0) ? "" : "\033" +
            //(((keyMode & KEYMOD_CTRL) == 0) ? "\u007F" : "\u0008");
            if (s == "b") return "\u007F";
            if (s == "F") return "e";
            if (s == "B") return "t";
            prev_s += s;
        } else if (prev_s.equals("u")) {
            prev_s = "";
            if (s == "f") return "a";
            if (s == "b") return "b";
            if (s == "u") return "c";
            if (s == "d") return "d";
            if (s == "t") return "\033";
            if (s == "F") return "f";
            if (s == "B") return "g";
            if (s == "U") return "h";
            if (s == "D") return "i";
            if (s == "T") return "j";
        } else if (prev_s.equals("d")) {
            prev_s = "";
            if (s == "f") return "k";
            if (s == "b") return "l";
            if (s == "u") return "m";
            if (s == "d") return "n";
            if (s == "t") return "o";
            if (s == "F") return "p";
            if (s == "B") return "q";
            if (s == "U") return "r";
            if (s == "D") return "s";
            if (s == "T") return "z";
        } else if (prev_s.equals("t")) {
            prev_s = "";
            if (s == "f") return "u";
            if (s == "b") return "v";
            if (s == "u") return "w";
            if (s == "d") return "x";
            if (s == "t") return "y";
            if (s == "F") return "\033[C";
            if (s == "B") return "\033[D";
            if (s == "U") return "\033[A";
            if (s == "D") return "\033[B";
            if (s == "T") return "\r";
        } else if (prev_s.equals("U")) {
            prev_s = "";
            if (s == "f") return ".";
            if (s == "b") return ",";
            if (s == "u") prev_s = "Uu";
            if (s == "d") prev_s = "Ud";
            if (s == "t") prev_s = "Ut";
            if (s == "F") return "?";
            if (s == "B") return "T";
            if (s == "U") return "VU";
            if (s == "D") return "\033[Z";
            if (s == "T") prev_s = "UT";
        } else if (prev_s.equals("D")) {
            prev_s = "";
            if (s == "f") prev_s = "Df";
            if (s == "b") prev_s = "Db";
            if (s == "u") prev_s = "Du";
            if (s == "d") return "?";
            if (s == "t") return "?";
            if (s == "F") return "?";
            if (s == "B") prev_s = "DB";
            if (s == "U") return "?";
            if (s == "D") return "VD";
            if (s == "T") prev_s = "DT";
        } else if (prev_s.equals("T")) {
            prev_s = "";
            if (s == "f") return "1";
            if (s == "b") return "2";
            if (s == "u") return "3";
            if (s == "d") return "4";
            if (s == "t") return "5";
            if (s == "F") return "6";
            if (s == "B") return "7";
            if (s == "U") return "8";
            if (s == "D") return "9";
            if (s == "T") return "0";
        } else if (prev_s.equals("Uu")) {
            prev_s = "";
            if (s == "f") return "A";
            if (s == "b") return "B";
            if (s == "u") return "C";
            if (s == "d") return "D";
            if (s == "t") return "E";
            if (s == "F") return "F";
            if (s == "B") return "G";
            if (s == "U") return "H";
            if (s == "D") return "I";
            if (s == "T") return "J";
        } else if (prev_s.equals("Ud")) {
            prev_s = "";
            if (s == "f") return "K";
            if (s == "b") return "L";
            if (s == "u") return "M";
            if (s == "d") return "N";
            if (s == "t") return "O";
            if (s == "F") return "P";
            if (s == "B") return "Q";
            if (s == "U") return "R";
            if (s == "D") return "S";
            if (s == "T") return "Z";
        } else if (prev_s.equals("Ut")) {
            prev_s = "";
            if (s == "f") return "U";
            if (s == "b") return "V";
            if (s == "u") return "W";
            if (s == "d") return "X";
            if (s == "t") return "Y";
            if (s == "F") return "\033[1~";
            if (s == "B") return "\033[4~";
            if (s == "U") return "\033[5~";
            if (s == "D") return "\033[6~";
            if (s == "T") return "+";
        } else if (prev_s.equals("UT")) {
            prev_s = "";
            if (s == "f") return "\033OP";
            if (s == "b") return "\033OQ";
            if (s == "u") return "\033OR";
            if (s == "d") return "\033OS";
            if (s == "t") return "\033[15~";
            if (s == "F") return "\033[17~";
            if (s == "B") return "\033[18~";
            if (s == "U") return "\033[19~";
            if (s == "D") return "\033[20~";
            if (s == "T") return "\033[21~";
        } else if (prev_s.equals("Df")) {
            prev_s = "";
            if (s == "f") return "!";
            if (s == "b") return "@";
            if (s == "u") return "#";
            if (s == "d") return "$";
            if (s == "t") return "%";
            if (s == "F") return "^";
            if (s == "B") return "&";
            if (s == "U") return "*";
            if (s == "D") return "`";
            if (s == "T") return "~";
        } else if (prev_s.equals("Du")) {
            prev_s = "";
            if (s == "f") return ")";
            if (s == "b") return "(";
            if (s == "u") return "/";
            if (s == "d") return "\\";
            if (s == "t") return "?"; // the real "?"
            if (s == "F") return "]";
            if (s == "B") return "[";
            if (s == "U") return "-";
            if (s == "D") return "_";
            if (s == "T") return ";";
        } else if (prev_s.equals("Du")) {
            prev_s = "";
            if (s == "f") return "{";
            if (s == "b") return "}";
            if (s == "u") return "'";
            if (s == "d") return "|";
            if (s == "t") return "!";
            if (s == "F") return ">";
            if (s == "B") return "<";
            if (s == "U") return "\"";
            if (s == "D") return "=";
            if (s == "T") return ":";
        } else if (prev_s.equals("DB")) {
            prev_s = "";
            if (s == "f") return "?";
            if (s == "b") return "?";
            if (s == "u") prev_s = "DBu";
            if (s == "d") prev_s = "DBd";
            if (s == "t") prev_s = "DBt";
            if (s == "F") return "?";
            if (s == "B") return "?";
            if (s == "U") return "?";
            if (s == "D") return "?";
            if (s == "T") return "?";
        } else if (prev_s.equals("DT")) {
            prev_s = "";
            if (s == "f") return "\033[23~";
            if (s == "b") return "\033[24~";
            if (s == "u") return "?";
            if (s == "d") return "?";
            if (s == "t") return "?";
            if (s == "F") return "?";
            if (s == "B") return "?";
            if (s == "U") return "?";
            if (s == "D") return "?";
            if (s == "T") return "?";
        } else if (prev_s.equals("DBu")) {
            prev_s = "";
            if (s == "f") return "\001";
            if (s == "b") return "\002";
            if (s == "u") return "\003";
            if (s == "d") return "\004";
            if (s == "t") return "\005";
            if (s == "F") return "\006";
            if (s == "B") return "\007";
            if (s == "U") return "\010";
            if (s == "D") return "\011";
            if (s == "T") return "\012";
        } else if (prev_s.equals("DBd")) {
            prev_s = "";
            if (s == "f") return "\013";
            if (s == "b") return "\014";
            if (s == "u") return "\015";
            if (s == "d") return "\016";
            if (s == "t") return "\017";
            if (s == "F") return "\020";
            if (s == "B") return "\021";
            if (s == "U") return "\022";
            if (s == "D") return "\023";
            if (s == "T") return "\024";
        } else if (prev_s.equals("DBt")) {
            prev_s = "";
            if (s == "f") return "\025";
            if (s == "b") return "\026";
            if (s == "u") return "\027";
            if (s == "d") return "\030";
            if (s == "t") return "\031";
            if (s == "F") return "\032";
            if (s == "B") return "?";
            if (s == "U") return "?";
            if (s == "D") return "?";
            if (s == "T") return "?";
        } else {
            prev_s = "";
        }
        return null;
    }
    public static String getCode(int keyCode, int keyMode, boolean cursorApp, boolean keypadApplication) {
        switch (keyCode) {
            case KEYCODE_DPAD_RIGHT: return lol("f");
            case KEYCODE_DPAD_LEFT: return lol("b");
            case KEYCODE_DPAD_UP: return lol("u");
            case KEYCODE_DPAD_DOWN: return lol("d");
            case KEYCODE_ENTER: return lol("t");
            case KEYCODE_FORWARD_DEL: return lol("F");
            case KEYCODE_DEL: return lol("B");
            case KEYCODE_VOLUME_UP: return lol("U");
            case KEYCODE_VOLUME_DOWN: return lol("D");
            case KEYCODE_BACK: return lol("T");

            case KEYCODE_DPAD_CENTER:
                return "\015";

            case KEYCODE_MOVE_HOME:
                // Note that KEYCODE_HOME is handled by the system and never delivered to applications.
                // On a Logitech k810 keyboard KEYCODE_MOVE_HOME is sent by FN+LeftArrow.
                return (keyMode == 0) ? (cursorApp ? "\033OH" : "\033[H") : transformForModifiers("\033[1", keyMode, 'H');
            case KEYCODE_MOVE_END:
                return (keyMode == 0) ? (cursorApp ? "\033OF" : "\033[F") : transformForModifiers("\033[1", keyMode, 'F');

            // An xterm can send function keys F1 to F4 in two modes: vt100 compatible or
            // not. Because Vim may not know what the xterm is sending, both types of keys
            // are recognized. The same happens for the <Home> and <End> keys.
            // normal vt100 ~
            // <F1> t_k1 <Esc>[11~ <xF1> <Esc>OP *<xF1>-xterm*
            // <F2> t_k2 <Esc>[12~ <xF2> <Esc>OQ *<xF2>-xterm*
            // <F3> t_k3 <Esc>[13~ <xF3> <Esc>OR *<xF3>-xterm*
            // <F4> t_k4 <Esc>[14~ <xF4> <Esc>OS *<xF4>-xterm*
            // <Home> t_kh <Esc>[7~ <xHome> <Esc>OH *<xHome>-xterm*
            // <End> t_@7 <Esc>[4~ <xEnd> <Esc>OF *<xEnd>-xterm*
            case KEYCODE_F1:
                return (keyMode == 0) ? "\033OP" : transformForModifiers("\033[1", keyMode, 'P');
            case KEYCODE_F2:
                return (keyMode == 0) ? "\033OQ" : transformForModifiers("\033[1", keyMode, 'Q');
            case KEYCODE_F3:
                return (keyMode == 0) ? "\033OR" : transformForModifiers("\033[1", keyMode, 'R');
            case KEYCODE_F4:
                return (keyMode == 0) ? "\033OS" : transformForModifiers("\033[1", keyMode, 'S');
            case KEYCODE_F5:
                return transformForModifiers("\033[15", keyMode, '~');
            case KEYCODE_F6:
                return transformForModifiers("\033[17", keyMode, '~');
            case KEYCODE_F7:
                return transformForModifiers("\033[18", keyMode, '~');
            case KEYCODE_F8:
                return transformForModifiers("\033[19", keyMode, '~');
            case KEYCODE_F9:
                return transformForModifiers("\033[20", keyMode, '~');
            case KEYCODE_F10:
                return transformForModifiers("\033[21", keyMode, '~');
            case KEYCODE_F11:
                return transformForModifiers("\033[23", keyMode, '~');
            case KEYCODE_F12:
                return transformForModifiers("\033[24", keyMode, '~');

            case KEYCODE_SYSRQ:
                return "\033[32~"; // Sys Request / Print
            // Is this Scroll lock? case Cancel: return "\033[33~";
            case KEYCODE_BREAK:
                return "\033[34~"; // Pause/Break

            case KEYCODE_ESCAPE:
                return "\033";

            case KEYCODE_INSERT:
                return transformForModifiers("\033[2", keyMode, '~');

            case KEYCODE_PAGE_UP:
                return "\033[5~";
            case KEYCODE_PAGE_DOWN:
                return "\033[6~";
            case KEYCODE_NUM_LOCK:
                return "\033OP";

            case KEYCODE_SPACE:
                // If ctrl is not down, return null so that it goes through normal input processing (which may e.g. cause a
                // combining accent to be written):
                return ((keyMode & KEYMOD_CTRL) == 0) ? null : "\0";
            case KEYCODE_TAB:
                // This is back-tab when shifted:
                return (keyMode & KEYMOD_SHIFT) == 0 ? "\011" : "\033[Z";

            case KEYCODE_NUMPAD_ENTER:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'M') : "\n";
            case KEYCODE_NUMPAD_MULTIPLY:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'j') : "*";
            case KEYCODE_NUMPAD_ADD:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'k') : "+";
            case KEYCODE_NUMPAD_COMMA:
                return ",";
            case KEYCODE_NUMPAD_DOT:
                return keypadApplication ? "\033On" : ".";
            case KEYCODE_NUMPAD_SUBTRACT:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'm') : "-";
            case KEYCODE_NUMPAD_DIVIDE:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'o') : "/";
            case KEYCODE_NUMPAD_0:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'p') : "0";
            case KEYCODE_NUMPAD_1:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'q') : "1";
            case KEYCODE_NUMPAD_2:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'r') : "2";
            case KEYCODE_NUMPAD_3:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 's') : "3";
            case KEYCODE_NUMPAD_4:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 't') : "4";
            case KEYCODE_NUMPAD_5:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'u') : "5";
            case KEYCODE_NUMPAD_6:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'v') : "6";
            case KEYCODE_NUMPAD_7:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'w') : "7";
            case KEYCODE_NUMPAD_8:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'x') : "8";
            case KEYCODE_NUMPAD_9:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'y') : "9";
            case KEYCODE_NUMPAD_EQUALS:
                return keypadApplication ? transformForModifiers("\033O", keyMode, 'X') : "=";
        }
        return null;
    }

    private static String transformForModifiers(String start, int keymod, char lastChar) {
        int modifier;
        switch (keymod) {
            case KEYMOD_SHIFT:
                modifier = 2;
                break;
            case KEYMOD_ALT:
                modifier = 3;
                break;
            case (KEYMOD_SHIFT | KEYMOD_ALT):
                modifier = 4;
                break;
            case KEYMOD_CTRL:
                modifier = 5;
                break;
            case KEYMOD_SHIFT | KEYMOD_CTRL:
                modifier = 6;
                break;
            case KEYMOD_ALT | KEYMOD_CTRL:
                modifier = 7;
                break;
            case KEYMOD_SHIFT | KEYMOD_ALT | KEYMOD_CTRL:
                modifier = 8;
                break;
            default:
                return start + lastChar;
        }
        return start + (";" + modifier) + lastChar;
    }
}
