package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.Event;


/**
 * Created by frankensteenie on 3/9/15.
 */
public class SettingsView implements Screen {
    public static int indexMusic;
    public static Table alarmTone = new Table();
    public static Table theme = new Table();
    private static Stage stage;
    private static Image bg;
    private static Skin skin = new Skin(Gdx.files.internal("JSON/uiskin.json"));
    private static BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("fonts/notifyWhite.fnt"),
            Gdx.files.internal("fonts/notifyWhite.png"), false);
    private static Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.BLACK);
    private static Sprite On, Off;
    private Label Alarm, Vibrate, Themes, Credits;


    public SettingsView(Stage stage) {
        alarmTone.clear();
        theme.clear();
        this.stage = stage;
        stage.clear();
    }

    public static void ThemeSelection() {

        String[] themeContents = {"Default", "Iron Man", "Super Mario", "Minion", "Music", "Panda", "Blue", "Green", "Orange",
                "Pink", "Purple", "Red", "Black", "Yellow","Stitch"};

        final SelectBox<Object> Themes = new SelectBox<Object>(skin);
        Themes.setItems(themeContents);
        Themes.setSelectedIndex(Variables.getBgTheme());
        Themes.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int index = Themes.getSelectedIndex();
                Variables.setBgTheme(index);
                Variables.setNextTheme(index);
                Variables.setBackTheme(index);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsView(stage));
            }
        });
        theme.add(Themes).width(300).height(80);
        theme.setPosition(750, 315);
        stage.addActor(theme);
    }

    public static void AlarmToneSelection() {

        Object[] AlarmTone = {"Tone 1","Tone 2","Tone 3","Tone 4","Tone 5","Tone 6","Tone 7","Tone 8",
                "Tone 9","Tone 10","Tone 11","Tone 12","Tone 13","Tone 14","Tone 15","Tone 16","Tone 17","Tone 18","Tone 19","Tone 20"};


        final SelectBox<Object> Alarm = new SelectBox<Object>(skin);
        Alarm.setItems(AlarmTone);
        Alarm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                indexMusic = Alarm.getSelectedIndex();
                Assets.music[Variables.getNextTone()].stop();
                Variables.setNextTone(indexMusic);
                Assets.music[Variables.getNextTone()].play();
            }
        });

        alarmTone.add(Alarm).width(300).height(80);
        alarmTone.setPosition(750, 720);
        stage.addActor(alarmTone);
    }

    public static void Onn() {
        final Image on = new Image(Assets.misc[4]);
        on.setPosition(650, 500);
        on.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                on.remove();
                Off();
            }
        });
        stage.addActor(on);
    }

    public static void Off() {
        final Image off = new Image(Assets.misc[3]);
        off.setPosition(750, 500);
        off.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                off.remove();
                Onn();
            }
        });
        stage.addActor(off);
    }

    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280, 800));
        Gdx.input.setInputProcessor(stage);

        bitmapFont.setScale(.4f);
        Variables.setNextTone(indexMusic);

        bg = new Image(Assets.bg[Variables.getBgTheme()]);
        bg.setFillParent(true);
        stage.addActor(bg);

        Assets.music[Variables.getNextTone()].stop();
        CalendarScreen.sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);

        Alarm = new Label("Alarm Tone: ", labelStyle);
        AlarmToneSelection();

        Vibrate = new Label("Vibrate: ", labelStyle);
        Onn();

        Themes = new Label("Themes: ", labelStyle);
        ThemeSelection();

        Credits = new Label("Credits: ", labelStyle);


        int t = Variables.getBgTheme();
        if ((t == 12) || (t == 1) || (t == 6) || (t == 8) || (t == 9) || (t == 10) || (t == 11)) {
            labelStyle= new Label.LabelStyle(bitmapFont, Color.WHITE);
        } else {
            labelStyle = new Label.LabelStyle(bitmapFont, Color.BLACK);
        }

        // Set Coordinates
        Alarm.setPosition(400, 700);
        Vibrate.setPosition(400, 500);
        Themes.setPosition(400, 300);
        Credits.setPosition(400, 100);


        // Add Labels to Stage
        stage.addActor(Alarm);
        stage.addActor(Vibrate);
        stage.addActor(Themes);
        stage.addActor(Credits);

        TextButton Credits = new TextButton("Credits", skin);
        Credits.setPosition(650, 100);
        Credits.setSize(200, 50);
        Credits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new CreditsView());
                    }
                })));
            }
        });
        stage.addActor(Credits);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        if (skin != null) skin.dispose();
        if (theme != null) theme.reset();
        if (alarmTone != null) alarmTone.reset();


//        for (int i = 0; i < 1; i++) {
//            On.getTexture().dispose();
//            Off.getTexture().dispose();
//        }
    }
}
