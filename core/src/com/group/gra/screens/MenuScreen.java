package com.group.gra.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.GameManager;
import com.group.gra.managers.SoundManager;
import com.group.gra.uifactory.UIFactory;

import static com.group.gra.EkoGra.SETTINGS_FILE;
import static com.group.gra.EkoGra.SOUND_ON;


public class MenuScreen implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch sb;
    public Sprite spriteBackground;
    private Sound buttonClickedSound;
    private Preferences prefs;

    public MenuScreen(SpriteBatch sb) {
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

        UIFactory uiFactory = new UIFactory();
        spriteBackground = uiFactory.createSpriteBackground("menuBackground.png",800,480);

        buttonClickedSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonClicked.mp3"));
        buttonClickedSound.setVolume(1,0.1f);

        TextButton buttonPlay = new TextButton("Play", skin);
        TextButton buttonSettings = new TextButton("Settings", skin);
        TextButton buttonCredits = new TextButton("Credits", skin);
        TextButton buttonQuit = new TextButton("Quit", skin);
        Button buttonInstruction = new Button(skin.get("info",Button.ButtonStyle.class));
        buttonInstruction.setBounds(0,0,100,100);
        addButtonPlayListener(buttonPlay);
        addButtonSettingsListener(buttonSettings);
        addButtonInstructionListener(buttonInstruction);
        addButtonQuitListener(buttonQuit);
        addButtonCreditsListener(buttonCredits);

        buttonPlay.pad(20);
        buttonSettings.pad(20);
        buttonQuit.pad(20);
        buttonCredits.pad(20);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.defaults().pad(10).fillX();
        table.add(buttonPlay).row();
        table.add(buttonSettings).row();
        table.add(buttonCredits).row();
        table.add(buttonQuit).row();

        stage.addActor(table);
        stage.addActor(buttonInstruction);
    }

    private void addButtonQuitListener(TextButton buttonQuit) {
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                Gdx.app.exit();
            }
        });

    }

    private void addButtonSettingsListener(TextButton buttonSettings) {
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen(sb));
            }
        });
    }

    private void addButtonInstructionListener(Button buttonInstruction) {
        buttonInstruction.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new InstructionScreen(sb));
            }
        });
    }
    private void addButtonCreditsListener(Button buttonInstruction) {
        buttonInstruction.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new CreditsScreen(sb));
            }
        });
    }

    private void addButtonPlayListener(TextButton buttonPlay) {
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                    SoundManager.pauseMenuScreenMusic();
                    SoundManager.playGameScreenMusic();
                }
                GameManager  manager = new GameManager();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(sb, manager.getGameConfiguration()));

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
        sb.dispose();
        spriteBackground.getTexture().dispose();
    }

}
