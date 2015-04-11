package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.awt.Color;
import java.util.Random;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;

/**
 * Created by frankensteenie on 3/23/2015.
 */
public class Jajanken implements Screen {

    static Stage stage;
    static Image rock, paper, scissor, vs, question;
    static int score = 0;
    private static Skin skin = new Skin(Gdx.files.internal("JSON/uiskin.json"));
    private static Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.getFontLabel(), com.badlogic.gdx.graphics.Color.BLACK);

    public Jajanken(Stage stage) {
        Jajanken.stage = stage;
        stage.clear();
        Assets.music[Variables.getNextTone()].play();
        Assets.music[Variables.getNextTone()].setLooping(true);
    }
    static int randomInt = new Random().nextInt(3);
    public static void showjajanken() {
        vs = new Image(Assets.versus);
        vs.setPosition(500, 300);
        stage.addActor(vs);

        question = Assets.question[randomInt];
        question.setPosition(900, 300);

        for (int i = 0; i < 3; i++) {
            Assets.answers[i].setPosition(200, 50 + (i * 250));
            Assets.answers[i].setScale(.8f);
            stage.addActor(Assets.answers[i]);
            final int index = i;
            Assets.answers[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    stage.addActor(question);
                    if (randomInt == index) {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new EventView(stage));
                        Assets.music[Variables.getNextTone()].stop();
                        Assets.music[Variables.getNextTone()].setLooping(false);
                    }
                }
            });
        }
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ExtendViewport(1280, 800));
        Gdx.input.setInputProcessor(stage);
        Image bg = new Image(Assets.bg[Variables.getBgTheme()]);
        bg.setFillParent(true);
        stage.addActor(bg);
        showjajanken();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        if(stage!=null)
            stage.dispose();
    }
}