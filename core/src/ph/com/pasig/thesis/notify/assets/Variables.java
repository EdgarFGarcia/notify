package ph.com.pasig.thesis.notify.assets;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by frankensteenie on 3/11/2015.
 */
public class Variables {
    //Themes
    public final static int DEFAULT = 0;
    public final static int IRON = 1;
    public final static int MARIO = 2;
    public final static int MINION = 3;
    public final static int MUSIC = 4;
    public final static int PANDA = 5;
    public final static int BLUE = 6;
    public final static int GREEN = 7;
    public final static int ORANGE = 8;
    public final static int PINK = 9;
    public final static int PURPLE = 10;
    public final static int RED = 11;
    public final static int WHITE = 12;
    public final static int YELLOW = 13;
    public final static int STITCH = 14;
    //Tones
    public final static int TONEONE = 15;
    public final static int TONETWO = 16;
    public final static int TONETHREE = 17;
    public final static int TONEFOUR = 18;
    public final static int TONEFIVE = 19;
    public final static int TONESIX = 20;
    public final static int TONESEVEN = 21;
    public final static int TONEEIGHT = 22;
    public final static int TONENINE = 23;
    public final static int TONETEN = 24;
    public final static int TONEELEVEL = 25;
    public final static int TONETWELVE = 26;
    public final static int TONETHIRTEEN = 27;
    public final static int TONEFOURTEEN = 28;
    public final static int TONEFIFTEEN = 29;
    public final static int TONESIXTEEN = 30;
    public final static int TONESEVENTEEN = 31;
    public final static int TONEEIGHTEEN = 32;
    public final static int TONENINETEEN = 33;
    public final static int TONETWENTY = 34;

    private static boolean alarmOff;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Variables.score = score;
    }

    private static int score;

    public static boolean isAlarmOff() {
        return alarmOff;
    }

    public static void setAlarmOff(boolean alarmOff) {
        Variables.alarmOff = alarmOff;
    }

    public static String mp3 = ".mp3";
    public static String[] tones = {"tone1" + mp3, "tone2" + mp3, "tone3" + mp3, "tone4" + mp3, "tone5" + mp3, "tone6" + mp3, "tone7" + mp3, "tone8" + mp3, "tone9" + mp3, "tone10" + mp3,
            "tone11" + mp3, "tone12" + mp3, "tone13" + mp3, "tone14" + mp3, "tone15" + mp3, "tone16" + mp3, "tone17" + mp3, "tone18" + mp3, "tone19" + mp3, "tone20" + mp3, "notif"+mp3};
    public static String[] month = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    public static String[] week = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
    public static String[] days = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31"};
    public static String[] sidePanel = {"calendar", "event", "alarm", "setting"};
    public static String[] misc = {"alarm", "delete", "line", "off", "on", "plus", "repeat", "timer"};
    public static String[] bg = {"white", "iron", "mario", "minion", "music", "panda", "blue",
            "green", "orange", "pink", "purple", "red", "black", "yellow", "stitch"};

    public static String[] jajanken = {"batoLeft","batoRight","paperLeft","paperRight","scissorLeft","scissorRight"};
    public static String[] question = {"batoRight","paperRight","scissorRight"};
    public static String[] answers = {"paperLeft","scissorLeft","batoLeft"};
    public static String vs = "vs";

    public static String message;
    private static int bgTheme;
    private static int backTheme;
    private static int nextTheme;
    private static int CurrentTone;
    private static int nextTone;

    public static int getNextTheme() {
        return nextTheme;
    }

    public static void setNextTheme(int nextTheme) {
        Variables.nextTheme = nextTheme;
    }

    public static int getBgTheme() {
        return bgTheme;
    }

    public static void setBgTheme(int bgTheme) {
        Variables.bgTheme = bgTheme;
    }

    public static int getBackTheme() {
        return backTheme;
    }

    public static void setBackTheme(int backTheme) {
        Variables.backTheme = backTheme;
    }

    public static int getNextTone() {
        return nextTone;
    }

    public static void setNextTone(int nextTone) {
        Variables.nextTone = nextTone;
    }

    public static Color color(){
        Color c;
        int t = Variables.getBgTheme();
        if ((t == 12) || (t == 1) || (t == 6) || (t == 8) || (t == 9) || (t == 10) || (t == 11)) {
            c = Color.WHITE;
        } else {
            c = Color.BLACK ;
        }
        return c;
    }


    public static int getCurrentTone() {
        return CurrentTone;
    }

    public static void setCurrentTone(int CurrentTone) {
        Variables.CurrentTone = CurrentTone;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String Get) {
        Variables.message = Get;
    }
}
