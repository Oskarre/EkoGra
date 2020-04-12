package com.group.gra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu implements Screen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin; // the appearnce of our buttons
    private Table table;
    private TextButton buttonPlay, buttonExit, buttonSettings; // background of a textbutton is a picture
    private BitmapFont whiteFont,blackFont,deafultFont; // We can also use TrueType font(eg Windows font(?))
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameterWhiteFont, parameterBlackFont;
    private Label heading; // only plain text no image has a font
    @Override
    public void show() {
        //Creation of a font from a true type font
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VCR_OSD_MONO_1.001.ttf"));
        parameterWhiteFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterBlackFont =  new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameterWhiteFont.size = 24;
        parameterWhiteFont.borderWidth = 2;
        parameterWhiteFont.borderColor = Color.BLACK;
        parameterWhiteFont.color = Color.WHITE;
        whiteFont = fontGenerator.generateFont(parameterWhiteFont);

        //fast creation of a new color font
        parameterBlackFont = parameterWhiteFont; // probably will be fucked
        parameterBlackFont.borderColor = Color.WHITE;
        parameterBlackFont.color = Color.BLACK;
        blackFont = fontGenerator.generateFont(parameterBlackFont);

        //creation of a font from Bitmap lol only 1 line kek. You need to have .fnt and .png file since it is BitMapFont. Otherwise it won't work
        //deafultFont = new BitmapFont(Gdx.files.internal("fonts/default.fnt"),false);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(atlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("default-round");
        textButtonStyle.down = skin.getDrawable("default-round-down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = whiteFont;
        buttonSettings = new TextButton("Settings",textButtonStyle);
        buttonSettings.pad(20);
        buttonExit = new TextButton("Exit",textButtonStyle);
        buttonExit.pad(20);
        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.pad(20);


        table.defaults().pad(10).fillX();
        table.add(buttonPlay).row();
        table.add(buttonSettings).row();
        table.add(buttonExit).row();

        stage.addActor(table);

        //debugging.
        //table.debugAll();
        //stage.setDebugAll(true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height); //if you want to work after maximizing
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

    }
}
