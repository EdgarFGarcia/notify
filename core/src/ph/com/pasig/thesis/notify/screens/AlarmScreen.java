package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.AlarmJson;
import ph.com.pasig.thesis.notify.helper.Event;

/**
 * Created by iamAxylle on 3/13/2015.
 */
public class AlarmScreen implements Screen {
    static Skin skin = new Skin(Gdx.files.internal("JSON/jsonTry.json"));
    private static TextField hrTextField, minTextField, amTextField, nameTextField;
    private static Label setTimeLabel, setAlarmName;
    private static TextButton setButton;
    private static Image bg;
    private static BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("fonts/notifyWhite.fnt"),
            Gdx.files.internal("fonts/notifyWhite.png"), false);
    private static Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.BLACK);

    private static String alarmName, am;
    private static int hour, min;
    private Stage stage;
    private FileHandle file = Gdx.files.local("bin/alarm.json");

    public AlarmScreen(Stage stage) {
        this.stage = stage;
        stage.clear();
    }

    private static long parseDate(String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a MM/dd/yyyy",
                Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(text).getTime();
    }

    private void showComponents() {
        // Set Font Size
        bitmapFont.setScale(.5f);

        // Initialize TextField
        hrTextField = new TextField("", skin);
        minTextField = new TextField("", skin);
        nameTextField = new TextField("", skin);
        amTextField = new TextField("", skin);

        setTimeLabel = new Label("Set Time:", labelStyle);
        setAlarmName = new Label("Set Name:", labelStyle);
        setButton = new TextButton("Set", skin);

        // Set Time Label
        setTimeLabel.setPosition(530, 660);
        stage.addActor(setTimeLabel);

        // Alarm Title Label
        setAlarmName.setPosition(530, 360);
        stage.addActor(setAlarmName);

        // Hour TextField
        hrTextField.setPosition(530, 500);
        hrTextField.setSize(150, 150);
        stage.addActor(hrTextField);

        // Minute TextField
        minTextField.setPosition(730, 500);
        minTextField.setSize(150, 150);
        stage.addActor(minTextField);

        // AM/PM TextField
        amTextField.setPosition(930, 500);
        amTextField.setSize(150, 150);
        stage.addActor(amTextField);

        // Alarm Name TextField
        nameTextField.setPosition(530, 200);
        nameTextField.setSize(550, 150);
        stage.addActor(nameTextField);

        // SetAlarm Button//TODO
        setButton.setPosition(750, 50);
        stage.addActor(setButton);

        setButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hour = Integer.parseInt(hrTextField.getText());
                min = Integer.parseInt(minTextField.getText());
                am = amTextField.getText();
                alarmName = nameTextField.getText();

                save(event(alarmName, hour, min, am)); //TODO
//                load();
            }
        });
    }
    private static AlarmJson alarmJson;
    public AlarmJson event(String alarmName, int hour, int min, String am) {
        alarmJson = new AlarmJson();
        alarmJson.hour = hour;
        alarmJson.min = min;
        alarmJson.am = am;
        alarmJson.alarmName = alarmName;
        return alarmJson;
    }

    public void save(AlarmJson alarmJson) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        file.writeString(Base64Coder.encodeString(json.toJson(alarmJson)), false);
    }

    public void load() {
        Json json = new Json();
        alarmJson = json.fromJson(AlarmJson.class, Base64Coder.decodeString(file.readString()));
    }

    private void checkJson(){
        load();
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);


        if((alarmJson.hour == hour)&&(alarmJson.min == min)){
            Assets.music[Variables.getNextTone()].play();
        }
    }
    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280, 800));
        Gdx.input.setInputProcessor(stage);

        bg = new Image(Assets.bg[Variables.getBgTheme()]);
        bg.setFillParent(true);
        stage.addActor(bg);

        CalendarScreen.sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);

        showComponents();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        checkJson();
        Event.checkAlarms(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }
}
