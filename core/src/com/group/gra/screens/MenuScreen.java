package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MenuScreen implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont blackFont;
    private SpriteBatch sb;

    public MenuScreen(SpriteBatch sb) {
        this.sb = sb;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(atlas);

        TextButton.TextButtonStyle textButtonStyle = createTextButtonStyle();

        TextButton buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.pad(20);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(sb));
            }
        });

        TextButton buttonSettings = new TextButton("Settings", textButtonStyle);
        buttonSettings.pad(20);
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen(sb));
            }
        });

        TextButton buttonQuit = new TextButton("Quit", textButtonStyle);
        buttonQuit.pad(20);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = configureTable(buttonPlay, buttonSettings, buttonQuit);
        stage.addActor(table);
    }

    private Table configureTable(TextButton buttonPlay, TextButton buttonSettings, TextButton buttonExit) {
        Table table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.defaults().pad(10).fillX();
        table.add(buttonPlay).row();
        table.add(buttonSettings).row();
        table.add(buttonExit).row();
        return table;
    }

    private TextButton.TextButtonStyle createTextButtonStyle() {
        blackFont = new BitmapFont(Gdx.files.internal("fonts/black.fnt"), false);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("default-round");
        textButtonStyle.down = skin.getDrawable("default-round-down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = blackFont;
        return textButtonStyle;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
