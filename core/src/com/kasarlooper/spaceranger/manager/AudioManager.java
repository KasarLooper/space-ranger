package com.kasarlooper.spaceranger.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.kasarlooper.spaceranger.GameResources;

public class AudioManager {
    public static Music spaceMusic;
    public static Sound soundBullet;
    public static Sound soundBoom;
    public static Sound soundEnergyGive;
    public static Sound soundShot;
    public static Sound soundWalking;
    public static Music planetMusic;
    public static Sound soundDamageEnemy;
    public static Sound soundDamageCosmonaut;


    static {
        spaceMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.MUSIC_SPACE_PATH));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal(GameResources.SOUND_BULLET_PATH));
        soundBoom = Gdx.audio.newSound(Gdx.files.internal(GameResources.SOUND_BOOM_PATH));
        soundEnergyGive = Gdx.audio.newSound(Gdx.files.internal(GameResources.SOUND_ENERGY_PATH));
        planetMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.MUSIC_PLANET_PATH));
        soundShot = Gdx.audio.newSound(Gdx.files.internal(GameResources.SOUND_SHOT_PATH));
        soundWalking = Gdx.audio.newSound(Gdx.files.internal(GameResources.SOUND_WALKING_PATH));
        soundDamageEnemy = Gdx.audio.newSound(Gdx.files.internal(GameResources.SOUND_DAMAGE_ENEMY));
        soundDamageCosmonaut = Gdx.audio.newSound(Gdx.files.internal(GameResources.SOUND_DAMAGE_COSMONAUT));

        spaceMusic.setVolume(0.2f);
        planetMusic.setVolume(0.2f);

        spaceMusic.setLooping(true);
        planetMusic.setLooping(true);
    }


}
