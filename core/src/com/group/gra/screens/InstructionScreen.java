package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.uifactory.UIFactory;

public class InstructionScreen implements Screen {
    public Sprite spriteBackground;
    private SpriteBatch sb;
    private Stage stage;

    public InstructionScreen(SpriteBatch sb) {
        this.sb = sb;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        UIFactory uiFactory = new UIFactory();
        spriteBackground = uiFactory.createSpriteBackground("Instruction.jpg");
        ImageButton returnButton = uiFactory.createReturnButton();
        addReturnButtonListener(returnButton);
        stage.addActor(returnButton);
    }

    private void addReturnButtonListener(ImageButton returnButton) {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        sb.dispose();
        sb.dispose();
    }
}
