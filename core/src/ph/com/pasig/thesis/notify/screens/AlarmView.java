package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.Event;

public class AlarmView implements Screen {

    private static BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("fonts/notifyWhite.fnt"),
            Gdx.files.internal("fonts/notifyWhite.png"), false);
    private static Label timeLabel;
    private Stage stage;

    public AlarmView(Stage stage) {
        this.stage = stage;
        stage.clear();
    }

    // Add images to Stage stage
    private void addImages() {
        Image alarmButton = new Image(Assets.misc[0]);
        alarmButton.setPosition(545 - (alarmButton.getWidth() / 2), 20);

        Image timerButton = new Image(Assets.misc[7]);
        timerButton.setPosition(1035 - (timerButton.getWidth() / 2), 20);

        alarmButton.setColor(Variables.color());
        timerButton.setColor(Variables.color());


        stage.addActor(alarmButton);
        stage.addActor(timerButton);

        alarmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new CountdownScreen(stage));
            }
        });

        timerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new StopWatchScreen(stage));
            }
        });

    }

    private String displayTime() {
        // Time
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("KK:mm:ss aa").format(calendar.getTime());
    }

    private void displayLabel() {

        Label.LabelStyle labelStyle;
        labelStyle = new Label.LabelStyle(bitmapFont, Variables.color());

        timeLabel = new Label(displayTime(), labelStyle);
        timeLabel.setPosition(520, 500);
        stage.addActor(timeLabel);
    }

    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280, 800));
        Gdx.input.setInputProcessor(stage);

        Image bg;
        bg = new Image(Assets.bg[Variables.getBgTheme()]);
        bg.setFillParent(true);
        stage.addActor(bg);

        addImages();
        displayLabel();
        CalendarScreen.sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);
        Event.checkAlarms(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        timeLabel.setText(displayTime());

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
