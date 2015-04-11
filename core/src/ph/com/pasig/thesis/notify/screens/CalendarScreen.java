package ph.com.pasig.thesis.notify.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.assets.Variables;
import ph.com.pasig.thesis.notify.helper.Event;


public class CalendarScreen implements Screen {
    final static Stage stage = new Stage();
    static Image back = new Image();
    static Image next = new Image();
    static int month, year;
    private static BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("fonts/notifyWhite.fnt"),
            Gdx.files.internal("fonts/notifyWhite.png"), false);
    private static Image[][] months = new Image[2][12];
    private static Image[][] weeks = new Image[2][7];
    private static Image[][] days = new Image[3][31];
    private static Image bg = new Image();
    private static int themeIndex;

    private static Image calendarButon, eventButton, alarmButton, settingButton;

    public CalendarScreen(int month, int year) {
        stage.clear();
        CalendarScreen.month = month;
        CalendarScreen.year = year;
        textureRegionToImage();
        int t = Variables.getBgTheme();
        if ((t == 12) || (t == 1) || (t == 6) || (t == 8) || (t == 9) || (t == 10) || (t == 11)) {
            themeIndex = 0;
        } else {
            themeIndex = 1;
        }
        bitmapFont.setScale(.8f);

    }

    public CalendarScreen() {
    }

    private static void textureRegionToImage() {
        // Months White
        for (int i = 0; i < 12; i++) {
            months[0][i] = new Image(Assets.months[0][i]);
        }

        // Months Black
        for (int i = 0; i < 12; i++) {
            months[1][i] = new Image(Assets.months[1][i]);
        }

        // Weeks White
        for (int i = 0; i < 7; i++) {
            weeks[0][i] = new Image(Assets.weeks[0][i]);
        }
        // Weeks Black
        for (int i = 0; i < 7; i++) {
            weeks[1][i] = new Image(Assets.weeks[1][i]);
        }

        // Days
        for (int i = 0; i < 31; i++) {
            // Normal
            days[0][i] = new Image(Assets.days[0][i]);

            //Special
            days[1][i] = new Image(Assets.days[1][i]);

            //Current
            days[2][i] = new Image(Assets.days[2][i]);
        }

        // Background
        bg = new Image(Assets.bg[Variables.getBgTheme()]);
    }

    public static void sidePanel(final Stage stage, TextureRegion C, TextureRegion E, TextureRegion A, TextureRegion S) {
        Assets.music[Variables.getNextTone()].stop();

        calendarButon = new Image(C);
        calendarButon.setPosition(0, 600);
        calendarButon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new CalendarScreen(Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.YEAR)));
                calendarButon.setColor(Color.CYAN);
            }
        });
        stage.addActor(calendarButon);

        eventButton = new Image(E);
        eventButton.setPosition(0, 400);
        eventButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new EventView(stage));
                CalendarScreen.eventButton.setColor(Color.CYAN);
            }
        });
        stage.addActor(eventButton);

        alarmButton = new Image(A);
        alarmButton.setPosition(0, 200);
        alarmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new AlarmView(stage));
                alarmButton.setColor(Color.CYAN);
            }
        });
        stage.addActor(alarmButton);

        settingButton = new Image(S);
        settingButton.setPosition(0, 0);
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingButton.setColor(Color.BLACK);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsView(stage));
                settingButton.setColor(Color.CYAN);
            }
        });
        stage.addActor(settingButton);
    }

    public static void showCalendar() {
        // Back and Next
        back = new Image(Assets.back[Variables.getBackTheme()]);
        next = new Image(Assets.next[Variables.getNextTheme()]);

        final int numberOfDays, startOfMonth; //Number Of Days, Start Of Month

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, 1);
        numberOfDays = gregorianCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = gregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

        // Button
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (month == 11) {
                    month = 0;
                    year++;
                } else {
                    month++;
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new CalendarScreen(month, year));
            }
        });
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (month == 0) {
                    month = 11;
                    year--;
                } else {
                    month--;
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new CalendarScreen(month, year));
            }
        });

        // Back and Next Buttons
        next.setPosition(1243 - next.getWidth(), 700);
        stage.addActor(next);
        back.setPosition(340, 700);
        stage.addActor(back);

        // Year and Month String
        String[] monthString = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String yearString = "" + year;
        Label yearLabel;
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Variables.color());
        yearLabel = new Label(monthString[month] + " " + yearString, labelStyle);
        yearLabel.setPosition(540, 710);
        stage.addActor(yearLabel);

        // Weeks Image
        for (int i = 0; i < 7; i++) {
            weeks[themeIndex][i].setPosition(340 + (i * 129), 580);
            stage.addActor(weeks[themeIndex][i]);
        }

        // Days
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int row = 500;

        for (int i = 1; i <= numberOfDays; i++) {
            if ((i + startOfMonth) % 7 == 2) {
                if (i != 1) {
                    row = row - 99;
                }
            }
            int column = 340 + (((i + (startOfMonth - 2)) % 7) * 129);

            GregorianCalendar gregorian = new GregorianCalendar(year, month, i);
            String dayOfWeek = gregorian.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            if ((i == currentDay) && (currentMonth == month) && (currentYear == year)) {
                days[2][i - 1].setPosition(column, row);
                stage.addActor(days[2][i - 1]);
                clickListener(days[2][i - 1], new CalendarScreen());
            } else if ((dayOfWeek.equals("Sunday")) || (dayOfWeek.equals("Saturday"))) {
                days[1][i - 1].setPosition(column, row);
                stage.addActor(days[1][i - 1]);
            } else {
                days[0][i - 1].setPosition(column, row);
                stage.addActor(days[0][i - 1]);
            }
        }
    }

    // Add Click Listener
    public static void clickListener(Image image, final Screen screen) {
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
            }
        });
    }

    @Override
    public void show() {
        stage.setViewport(new ExtendViewport(1280, 800));
        Gdx.input.setInputProcessor(stage);

        bg.setFillParent(true);
        stage.addActor(bg);

        sidePanel(stage, Assets.sidePanel[0], Assets.sidePanel[1], Assets.sidePanel[2], Assets.sidePanel[3]);
        showCalendar();
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
        stage.dispose();
        bitmapFont.dispose();
    }
}
