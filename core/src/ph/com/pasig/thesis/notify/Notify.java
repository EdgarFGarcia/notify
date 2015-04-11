package ph.com.pasig.thesis.notify;

import com.badlogic.gdx.Game;

import java.util.Calendar;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.AndroidPushNotificationServiceCallback;
import ph.com.pasig.thesis.notify.helper.Stopwatch;
import ph.com.pasig.thesis.notify.screens.CalendarScreen;
import ph.com.pasig.thesis.notify.screens.Jajanken;

/**
 * Created by iamAxylle on 3/11/2015.
 */
public class Notify extends Game {

//    public Notify(AndroidPushNotificationServiceCallback callback){
//        this.callback = callback;
//    }

    @Override
    public void create() {
        Assets.load();
        Assets.manager.finishLoading();

        // Set Theme
        Variables.setBackTheme(Variables.PANDA);
        Variables.setNextTheme(Variables.PANDA);
        Variables.setBgTheme(Variables.PANDA);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        setScreen(new CalendarScreen(month, year));
        Stopwatch.stopwatchDefault();

        Variables.setAlarmOff(true);
        Variables.setScore(0);

//        setScreen(new Jajanken());
    }

    @Override
    public void dispose() {
        Assets.dispose();
        super.dispose();
    }
}
