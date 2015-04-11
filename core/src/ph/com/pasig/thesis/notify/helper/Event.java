package ph.com.pasig.thesis.notify.helper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

import ph.com.pasig.thesis.notify.assets.Assets;
import ph.com.pasig.thesis.notify.screens.Jajanken;


public class Event {
    public static CheckBox[] checkBox = new CheckBox[99];
    public static LinkedList<String> toDelete = new LinkedList<String>();
//    static String locRoot = Gdx.files.getLocalStoragePath();
    private static FileHandle file = Gdx.files.local("bin/event.json");
    private static FileHandle fileTemp = Gdx.files.local("bin/temp.json");

    public static EventJson event(String eventName, long timeInMillis) {
        EventJson eventJson = new EventJson();
        eventJson.eventName = eventName;
        eventJson.timeInMillis = timeInMillis;
        return eventJson;
    }

    public static void save(EventJson eventJson) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        if (!file.exists()) {
            file.writeString("[\n", true);
        } else {
            deleteLastChar();
        }
        file.writeString((json.toJson(eventJson)), true);
        file.writeString(",\n]", true);
    }

    private static void deleteLastChar() {
        File fileName = file.file();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
            long length;
            length = randomAccessFile.length() - 1;
            byte b;
            do {
                length -= 1;
                randomAccessFile.seek(length);
                b = randomAccessFile.readByte();
            } while (b != 10);
            randomAccessFile.setLength(length + 1);
            randomAccessFile.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public static ArrayList<EventJson> loadData() {
        toDelete.clear();
        ArrayList<EventJson> data = new ArrayList<EventJson>();
        if (file.exists()) {
            Json json = new Json();
            int index = 0;
            ArrayList<JsonValue> list = json.fromJson(ArrayList.class, file.readString());
            for (JsonValue v : list) {
                data.add(json.readValue(EventJson.class, v));
                toDelete.add("{\"eventName\":\"" + data.get(index).eventName + "\",\"timeInMillis\":"
                        + data.get(index).timeInMillis + "},");

//                toDelete.add(json.toJson(data.get(index)));
                System.out.println();
                index++;
            }
        }
        return data;
    }

    public static void showEvent(Stage stage) {

        // <editor-fold desc="boss, ito yung Table Header, mga buttons lang to.">
        Skin skin = new Skin(Gdx.files.internal("JSON/jsonTry.json"));
        skin.getFont("default-font").setScale(.4f);

        TextButton[] header = new TextButton[3];
        header[0] = new TextButton("EVENT TITLE", skin);
        header[1] = new TextButton("DATE", skin);
        header[2] = new TextButton("STATUS", skin);

        header[0].setBounds(350, 700, 500, 50);
        header[1].setBounds(850, 700, 210, 50);
        header[2].setBounds(1058, 700, 180, 50);

        for (int i = 0; i < 3; i++) {
            header[i].setColor(Color.LIGHT_GRAY);
            header[i].setDisabled(true);
            stage.addActor(header[i]);
        }
        // </editor-fold>


        // <editor-fold desc="Ito naman yung mismong table">
        Table table = new Table(skin);
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setBounds(350, 120, 890, 580);

        long current = System.currentTimeMillis();
        for (int index = 0; index < Event.loadData().size(); index++) {
            checkBox[index] = new CheckBox("", skin);

            String event = Event.loadData().get(index).eventName;
            long time = Event.loadData().get(index).timeInMillis;

            toDelete.add("{\"eventName\":\"" + event + "\",\"timeInMillis\":" + time + "}");

            String date = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(time);
            String hour = new SimpleDateFormat("hh:mm a", Locale.US).format(time);
            String testString = hour + "\n" + date;

            Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("default-font"), Color.WHITE);
            Label label = new Label(testString, labelStyle);
            label.setAlignment(1);

            String status;
            if (current < time) {
                status = "ON-GOING";
            } else if (current == time) {
                Assets.music[1].play();
//                Assets.music[Variables.getNextTone()].play();
                status = "DONE";
            } else {
                status = "DONE";
            }
            Label statusLabel = new Label(status, labelStyle);
            statusLabel.setAlignment(1, 1);

            table.add(checkBox[index]).width(40).padRight(10);
            table.add(event).width(480).height(120);
            table.add(label).width(210);
            table.add(statusLabel).width(130);
            table.row();
        }

        table.left().top();
        stage.addActor(scrollPane);
        // </editor-fold>
    }

    public static void deleteEvent(String lineToRemove) {

        fileTemp.writeString("", false);
        File inputFile = file.file();
        File tempFile = fileTemp.file();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            inputFile.setWritable(true);

            //Files.deleteIfExists(inputFile.toPath());
            //FileHandle fileHandle = new FileHandle(tempFile);
            fileTemp.copyTo(file);
            //Files.deleteIfExists(tempFile.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteEvent(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            deleteEvent(list.get(i));
        }
    }

    public static void checkAlarms(Stage stage) {
        long current = System.currentTimeMillis();
//        current = current - (current % 60000);
        for (int index = 0; index < Event.loadData().size(); index++) {
            long time = Event.loadData().get(index).timeInMillis;
            if (current - (current % 1000) == time - (time % 1000)) {

//                Variables.setAlarmOff(false);
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new Jajanken(stage));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Jajanken(stage));
            }
        }
    }
}