package ph.com.pasig.thesis.notify.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by iamAxylle on 3/11/2015.
 */
public class Assets {

    public static AssetManager manager;
    public static TextureRegion[][] months = new TextureRegion[2][12];
    public static TextureRegion[][] weeks = new TextureRegion[2][7];
    public static TextureRegion[][] days = new TextureRegion[3][31];
    public static TextureRegion[] sidePanel = new TextureRegion[4];
    public static TextureRegion[] misc = new TextureRegion[8];
    public static TextureRegion[] bg = new TextureRegion[15];
    public static TextureRegion[] back = new TextureRegion[15];
    public static TextureRegion[] next = new TextureRegion[15];
    public static TextureRegion[] jajanken = new TextureRegion[6];
    public static TextureRegion versus;


    public static Image[] question = new Image[3];
    public static Image[] answers = new Image[3];
    public static Music[] music = new Music[21];
    private static TextureAtlas atlas, batoAtlas;
    private static String assetFile = "images/notify.pack";
    private static String batobatopick = "images/jajanken.pack";
    private static BitmapFont fontLabel;
    private static BitmapFont fontLabel2;

    public static void load() {
        manager = new AssetManager();
        manager.load(assetFile, TextureAtlas.class);
        loadImages();
        loadMusic();
        loadFonts();
    }

    private static void loadFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/notify.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 72;
        fontLabel = generator.generateFont(parameter);
        fontLabel.setColor(0,0,0,1);
        parameter.size = 36;
        fontLabel2 = generator.generateFont(parameter);
        generator.dispose();
    }

    public static BitmapFont getFontLabel(){
        return fontLabel;
    }

    public static BitmapFont getFontLabel2(){
        return fontLabel2;
    }

    private static TextureAtlas getAtlas() {
        if (atlas == null) {
            atlas = new TextureAtlas(Gdx.files.internal(assetFile));
        }
        return atlas;
    }

    private static TextureAtlas getBatoAtlas(){
        if(batoAtlas == null){
            batoAtlas = new TextureAtlas(Gdx.files.internal(batobatopick));
        }
        return batoAtlas;
    }

    private static void loadImages() {
        versus = getBatoAtlas().findRegion(Variables.vs);
        //jajanken
        for(int i = 0; i < 6; i++){
            jajanken[i] = getBatoAtlas().findRegion(Variables.jajanken[i]);
        }
        for(int i = 0; i < 3; i++){
            answers[i] = new Image(getBatoAtlas().findRegion(Variables.answers[i]));
        }
        for(int i = 0; i < 3; i++){
            question[i] = new Image(getBatoAtlas().findRegion(Variables.question[i]));
        }


        // Months  White
        for (int i = 0; i < 12; i++) {
            months[0][i] = getAtlas().findRegion("months/" + Variables.month[i]);
        }

        // Months  Black
        for (int i = 0; i < 12; i++) {
            months[1][i] = getAtlas().findRegion("months/" + Variables.month[i]+"b");
        }

        // Weeks White
        for (int i = 0; i < 7; i++) {
            weeks[0][i] = getAtlas().findRegion("weeks/" + Variables.week[i]);
        }

        // Weeks Black
        for (int i = 0; i < 7; i++) {
            weeks[1][i] = getAtlas().findRegion("weeks/" + Variables.week[i]+"b");
        }
        // Days
        for (int i = 0; i < 31; i++) {
            // Normal
            days[0][i] = getAtlas().findRegion("days/" + Variables.days[i]);

            //Special
            days[1][i] = getAtlas().findRegion("days/" + Variables.days[i] + "b");

            //Current
            days[2][i] = getAtlas().findRegion("days/" + Variables.days[i] + "c");
        }

        // Side Panel
        for (int i = 0; i < 4; i++) {
            sidePanel[i] = getAtlas().findRegion("sidePanel/" + Variables.sidePanel[i]);
        }

        // Misc
        for (int i = 0; i < 8; i++) {
            misc[i] = getAtlas().findRegion("misc/" + Variables.misc[i]);
        }

        // Background
        for (int i = 0; i < 15; i++) {
            bg[i] = getAtlas().findRegion("bg/" + Variables.bg[i]);
        }

        // Back
        for (int i = 0; i < 15; i++) {
            back[i] = getAtlas().findRegion("back/" + Variables.bg[i] + "back");
        }

        // Next
        for (int i = 0; i < 15; i++) {
            next[i] = getAtlas().findRegion("next/" + Variables.bg[i] + "next");
        }
    }

    public static void loadMusic() {
        for (int i = 0; i < 21; i++) {
            music[i] = Gdx.audio.newMusic(Gdx.files.internal("sounds/" + Variables.tones[i]));
        }
    }

    public static void dispose() {
        manager.unload(assetFile);
        manager.dispose();
        fontLabel.dispose();
    }
}
