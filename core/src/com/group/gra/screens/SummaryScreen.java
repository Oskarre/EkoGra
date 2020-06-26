package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.group.gra.entities.MatchStatus;
import com.group.gra.entities.Result;
import com.group.gra.uifactory.UIFactory;

public class SummaryScreen implements Screen {
    public Sprite spriteBackground;
    private SpriteBatch sb;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Array<com.group.gra.entities.Result> matches;

    public SummaryScreen(SpriteBatch sb, Array<com.group.gra.entities.Result> matches) {
        this.sb = sb;
        this.matches = matches;
    }

    @Override
    public void show() {
        FitViewport viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        createTableWithResults();

        UIFactory uiFactory = new UIFactory();
        ImageButton returnButton = uiFactory.createReturnButton();
        addReturnButtonListener(returnButton);
        stage.addActor(returnButton);
        spriteBackground = uiFactory.createSpriteBackground("menuBackground.png");
    }

    private void createTableWithResults() {
        atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);

        Table table = new Table(skin);
        table.center();
        table.setFillParent(true);
        table.defaults().pad(2).fillX();
        fillTableByUserResult(table);
        stage.addActor(table);
    }

    private void fillTableByUserResult(Table table) {
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

    private void addReturnButtonListener(ImageButton returnButton) {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        atlas.dispose();
        skin.dispose();
        stage.dispose();
        sb.dispose();
        sb.dispose();
    }
}
