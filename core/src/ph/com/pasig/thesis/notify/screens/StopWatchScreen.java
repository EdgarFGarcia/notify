package ph.com.pasig.thesis.notify.screens;

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

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.Stopwatch;


public class StopWatchScreen implements Screen {

    static long time;

    private static BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("fonts/notifyWhite.fnt"),
            Gdx.files.internal("fonts/notifyWhite.png"), false);
    Label timeLabel;
    private Stage stage;

    public StopWatchScreen(Stage stage) {
        this.stage = stage;
        stage.clear();
    }


    private void incrementTime(boolean startTimer) {
        if (startTimer) {
            time = Stopwatch.getPausedTime() + (System.currentTimeMillis() - Stopwatch.getStartTime());
        }
    }

    private void resetTimer() {
        time = 0;
        Stopwatch.setStarted(false);
        Stopwatch.setPausedTime(0);
    }

    private void startTimer() {
        if (!Stopwatch.isStarted()) {
            Stopwatch.setStarted(true);
            Stopwatch.setStartTime(System.currentTimeMillis());
        } else {
            Stopwatch.setPausedTime(time);
            Stopwatch.setStarted(false);
        }
    }

    public String displayTime() {
        String testString;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss:SSS");
        testString = simpleDateFormat.format(time);
        return testString;
    }


    private void displayLabel() {
        Label.LabelStyle labelStyle;
        labelStyle = new Label.LabelStyle(bitmapFont, Variables.color());
        timeLabel = new Label(displayTime(), labelStyle);
        timeLabel.setPosition(520, 500);
        stage.addActor(timeLabel);
    }

    private void showImages() {
        // Restart
        Image restartButton = new Image(Assets.misc[6]);
        restartButton.setPosition(545 - (restartButton.getWidth() / 2), 20);
        restartButton.setColor(Variables.color());
        stage.addActor(restartButton);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTimer();
            }
        });

        // Start
        Image startButton = new Image(Assets.misc[7]);
        startButton.setPosition(945 - (startButton.getWidth() / 2), 20);
        startButton.setColor(Variables.color());
        stage.addActor(startButton);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startTimer();
            }
        });
    }

    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280, 800));
        Gdx.input.setInputProcessor(stage);

        Image bg = new Image(Assets.bg[Variables.getBgTheme()]);
        bg.setFillParent(true);
        stage.addActor(bg);
        displayLabel();

        CalendarScreen.sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        showImages();

        if (Stopwatch.isStarted()) {
            incrementTime(true);
        }

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
