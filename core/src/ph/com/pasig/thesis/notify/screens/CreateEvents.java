package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.AlarmJson;
import ph.com.pasig.thesis.notify.helper.Event;
import ph.com.pasig.thesis.notify.helper.EventJson;

/**
 * Created by iamAxylle on 3/17/2015.
 */
public class CreateEvents implements Screen {

    private static Stage stage;
    private static Skin skin = new Skin(Gdx.files.internal("JSON/jsonTry.json"));

    private static TextField nameTextField = new TextField("", skin);

    private static TextField monthTextField = new TextField("", skin);
    private static TextField dayTextField = new TextField("", skin);
    private static TextField yearTextField = new TextField("", skin);
    private static TextField hourTextField = new TextField("", skin);
    private static TextField minTextField = new TextField("", skin);

    private static TextButton[] upTextButton = new TextButton[5];
    private static TextButton[] downTextButton = new TextButton[5];
    private static TextButton amPmTextButton = new TextButton("", skin);
    private static TextButton setTextButton = new TextButton("SET", skin);

    private static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static String[] amPm = {"AM", "PM"};

    private static int month, amPmIndex, year, day, numberOfDays, hour, minutes;

    public CreateEvents(Stage stage) {
        CreateEvents.stage = stage;
        stage.clear();

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        amPmIndex = calendar.get(Calendar.AM_PM);
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minutes = calendar.get(Calendar.MINUTE);
    }

    private void loadComponents() {
        int[] xAxis = {430, 560, 670, 870, 980, 1090};
        int[] width = {120, 100, 150, 100, 100, 100};

        // Event Name Text Field
        nameTextField.setBounds(560, 650, 500, 100);
        stage.addActor(nameTextField);

        // Text Fields
        monthTextField.setBounds(xAxis[0], 400, width[0], 100);
        monthTextField.setAlignment(1);
        monthTextField.setDisabled(true);
        stage.addActor(monthTextField);

        dayTextField.setBounds(xAxis[1], 400, width[1], 100);
        dayTextField.setAlignment(1);
        dayTextField.setDisabled(true);
        stage.addActor(dayTextField);

        yearTextField.setBounds(xAxis[2], 400, width[2], 100);
        yearTextField.setAlignment(1);
        yearTextField.setDisabled(true);
        stage.addActor(yearTextField);

        hourTextField.setBounds(xAxis[3], 400, width[3], 100);
        hourTextField.setAlignment(1);
        hourTextField.setDisabled(true);
        stage.addActor(hourTextField);

        minTextField.setBounds(xAxis[4], 400, width[4], 100);
        minTextField.setAlignment(1);
        minTextField.setDisabled(true);
        stage.addActor(minTextField);

        amPmTextButton.setBounds(xAxis[5], 400, width[5], 100);
        stage.addActor(amPmTextButton);

        // Text Buttons
        for (int i = 0; i < 5; i++) {
            upTextButton[i] = new TextButton("+", skin);
            downTextButton[i] = new TextButton("-", skin);

            upTextButton[i].setBounds(xAxis[i], 500, width[i], 60);
            downTextButton[i].setBounds(xAxis[i], 340, width[i], 60);

            stage.addActor(upTextButton[i]);
            stage.addActor(downTextButton[i]);
        }

        // Set TextButton
        setTextButton.setBounds(660, 100, 300, 100);
        stage.addActor(setTextButton);
        setDefault();
    }


    private void setDefault() {
        monthTextField.setText(months[month]);
        amPmTextButton.setText(amPm[amPmIndex]);
        dayTextField.setText(String.valueOf(day));
        yearTextField.setText(String.valueOf(year));
        hourTextField.setText(String.valueOf(hour));
        minTextField.setText(String.valueOf(minutes));
    }

    private static void correctDayTextField() {
        numberOfDays = new GregorianCalendar(year, month, 1).getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        if (Integer.parseInt(dayTextField.getText()) > numberOfDays) {
            dayTextField.setText(String.valueOf(numberOfDays));
        }
    }

    private static void addListener() {
        numberOfDays = new GregorianCalendar(year, month, 1).getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        // Month Button (+)
        upTextButton[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (month < 11) {
                    month++;
                    monthTextField.setText(months[month]);
                    correctDayTextField();
                }
            }
        });

        // Month Button (-)
        downTextButton[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (month > 0) {
                    month--;
                    monthTextField.setText(months[month]);
                    correctDayTextField();
                }
            }
        });

        // Day Button (+)
        upTextButton[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                numberOfDays = new GregorianCalendar(year, month, 1).getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                if (day < numberOfDays) {
                    day++;
                    dayTextField.setText(String.valueOf(day));
                }
            }
        });

        // Day Button (-)
        downTextButton[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                numberOfDays = new GregorianCalendar(year, month, 1).getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                if (day > 1) {
                    day--;
                    dayTextField.setText(String.valueOf(day));
                }
            }
        });

        // Year Button (+)
        upTextButton[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (year < 9999) {
                    year++;
                    yearTextField.setText(String.valueOf(year));
                    correctDayTextField();
                }
            }
        });

        // Year Button (-)
        downTextButton[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (year > 1000) {
                    year--;
                    yearTextField.setText(String.valueOf(year));
                    correctDayTextField();
                }
            }
        });

        // Hour Button (+)
        upTextButton[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hour < 12) {
                    hour++;
                    hourTextField.setText(String.valueOf(hour));
                }
            }
        });

        // Hour Button (-)
        downTextButton[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hour > 1) {
                    hour--;
                    hourTextField.setText(String.valueOf(hour));
                }
            }
        });

        // Minute Button (+)
        upTextButton[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (minutes < 59) {
                    minutes++;
                    minTextField.setText(String.valueOf(minutes));
                }
            }
        });

        // Minute Button (-)
        downTextButton[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (minutes > 0) {
                    minutes--;
                    minTextField.setText(String.valueOf(minutes));
                }
            }
        });

        // AM_PM Button
        amPmTextButton.setText(amPm[amPmIndex]);
        amPmTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (amPmIndex == 0) {
                    amPmIndex = 1;
                } else {
                    amPmIndex = 0;
                }
                amPmTextButton.setText(amPm[amPmIndex]);
            }
        });

        setTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (amPmIndex == 1) {
                    hour = hour + 12;
                }
                long timeInMillis = new GregorianCalendar(year, month, day, hour, minutes).getTimeInMillis();
                String eventName = nameTextField.getText();
                Event.save(Event.event(eventName, timeInMillis));
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

        loadComponents();
        addListener();
        CalendarScreen.sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);

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
        stage.dispose();
    }

}
