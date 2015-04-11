package ph.com.pasig.thesis.notify.helper;

/**
 * Created by frankensteenie on 3/24/2015.
 */
public interface AndroidPushNotificationServiceCallback {
    public void startService();

    public void sendPushNotification();

    public void stopService();
}
