package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.uifactory.UIFactory;

import static com.group.gra.EkoGra.SETTINGS_FILE;
import static com.group.gra.EkoGra.SOUND_ON;

public class CreditsScreen implements Screen {

    public Sprite spriteBackground;
    private SpriteBatch sb;
    private Stage stage;
    private Sound buttonClickedSound;
    private Preferences prefs;
    public CreditsScreen(SpriteBatch sb) {
        this.sb=sb;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        UIFactory uiFactory = new UIFactory();
        spriteBackground = uiFactory.createSpriteBackground("menuBackground.png",800,480);
        Button returnButton = uiFactory.createReturnButton(750,0,50,50);
        addReturnButtonListener(returnButton);
        stage.addActor(returnButton);
        prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        buttonClickedSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonClicked.mp3"));
        buttonClickedSound.setVolume(1,0.1f);
    }

    private void addReturnButtonListener(Button returnButton) {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
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
        spriteBackground.getTexture().dispose();
        sb.dispose();
    }
}
