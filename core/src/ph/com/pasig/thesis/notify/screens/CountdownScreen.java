package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.text.SimpleDateFormat;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.Countdown;
import ph.com.pasig.thesis.notify.helper.Event;

public class CountdownScreen implements Screen {
    static Skin skin = new Skin(Gdx.files.internal("JSON/jsonTry.json"));
    TextButton startTextButton = new TextButton("START", skin);
    TextButton stopTextButton = new TextButton("STOP", skin);
    //TextButton restartTextButton = new TextButton("START", skin);
    TextField hrTextField = new TextField("", skin);
    TextField minTextField = new TextField("", skin);
    TextField secTextField = new TextField("", skin);
    TextField countTextField = new TextField("", skin);
    static long countdownTime;

    private Stage stage;

    public CountdownScreen(Stage stage) {
        this.stage = stage;
        stage.clear();
    }

    private void showComponents() {
        // Start TextButton
        startTextButton.setBounds(545, 50, 250, 80);
        startTextButton.setColor(Color.GREEN);
        stage.addActor(startTextButton);

        // Stop TextButton
        stopTextButton.setBounds(825, 50, 250, 80);
        stopTextButton.setColor(Color.RED);
        stage.addActor(stopTextButton);

        TextField.TextFieldFilter filter = new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                boolean b;
                switch (c) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        b = true;
                        break;
                    default:
                        b = false;
                }
                return b;
            }
        };

        // Countdown TextField
        countTextField.setBounds(480, 450, 660, 200);
        countTextField.setAlignment(1);
        stage.addActor(countTextField);

        // Hour TextField
        hrTextField.setMaxLength(2);
        hrTextField.setBounds(480, 450, 200, 200);
        hrTextField.setTextFieldFilter(filter);
        hrTextField.setAlignment(1);
        stage.addActor(hrTextField);

        // Minutes TextField
        minTextField.setMaxLength(2);
        minTextField.setBounds(710, 450, 200, 200);
        minTextField.setTextFieldFilter(filter);
        minTextField.setAlignment(1);
        stage.addActor(minTextField);

        // Second TextField
        secTextField.setMaxLength(2);
        secTextField.setBounds(940, 450, 200, 200);
        secTextField.setAlignment(1);
        secTextField.setTextFieldFilter(filter);
        stage.addActor(secTextField);

        enableComponents(Countdown.isStarted());

        startTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            startTimer();
            }
        });

        stopTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stopTimer();
            }
        });
    }

    private void stopTimer(){
        Countdown.setStarted(false);
        enableComponents(Countdown.isStarted());
        Assets.music[Variables.getNextTone()].stop();
    }

    private void startTimer(){
        Countdown.setStarted(true);
        enableComponents(Countdown.isStarted());

        String hrString, minString, secString;
        hrString = hrTextField.getText();
        minString = minTextField.getText();
        secString = secTextField.getText();


        if (hrString.equals("")) {
            hrString = "0";
            hrTextField.setText("0");
        }
        if (minString.equals("")) {
            minString = "0";
            minTextField.setText("0");
        }
        if (secString.equals("")) {
            secString = "0";
            secTextField.setText("0");
        }


        countdownTime = (Integer.parseInt(hrString) - 8) * 3600;
        countdownTime += Integer.parseInt(minString) * 60;
        countdownTime += Integer.parseInt(secString);
        countdownTime = countdownTime * 1000;
        Countdown.setEndTime(System.currentTimeMillis() + countdownTime);
    }

    private void enableComponents(boolean start) {
        countTextField.setVisible(start);
        hrTextField.setVisible(!start);
        minTextField.setVisible(!start);
        secTextField.setVisible(!start);
        if (start) {
            startTextButton.setTouchable(Touchable.disabled);
            stopTextButton.setTouchable(Touchable.enabled);
            startTextButton.setColor(Color.GRAY);
            stopTextButton.setColor(Color.RED);
        } else {
            startTextButton.setTouchable(Touchable.enabled);
            stopTextButton.setTouchable(Touchable.disabled);
            stopTextButton.setColor(Color.GRAY);
            startTextButton.setColor(Color.GREEN);
        }
    }

    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280, 800));
        Gdx.input.setInputProcessor(stage);

        Image bg = new Image(Assets.bg[Variables.getBgTheme()]);
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
        if (Countdown.isStarted()) {
            Countdown.setDisplayTime(Countdown.getEndTime() - System.currentTimeMillis());

            if (Countdown.getDisplayTime() + 28800000 <= 0) {
                countTextField.setText("00:00:00");
                Assets.music[Variables.getNextTone()].play();
                Countdown.setStarted(false);
                enableComponents(Countdown.isStarted());
            }
        }
        countTextField.setText(new SimpleDateFormat("HH:mm:ss").format(Countdown.getDisplayTime()));
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
