package ph.com.pasig.thesis.notify.helper;

/**
 * Created by iamAxylle on 3/13/2015.
 */
public class Stopwatch {
    private static long startTime;
    private static long pausedTime;
    private static boolean started;
    private static boolean paused;


    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        Stopwatch.started = started;
    }

    public static long getPausedTime() {
        return pausedTime;
    }

    public static void setPausedTime(long pausedTime) {
        Stopwatch.pausedTime = pausedTime;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        Stopwatch.startTime = startTime;
    }

    public static void stopwatchDefault() {
        startTime = System.currentTimeMillis();
        started = false;
        pausedTime = 0;
    }


}

