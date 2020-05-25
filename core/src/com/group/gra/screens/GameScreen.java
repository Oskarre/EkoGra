package com.group.gra.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.trashes.Trash;
import com.group.gra.trashes.TrashGenerator;

public class GameScreen implements Screen {
    public static final int DELAY_TIME = 2;
    private Stage stage;
    private SpriteBatch sb;
    private Sprite spriteBackground;
    private Sprite plasticContainer;
    private Sprite paperContainer;
    private Sprite hazardousContainer;
    private Sprite glassContainer;
    private Sprite bioContainer;
    private Sprite container;
    private Sprite maciag;
    private Music backgroundMusic;

    public GameScreen(SpriteBatch sb) {
        this.sb = sb;
        spriteBackground = new Sprite(new Texture("gameScreenBackground.png"));
        spriteBackground.setSize(800, 480);

        plasticContainer = new Sprite(new Texture("plasticContainer.png"));
        plasticContainer.setSize(100, 100);
        plasticContainer.setPosition(100, 150);

        paperContainer = new Sprite(new Texture("paperContainer.png"));
        paperContainer.setSize(100, 100);
        paperContainer.setPosition(200, 150);

        glassContainer = new Sprite(new Texture("glassContainer.png"));
        glassContainer.setSize(100, 100);
        glassContainer.setPosition(300, 150);

        hazardousContainer = new Sprite(new Texture("hazardousContainer.png"));
        hazardousContainer.setSize(100, 100);
        hazardousContainer.setPosition(400, 150);

        bioContainer = new Sprite(new Texture("bioContainer.png"));
        bioContainer.setSize(100, 100);
        bioContainer.setPosition(500, 150);

        container = new Sprite(new Texture("container.png"));
        container.setSize(100, 100);
        container.setPosition(600, 150);
        maciag = new Sprite(new Texture("maciag_1.png"));
        maciag.setSize(1500
                , 200);
        maciag.setPosition(-350, 0);

    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        displayTrashes();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameScreenMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.play();

    }
    private void displayTrashes() {
        TrashGenerator generator = new TrashGenerator();
        Array<Trash> trashArray = generator.generateTrashArray(50);

        int x = DELAY_TIME;
        for (Trash t : trashArray) {
            Image image = t.getImage();
            image.setPosition(0, 100);
            SequenceAction sequence = new SequenceAction(Actions.hide(), Actions.delay(x), Actions.show(),
                    Actions.moveTo(640, 100, 4f), Actions.hide());
            image.addAction(sequence);
            stage.addActor(image);
            x += DELAY_TIME;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        spriteBackground.draw(sb);
        plasticContainer.draw(sb);
        paperContainer.draw(sb);
        bioContainer.draw(sb);
        glassContainer.draw(sb);
        hazardousContainer.draw(sb);
        container.draw(sb);
        maciag.draw(sb);
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
        backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        spriteBackground.getTexture().dispose();
        sb.dispose();
        backgroundMusic.dispose();
    }
}
