package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.GameResources;

public class AudioManager {
    public Music spaceMusic;
    //public Sound sound_bullet;

    public AudioManager() {
        spaceMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.MUSIC_SPACE_PATH)); // "spaceMusic/Electric_Dark_Souls-Next_Level.mp3"

        spaceMusic.setVolume(0.2f);
        spaceMusic.setLooping(true);

        spaceMusic.play();
    }

}
