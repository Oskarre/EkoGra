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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.GameManager;
import com.group.gra.entities.MatchStatus;
import com.group.gra.entities.Result;
import com.group.gra.uifactory.UIFactory;

import static com.group.gra.EkoGra.*;

public class SummaryScreen implements Screen {
    public Sprite spriteBackground;
    private SpriteBatch sb;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Array<Result> matches;
    private boolean isWin;
    private Sound buttonClickedSound;
    private Preferences prefs;

    public SummaryScreen(SpriteBatch sb, Array<Result> matches, boolean isWin) {
        this.sb = sb;
        this.matches = matches;
        this.isWin = isWin;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        atlas = new TextureAtlas("ui/design.atlas");
        skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);
        prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        Gdx.input.setInputProcessor(stage);

        createTableWithResults();
        UIFactory uiFactory = new UIFactory();
        Button returnButton = uiFactory.createReturnButton(600, 20, 150, 150);
        addReturnButtonListener(returnButton);
        stage.addActor(returnButton);
        spriteBackground = uiFactory.createSpriteBackground("menuBackground.png");

        int actualLevel = prefs.getInteger(LEVEL);

        if(isWin && actualLevel <=3){
            prefs.putInteger(LEVEL, actualLevel + 1).flush();
            Button playButton = uiFactory.createPlayButton();
            addPlayButtonListener(playButton);
            stage.addActor(playButton);
        }

        buttonClickedSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonClicked.mp3"));
        buttonClickedSound.setVolume(1,0.1f);
    }

    private void createTableWithResults() {
        atlas = new TextureAtlas("ui/design.atlas");
        skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);


        Table table = new Table(skin);
        table.left();
        table.defaults().pad(2).fillX();
        fillTableByUserResult(table);
        ScrollPane scrollPane = new ScrollPane(table,skin);
        scrollPane.setSize(stage.getWidth(), stage.getHeight());
        scrollPane.setupFadeScrollBars(0,0);
        scrollPane.setOverscroll(false,false);
        scrollPane.setClamp(true);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
    }

    private void fillTableByUserResult(Table table) {
        int gameMode = prefs.getInteger(GAME_MODE);
        if(isWin && gameMode == LEVEL_MODE){
            Label label = new Label("Congratulation! You pass the Level " + prefs.getInteger(LEVEL) +"!", skin);
            table.add(label);
            table.row();
        }else if( gameMode == LEVEL_MODE){
            Label label = new Label("You did not pass the Level " + prefs.getInteger(LEVEL) +"!", skin);
            table.add(label);
            table.row();
        }
        for (Result match : matches) {
            Label label = new Label(match.getName(), skin);
            if (match.getStatus().equals(MatchStatus.Wrong)) {
                label.setColor(Color.RED);
            } else {
                label.setColor(Color.GREEN);
            }
            table.add(label);
            table.row();
        }
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

    private void addPlayButtonListener(Button returnButton) {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean(SOUND_ON)) {
                    buttonClickedSound.play();
                }
                GameManager gameManager = new GameManager();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(sb, gameManager.getGameConfiguration()));
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
        atlas.dispose();
        skin.dispose();
        stage.dispose();
        sb.dispose();
    }
}
