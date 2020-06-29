package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.managers.SoundManager;
import com.group.gra.uifactory.UIFactory;

import static com.group.gra.EkoGra.*;

public class SettingsScreen implements Screen {
    public static final String LEVEL_MODE_LABEL = "Level mode";
    public static final String TRAINING_MODE_LABEL = "Training mode";
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch sb;
    public Sprite spriteBackground;
    private Sound buttonClickedSound;
    private Preferences prefs;

    public SettingsScreen(SpriteBatch sb) {
        this.sb = sb;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/design.atlas");
        skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);
        prefs = Gdx.app.getPreferences(SETTINGS_FILE);

        TextButton buttonComeBack = new TextButton("Quit", skin);
        final TextButton buttonGameMode = createButtonGameMode();
        final TextButton buttonSpeed = createButtonSpeed();

        Button buttonSound = createButtonSound();

        buttonSound.setBounds(0,380,100,100);

        addButtonSoundListener(buttonSound);
        addButtonGameModeListener(buttonGameMode, buttonSpeed);
        addButtonSpeedListener(buttonSpeed);
        addButtonComeBackListener(buttonComeBack);

        buttonGameMode.pad(20);
        buttonSpeed.pad(20);
        buttonComeBack.pad(20);

        UIFactory uiFactory = new UIFactory();
        spriteBackground = uiFactory.createSpriteBackground("menuBackground.png",800,480);
        Label labelScreen = new Label("Settings", skin);
        labelScreen.setColor(Color.BLACK);
        Label labelGameMode = new Label("Game mode:", skin);
        labelGameMode.setColor(Color.BLACK);
        Label labelGameSpeed = new Label("Game speed:", skin);
        labelGameSpeed.setColor(Color.BLACK);
        labelScreen.setAlignment(Align.center);
        labelGameMode.setAlignment(Align.right);
        labelGameSpeed.setAlignment(Align.right);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.defaults().pad(10).fillX().uniform();
        table.add(labelScreen).expandX().colspan(3).row();
        table.add(labelGameMode);
        table.add(buttonGameMode);
        table.add().row();
        table.add(labelGameSpeed);
        table.add(buttonSpeed);
        table.add().row();
        table.add();
        table.add(buttonComeBack);
        table.add().row();
        table.center();

        stage.addActor(table);
        stage.addActor(buttonSound);
        buttonClickedSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonClicked.mp3"));
        buttonClickedSound.setVolume(1,0.1f);
    }

    private void addButtonComeBackListener(TextButton buttonComeBack) {
        buttonComeBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
            }
        });
    }

    private void addButtonSpeedListener(final TextButton buttonSpeed) {
        buttonSpeed.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                if (prefs.getInteger(GAME_SPEED) == 3) {
                    prefs.putInteger(GAME_SPEED, 1).flush();
                } else {
                    prefs.putInteger(GAME_SPEED, prefs.getInteger(GAME_SPEED) + 1).flush();
                }
                buttonSpeed.setText(Integer.toString(prefs.getInteger(GAME_SPEED)));

            }
        });
    }

    private void addButtonGameModeListener(final TextButton buttonGameMode, final TextButton buttonSpeed) {
        buttonGameMode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                int mode = prefs.getInteger(GAME_MODE);
                if (mode == 1) {
                    buttonGameMode.setText(TRAINING_MODE_LABEL);
                    prefs.putInteger(GAME_MODE, 2).flush();
                    buttonSpeed.setTouchable(Touchable.enabled);
                } else if (mode == 2) {
                    buttonGameMode.setText(LEVEL_MODE_LABEL);
                    prefs.putInteger(GAME_MODE, 1).flush();
                    buttonSpeed.setTouchable(Touchable.disabled);
                }
            }
        });
    }
    private TextButton createButtonGameMode() {
        if (prefs.getInteger(GAME_MODE) == 1) {
            return new TextButton(LEVEL_MODE_LABEL, skin);
        } else {
            return new TextButton(TRAINING_MODE_LABEL, skin);
        }
    }

    private TextButton createButtonSpeed() {
        TextButton buttonSpeed = new TextButton(Integer.toString(prefs.getInteger(GAME_SPEED)), skin);
        if (prefs.getInteger(GAME_MODE) == 1) {
            buttonSpeed.setTouchable(Touchable.disabled);
        } else {
            buttonSpeed.setTouchable(Touchable.enabled);
        }
        return buttonSpeed;
    }

    private void addButtonSoundListener(final Button buttonSound) {
        buttonSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                if (prefs.getBoolean(SOUND_ON,true)) {
                    prefs.putBoolean(SOUND_ON, false).flush();
                    buttonSound.setStyle(skin.get("sound_off",Button.ButtonStyle.class));
                    SoundManager.pauseMenuScreenMusic();

                }
                else {
                    prefs.putBoolean(SOUND_ON, true).flush();
                    buttonSound.setStyle(skin.get("sound_on",Button.ButtonStyle.class));
                    SoundManager.playMenuScreenMusic();
                }
            }
        });
    }
    private Button createButtonSound() {
        if (prefs.getBoolean(SOUND_ON,true)) {
            return new Button(skin.get("sound_on",Button.ButtonStyle.class));
        } else {
            return new Button(skin.get("sound_off",Button.ButtonStyle.class));
        }
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
