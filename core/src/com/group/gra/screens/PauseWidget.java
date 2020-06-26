package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.group.gra.EkoGra.SETTINGS_FILE;
import static com.group.gra.EkoGra.SOUND_ON;

public class PauseWidget {


    private Button buttonPause;
    private final TextButton buttonQuit;
    private final Button buttonSound;
    private final Window pauseWidget;
    private final Skin skin;
    private final SpriteBatch sb;
    private final Music backgroundMusic;
    private Stage stage;
    private final TextButton buttonResume;
    private boolean pause;

    PauseWidget(Music music, Stage gameScreenStage, SpriteBatch gameScreenSb) {
        backgroundMusic = music;
        stage = gameScreenStage;
        sb = gameScreenSb;
        pause=false;

        TextureAtlas atlas = new TextureAtlas("ui/design.atlas");
        skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);


        buttonResume = new TextButton("Resume",skin);
        addButtonResume(buttonResume);
        buttonQuit = new TextButton("Quit",skin);
        addButtonQuit(buttonQuit);

        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);

        buttonSound = createButtonSound(prefs);
        addButtonSoundListener(buttonSound);

        pauseWidget = new Window("", skin);
        pauseWidget.setSize(stage.getWidth(),stage.getHeight());
        pauseWidget.setMovable(false);

        Table table = new Table(skin);
        pauseWidget.add(table);

        table.add(buttonResume).row();
        table.add(buttonQuit).row();
        table.add(buttonSound).row();
    }



    private void addButtonResume(final TextButton buttonResume) {
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause=false;
                pauseWidget.addAction(Actions.removeActor());
            }
        });
    }

    private void addButtonQuit(final TextButton buttonQuit) {
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
            }
        });
    }

    private void addButtonSoundListener(final Button buttonSound) {
        buttonSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
                if (prefs.getBoolean(SOUND_ON,true)) {
                    prefs.putBoolean(SOUND_ON, false).flush();
                    buttonSound.setStyle(skin.get("sound_off",Button.ButtonStyle.class));
                    backgroundMusic.stop();
                }
                else {
                    prefs.putBoolean(SOUND_ON, true).flush();
                    buttonSound.setStyle(skin.get("sound_on",Button.ButtonStyle.class));
                    backgroundMusic.play();
                }
            }
        });
    }
    private Button createButtonSound(Preferences prefs) {
        if (prefs.getBoolean(SOUND_ON,true)) {
            return new Button(skin.get("sound_on",Button.ButtonStyle.class));
        } else {
            return new Button(skin.get("sound_off",Button.ButtonStyle.class));
        }
    }
    public boolean isGamePaused()
    {
        if(pause)
            return true;
        else return false;
    }

    private void addButtonPauseListener(Button buttonPause) {
        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause=true;
                stage.addActor(pauseWidget);
            }
        });
    }
    public void createButtonPause(int x, int y, int width, int height)
    {
        buttonPause = new Button(skin.get("pause",Button.ButtonStyle.class));
        buttonPause.setSize(width, height);
        addButtonPauseListener(buttonPause);
        stage.addActor(buttonPause);
    }

}
