package com.group.gra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group.gra.screens.Splash;

public class EkoGra extends Game {
	//SpriteBatch batch;
	public static final String TITLE = "EkoGra";

	@Override
	public void create () {
		//batch = new SpriteBatch();
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		setScreen(new Splash());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize (int width, int height) {
		super.resize(width,height);
	}

	@Override
	public void pause () {
		super.pause();
	}

	@Override
	public void resume () {
		super.resume();
	}
	@Override
	public void dispose () {
		//batch.dispose();
		super.dispose();
	}

}
