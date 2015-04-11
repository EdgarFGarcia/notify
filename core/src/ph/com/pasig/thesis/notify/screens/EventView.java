package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.Event;


/**
 * Created by frankensteenie on 3/9/15.
 */
public class EventView implements Screen {
    private static Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.getFontLabel2(), Color.BLACK);
    private static Image plusSign = new Image();
    private static Stage stage;
    private static Skin skin = new Skin(Gdx.files.internal("JSON/uiskin.json"));

    public EventView(Stage stage) {
        EventView.stage = stage;
        stage.clear();
    }

    private static void loadComponents() {
        Event.showEvent(stage);

        plusSign = new Image(Assets.misc[5]);
        plusSign.setPosition(1180, 10);
        plusSign.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new CreateEvents(stage));
            }
        });
        stage.addActor(plusSign);

        TextButton delete = new TextButton("-", skin);
        delete.setBounds(1060, 10, 100, 100);
        stage.addActor(delete);

        delete.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ArrayList evetsToDelete = new ArrayList<String>();
                for (int i = 0; i < Event.loadData().size(); i++) {
                    if (Event.checkBox[i].isChecked()) {
                        evetsToDelete.add(Event.toDelete.get(i));
                    }
                }
                Event.deleteEvent(evetsToDelete);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new EventView(stage));
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

        CalendarScreen.sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);
        loadComponents();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        if (stage != null)
            stage.dispose();
        if (skin != null)
            skin.dispose();
    }
}
