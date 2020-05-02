package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

import static com.group.gra.EkoGra.*;

public class SettingsScreen implements Screen {
    public static final String PRZYGODOWY = "Przygodowy";
    public static final String TRENINGOWY = "Treningowy";
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch sb;
    public Sprite spriteBackground;

    public SettingsScreen(SpriteBatch sb) {
        this.sb = sb;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);

        TextButton buttonComeBack = new TextButton("Powrót MainMenu", skin);
        final TextButton buttonGameMode = createButtonGameMode(prefs);
        final TextButton buttonSpeed = createButtonSpeed(prefs);

        addButtonGameModeListener(buttonGameMode, buttonSpeed);
        addButtonSpeedListener(buttonSpeed);
        addButtonComeBackListener(buttonComeBack);

        buttonGameMode.pad(20);
        buttonSpeed.pad(20);
        buttonComeBack.pad(20);

        createBackground();
        Label labelScreen = new Label("Ustawienia", skin);
        Label labelGameMode = new Label("Tryb gry:", skin);
        Label labelGameSpeed = new Label("Szybkość:", skin);
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
        table.top();
        stage.addActor(table);
    }

    private void addButtonComeBackListener(TextButton buttonComeBack) {
        buttonComeBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
            }
        });
    }

    private void addButtonSpeedListener(final TextButton buttonSpeed) {
        buttonSpeed.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
                if (prefs.getInteger(GAME_SPEED) == 10) {
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
                Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
                int mode = prefs.getInteger(GAME_MODE);
                if (mode == 1) {
                    buttonGameMode.setText(TRENINGOWY);
                    prefs.putInteger(GAME_MODE, 2).flush();
                    buttonSpeed.setTouchable(Touchable.enabled);
                } else if (mode == 2) {
                    buttonGameMode.setText(PRZYGODOWY);
                    prefs.putInteger(GAME_MODE, 1).flush();
                    buttonSpeed.setTouchable(Touchable.disabled);
                }
            }
        });
    }
    private TextButton createButtonGameMode(Preferences prefs) {
        if (prefs.getInteger(GAME_MODE) == 1) {
            return new TextButton(PRZYGODOWY, skin);
        } else {
            return new TextButton(TRENINGOWY, skin);
        }
    }
    private TextButton createButtonSpeed(Preferences prefs) {
        TextButton buttonSpeed = new TextButton(Integer.toString(prefs.getInteger(GAME_SPEED)), skin);
        if (prefs.getInteger(GAME_MODE) == 1) {
            buttonSpeed.setTouchable(Touchable.disabled);
        } else {
            buttonSpeed.setTouchable(Touchable.enabled);
        }
        return buttonSpeed;
    }
    private void createBackground() {
        Texture backgroundTexture = new Texture("settinsbackground.png");
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
