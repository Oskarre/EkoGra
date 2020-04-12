package com.group.gra.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group.gra.tween.SpriteAccessor;

public class Splash implements Screen {

    private Sprite spriteBackground;
    private Sprite spriteTitle;
    private Texture backgroundTexture;
    private Texture titleTexture;
    private SpriteBatch sb;
    private TweenManager tweenmanager;

    @Override
    public void show() {
        sb = new SpriteBatch();
        tweenmanager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        backgroundTexture = new Texture("backgroundTexture.jpg");
        titleTexture = new Texture("title.png");
        spriteBackground = new Sprite(backgroundTexture);
        spriteTitle = new Sprite(titleTexture);

        spriteBackground.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteTitle.setPosition(Gdx.graphics.getWidth()/2-spriteTitle.getWidth()/2,Gdx.graphics.getHeight()/2-spriteTitle.getHeight()/2);

        Tween.set(spriteTitle,SpriteAccessor.ALPHA).target(1).start(tweenmanager);
        Tween.to(spriteTitle,SpriteAccessor.ALPHA,2).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }).start(tweenmanager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); // sets the color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clearing with that color

        tweenmanager.update(delta);

        sb.begin();
        spriteTitle.draw(sb);
        spriteBackground.draw(sb);
        spriteTitle.draw(sb);
        sb.end();
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
        sb.dispose();
        spriteBackground.getTexture().dispose();
        spriteTitle.getTexture().dispose();
    }
}
