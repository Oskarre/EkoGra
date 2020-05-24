package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MenuScreen implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch sb;
    public Sprite spriteBackground;

    public MenuScreen(SpriteBatch sb) {
        this.sb = sb;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);


        TextButton buttonPlay = new TextButton("Play", skin);
        TextButton buttonSettings = new TextButton("Settings", skin);
        TextButton buttonQuit = new TextButton("Quit", skin);

        addButtonPlayListener(buttonPlay);
        addButtonSettingsListener(buttonSettings);
        addButtonQuitListener(buttonQuit);

        buttonPlay.pad(20);
        buttonSettings.pad(20);
        buttonQuit.pad(20);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.defaults().pad(10).fillX();
        table.add(buttonPlay).row();
        table.add(buttonSettings).row();
        table.add(buttonQuit).row();

        stage.addActor(table);

        createSpriteBackground();
    }

    private void addButtonQuitListener(TextButton buttonQuit) {
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void addButtonSettingsListener(TextButton buttonSettings) {
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen(sb));
            }
        });
    }

    private void addButtonPlayListener(TextButton buttonPlay) {
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(sb));
            }
        });
    }

    private void createSpriteBackground() {
        Texture backgroundTexture = new Texture("gameScreenBackground.png");
        spriteBackground = new Sprite(backgroundTexture);
        spriteBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        atlas.dispose();
        skin.dispose();
        stage.dispose();
        sb.dispose();
        sb.dispose();
    }
}
