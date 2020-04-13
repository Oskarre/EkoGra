package com.group.gra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group.gra.screens.SplashScreen;

public class EkoGra extends Game {
    public static final String TITLE = "EkoGra";
    public SpriteBatch sb;

    @Override
    public void create() {
        sb = new SpriteBatch();
        setScreen(new SplashScreen(sb));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
