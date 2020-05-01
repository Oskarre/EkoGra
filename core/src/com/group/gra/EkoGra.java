package com.group.gra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group.gra.screens.SplashScreen;

public class EkoGra extends Game {
    public static final String TITLE = "EkoGra";
    public SpriteBatch sb;

    @Override
    public void create() {
        sb = new SpriteBatch();
        setScreen(new SplashScreen(sb));

        //ustawienie wartosci domyslnych ustawien jeśli jeszcze nie istnieją
        Preferences prefs = Gdx.app.getPreferences("EkoGra.settings");
        if(prefs.getBoolean("firstLaunch",true)) { //warunek przechodzi tylko za pierwszym odpaleniem aplikacji na urzadzeniu
        prefs.putInteger("GAME_MODE",1);
        prefs.putInteger("GAME_SPEED",1);
        prefs.putBoolean("firstLaunch",false);
    }
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
        Preferences prefs = Gdx.app.getPreferences("EkoGra.settings");
        prefs.flush();
        super.dispose();
    }
}
