package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music music;
    //public Sound sound_bullet;

    public AudioManager() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Electric_Dark_Souls-Next_Level.mp3"));

        music.setVolume(0.2f);
        music.setLooping(true);

        music.play();
    }

}
