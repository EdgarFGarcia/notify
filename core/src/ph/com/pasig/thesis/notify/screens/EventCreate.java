package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.Event;

public class EventCreate implements Screen{

    public EventCreate(Stage stage){
        EventCreate.stage = stage;
        stage.clear();
    }

    //global/class variables
    static Stage stage = new Stage();
    static Image bg = new Image();
    public static TextField hourTextField, minuteTextField, timeTextField;
    public static Skin skin = new Skin(Gdx.files.internal("JSON/uiskin.json"));
    private static TextButton setButton;
    private static Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.getFontLabel2(), Color.BLACK);
    public static Label setHour, setMinute, setDay, setMonth, setYear;
    private static String amOrPm;
    private static int hour, minute, years, month, days;

    public static void eventCreate(){

        //TextFields
        hourTextField = new TextField("", skin);
        minuteTextField = new TextField("", skin);
        timeTextField = new TextField("", skin);

        setHour = new Label("Set Hour", labelStyle);
        setMinute = new Label("Set Minute", labelStyle);
        setDay = new Label("Set Day", labelStyle);
        setMonth = new Label("Set Month", labelStyle);
        setYear = new Label("Set Years", labelStyle);
        setButton = new TextButton("Set",skin);
        //TODO dropdown list for days, months; int for years
        //change color font

        //TextField filter
        TextField.TextFieldFilter filter = new TextField.TextFieldFilter.DigitsOnlyFilter(){
            @Override
            public boolean acceptChar(TextField textField, char c){
                boolean b;
                switch (c){
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

        setHour.setBounds(350, 600, 100 ,100);
        hourTextField.setBounds(360, 550, 150, 50);
        hourTextField.setAlignment(1);
        hourTextField.setMaxLength(2);
        hourTextField.setTextFieldFilter(filter);

        setMinute.setBounds(550,600,100,100);
        minuteTextField.setBounds(575,550,150,50);
        minuteTextField.setAlignment(1);
        minuteTextField.setMaxLength(2);
        minuteTextField.setTextFieldFilter(filter);

        setDay.setBounds(790,625,150,50);
        String[] daySelection = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        final SelectBox<Object> Days = new SelectBox<Object>(skin);
        Days.setItems(daySelection);
        Days.setBounds(790, 550, 150, 50);
        Days.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                days = Days.getSelectedIndex();
            }
        });

        setMonth.setBounds(990,625,150,50);
        String[] monthSelection = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        final SelectBox<Object> Months = new SelectBox<Object>(skin);
        Months.setItems(monthSelection);
        Months.setBounds(990, 550, 150, 50);
        Months.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                month = Months.getSelectedIndex();
              //  Event.getMonths();
            }
        });

        String[] periodSelection = {"AM", "PM"};
        final SelectBox<Object> Period = new SelectBox<Object>(skin);
        Period.setItems(periodSelection);
        Period.setBounds(1140, 550, 150, 50);
        Period.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        setButton.setBounds(500, 300, 150, 150);
        setButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hour = Integer.parseInt(hourTextField.getText());
                minute = Integer.parseInt(minuteTextField.getText());
              //  Event.event(null, null, hour, minute, month, days);
            }
        });
        stage.addActor(hourTextField);
        stage.addActor(setHour);
        stage.addActor(setMinute);
        stage.addActor(minuteTextField);
        stage.addActor(Days);
        stage.addActor(setDay);
        stage.addActor(Months);
        stage.addActor(setMonth);
        stage.addActor(Period);
        stage.addActor(setButton);
    }



    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280,800));
        Gdx.input.setInputProcessor(stage);

        bg = new Image(Assets.bg[Variables.getBgTheme()]);
        bg.setFillParent(true);
        stage.addActor(bg);

        CalendarScreen.sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);

        eventCreate();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
        if(skin!=null)
            skin.dispose();
    }
}