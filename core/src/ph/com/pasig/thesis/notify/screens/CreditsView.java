package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import ph.com.pasig.thesis.notify.assets.Assets;

/**
 * Created by frankensteenie on 3/23/2015.
 */
public class CreditsView implements Screen {

    static Stage stage = new Stage();
    static Image returnButton;
    private Label harold, jonas, kirk, richelle;
    private Label subLabelHarold, subLabelJonas, subLabelKirk, subLabelRichelle;
    private Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.getFontLabel(), Color.WHITE);
    private Label.LabelStyle labelStyle2 = new Label.LabelStyle(Assets.getFontLabel2(), Color.WHITE);

    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280,800));
        Gdx.input.setInputProcessor(stage);

        harold = new Label("Harold Bryan Esteron", labelStyle);
        harold.setBounds(100,100,100,50);
        subLabelHarold = new Label("Assistant Programmer", labelStyle2);
        subLabelHarold.setBounds(100,30,100,50);
        subLabelHarold.addAction(Actions.sequence(Actions.moveTo(100, 900, 10.5f), Actions.fadeOut(1)));
        harold.addAction(Actions.sequence(Actions.moveTo(100, 900, 10), Actions.fadeOut(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                harold.clearActions();
                subLabelHarold.clearActions();
                jonas();
            }
        })));
        stage.addActor(subLabelHarold);
        stage.addActor(harold);

        Assets.music[20].play();
        Assets.music[20].setLooping(true);
        Assets.music[20].setVolume(0.5f);
    }

    private void jonas(){
        jonas = new Label("Jonas Gabon", labelStyle);
        jonas.setBounds(100,100,100,50);
        subLabelJonas = new Label("Designer", labelStyle2);
        subLabelJonas.setBounds(100,30,100,50);
        subLabelJonas.addAction(Actions.sequence(Actions.moveTo(100, 900, 10.5f), Actions.fadeOut(1)));
        jonas.addAction(Actions.sequence(Actions.moveTo(100, 900, 10), Actions.fadeOut(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                jonas.clearActions();
                subLabelJonas.clearActions();
                kirk();
            }
        })));
        stage.addActor(subLabelJonas);
        stage.addActor(jonas);
    }

    private void kirk(){
        kirk = new Label("Kirk Persius Jimenez", labelStyle);
        kirk.setBounds(300,300,300,50);
        subLabelKirk = new Label("Senior Programmer", labelStyle2);
        subLabelKirk.setBounds(100,30,100,50);
        subLabelKirk.addAction(Actions.sequence(Actions.moveTo(100, 900, 10.5f), Actions.fadeOut(1)));
        kirk.addAction(Actions.sequence(Actions.moveTo(100, 900, 10), Actions.fadeOut(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                kirk.clearActions();
                subLabelKirk.clearActions();
                richelle();
            }
        })));
        stage.addActor(subLabelKirk);
        stage.addActor(kirk);
    }

    private void richelle(){
        richelle = new Label("Richelle Rosales", labelStyle);
        richelle.setBounds(400,400,100,50);
        subLabelRichelle = new Label("Assistant Programmer", labelStyle2);
        subLabelRichelle.setBounds(100,30,100,50);
        subLabelRichelle.addAction(Actions.sequence(Actions.moveTo(100, 900, 10.5f), Actions.fadeOut(1)));
        richelle.addAction(Actions.sequence(Actions.moveTo(100, 900, 10), Actions.fadeOut(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                richelle.clearActions();
                subLabelRichelle.clearActions();
                Assets.music[20].dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsView(stage));
            }
        })));
        stage.addActor(subLabelRichelle);
        stage.addActor(richelle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
