package com.group.gra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group.gra.screens.SplashScreen;

public class EkoGra extends Game {
    public static final String TITLE = "EkoGra";
    public static final String GAME_MODE = "GAME_MODE";
    public static final String GAME_SPEED = "GAME_SPEED";
    public static final String SOUND_ON = "SOUND_ON";
    public static final String LEVEL = "LEVEL";
    public static final int LEVEL_MODE = 1;
    public static final int TRENING_MODE = 2;
    public static final String SETTINGS_FILE = "EkoGra.settings";
    private static final String FIRST_LAUNCH = "firstLaunch";

    public SpriteBatch sb;

    @Override
    public void create() {
        sb = new SpriteBatch();
        setScreen(new SplashScreen(sb));

        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        if (prefs.getBoolean(FIRST_LAUNCH, true)) {
            prefs.putInteger(GAME_MODE, 1);
            prefs.putInteger(GAME_SPEED, 1);
            prefs.putBoolean(SOUND_ON, true);
            prefs.putBoolean(FIRST_LAUNCH, false);
        }
        prefs.putInteger(LEVEL,1);
        prefs.flush();
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
