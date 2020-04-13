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

public class SplashScreen implements Screen {
    public Sprite spriteBackground;
    private Sprite spriteTitle;
    private SpriteBatch sb;
    private TweenManager tweenmanager;

    public SplashScreen(SpriteBatch sb) {
        this.sb = sb;
    }

    @Override
    public void show() {
        spriteBackground = createSpriteBackground("backgroundTexture.jpg");
        spriteTitle = createSpriteTitle("title.png");
        initializeTweenManager();
    }

    private Sprite createSpriteBackground(String spriteImage) {
        Texture texture = new Texture(spriteImage);
        Sprite sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return sprite;
    }

    private Sprite createSpriteTitle(String spriteImage) {
        Texture texture = new Texture(spriteImage);
        Sprite sprite = new Sprite(texture);
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
        return sprite;
    }

    private void initializeTweenManager() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        tweenmanager = new TweenManager();
        Tween.set(spriteTitle, SpriteAccessor.ALPHA).target(1).start(tweenmanager);
        Tween.to(spriteTitle, SpriteAccessor.ALPHA, 2).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
            }
        }).start(tweenmanager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenmanager.update(delta);

        sb.begin();
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
        spriteBackground.getTexture().dispose();
        spriteTitle.getTexture().dispose();
        tweenmanager.killAll();
        sb.dispose();
    }
}
