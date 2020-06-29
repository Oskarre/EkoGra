package com.group.gra.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager {
    private static Music menuScreenMusic;
    private static Music gameScreenMusic;


    public static void initSoundManager(){
        menuScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuScreenMusic.mp3"));
        menuScreenMusic.setLooping(true);
        menuScreenMusic.setVolume(0.1f);

        gameScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameScreenMusic.mp3"));
        gameScreenMusic.setLooping(true);
        menuScreenMusic.setVolume(0.1f);
    }

    public static void playGameScreenMusic() {
        gameScreenMusic.play();
    }

    public static void playMenuScreenMusic() {
        menuScreenMusic.play();
    }

    public static void pauseGameScreenMusic() {
        gameScreenMusic.pause();
    }

    public static void pauseMenuScreenMusic() {
        menuScreenMusic.pause();
    }
    public static void stopGameScreenMusic() {
        gameScreenMusic.stop();
    }

}
