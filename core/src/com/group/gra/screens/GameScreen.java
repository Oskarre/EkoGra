package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch sb;
    public Sprite spriteBackground;

    public GameScreen(SpriteBatch sb) {
        this.sb = sb;
    }

    @Override
    public void show() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/black.fnt"), false);
        FitViewport viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"),atlas);

        TextButton buttonComeBack = new TextButton("Powrót MainMenu", skin);
        buttonComeBack.pad(20);
        buttonComeBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
            }
        });

        createBackground();
        Label label = new Label("Ekran Game",skin);

        Table table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.defaults().pad(10).fillX();
        table.add(label).row();
        table.add(buttonComeBack).row();

        stage.addActor(table);
    }

    private void createBackground() {
        Texture backgroundTexture = new Texture("grabackground.png");
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
        spriteBackground.getTexture().dispose();
        sb.dispose();
    }
}
