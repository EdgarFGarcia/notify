package ph.com.pasig.thesis.notify.helper;

/**
 * Created by iamAxylle on 3/13/2015.
 */
public class Countdown {
    private static long displayTime;
    private static long endTime;
    private static boolean started;

    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        Countdown.started = started;
    }

    public static long getDisplayTime() {
        return displayTime;
    }

    public static void setDisplayTime(long displayTime) {
        Countdown.displayTime = displayTime;
    }

    public static long getEndTime() {
        return endTime;
    }

    public static void setEndTime(long endTime) {
        Countdown.endTime = endTime;
    }
}
