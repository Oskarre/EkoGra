package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.uifactory.UIFactory;

import static com.group.gra.EkoGra.SETTINGS_FILE;
import static com.group.gra.EkoGra.SOUND_ON;

public class CreditsScreen implements Screen {

    public Sprite spriteBackground;
    private SpriteBatch sb;
    private Stage stage;
    private Sound buttonClickedSound;
    private Preferences prefs;

    public CreditsScreen(SpriteBatch sb) {
        this.sb=sb;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        UIFactory uiFactory = new UIFactory();
        spriteBackground = uiFactory.createSpriteBackground("menuBackground.png",800,480);
        Button returnButton = uiFactory.createReturnButton(750,0,50,50);
        addReturnButtonListener(returnButton);

        prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        buttonClickedSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonClicked.mp3"));
        buttonClickedSound.setVolume(1,0.1f);

        TextureAtlas atlas = new TextureAtlas("ui/design.atlas");
        Skin skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);
        Label creditsText = new Label("Font: \nhttps://www.fontspace.com/nunito-font-f44025\n" +
                "\nMenu screen music: \n" +
                "airtone - reCreation - \nhttp://dig.ccmixter.org/files/airtone/59721\n" +
                "Game screen music: \n" +
                "Stefan Kartenberg - Guitalele's Happy Place - \nhttp://dig.ccmixter.org/files/JeffSpeed68/56194\n" +
                "Other sounds: \nhttp://www.freesfx.co.uk\n" +
                "\nGraphics:\n" +
                "Scene Vectors by Vecteezy: https://www.vecteezy.com/free-vector/scene\n" +
                "Wood Vectors by Vecteezy: https://www.vecteezy.com/free-vector/wood\n" +
                "Trash Vectors by Vecteezy: https://www.vecteezy.com/free-vector/trash\n" +
                "Isolated Vectors by Vecteezy: https://www.vecteezy.com/free-vector/isolated\n" +
                "Apparel Vectors by Vecteezy: https://www.vecteezy.com/free-vector/apparel\n" +
                "Steak Vectors by Vecteezy: https://www.vecteezy.com/free-vector/steak\n" +
                "Shit Vectors by Vecteezy: https://www.vecteezy.com/free-vector/shit\n" +
                "Icon Vectors by Vecteezy: https://www.vecteezy.com/free-vector/icon\n" +
                "Biodegradable Vectors by Vecteezy: https://www.vecteezy.com/free-vector/biodegradable\n"+
                "Organic Vectors by Vecteezy: https://www.vecteezy.com/free-vector/organic"
                ,skin);
        creditsText.setWrap(true);
        creditsText.setWidth(750);
        ScrollPane scrollPane = new ScrollPane(creditsText,skin);
        scrollPane.setSize(stage.getWidth(), stage.getHeight());
        scrollPane.setupFadeScrollBars(0,0);
        scrollPane.setOverscroll(false,false);
        scrollPane.setClamp(true);
        scrollPane.setFillParent(true);

        stage.addActor(scrollPane);
        stage.addActor(returnButton);
    }

    private void addReturnButtonListener(Button returnButton) {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        spriteBackground.draw(sb);
        sb.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        spriteBackground.getTexture().dispose();
        sb.dispose();
    }
}
