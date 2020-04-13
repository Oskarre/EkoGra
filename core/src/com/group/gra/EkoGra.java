package com.group.gra;

import com.badlogic.gdx.Game;
import com.group.gra.screens.Splash;

public class EkoGra extends Game {
    public static final String TITLE = "EkoGra";

    @Override
    public void create() {
        setScreen(new Splash());
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
