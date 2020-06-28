package com.group.gra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import com.group.gra.entities.GameParameters;

import static com.group.gra.EkoGra.*;

public class GameManager {

    public GameParameters getGameConfiguration(){
        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        int mode = prefs.getInteger(GAME_MODE);
        if (mode == TRENING_MODE) {
            int speed = prefs.getInteger(GAME_SPEED);
            switch(speed){
                case 1:
                    return new GameParameters(4f,2f, 50, 1f/25f);
                case 2:
                    return new GameParameters(3f,1.5f, 50, 1f/34f);
                case 3:
                    return new GameParameters(2f,1f, 50, 1f/50f);

            }
        } else if (mode == LEVEL_MODE) {
            int level =prefs.getInteger(LEVEL);
            switch(level){
                case 1:
                    return new GameParameters(4f,2f, 20, 1f/25f);
                case 2:
                    return new GameParameters(3f,1.5f, 30, 1f/34f);
                case 3:
                    return new GameParameters(2f,1f, 40, 1f/50f);

            }
        }
        return new GameParameters(4f,2f, 20, 1f/25f);
    }
}
