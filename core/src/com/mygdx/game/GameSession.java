package com.mygdx.game;

import static com.mygdx.game.GameSettings.SPAWN_COOL_DOWN;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {
    private long startTime;
    private long lastSpawnTime;
    private int core_collected;

    public GameSession() {
        core_collected = 0;
    }

    public void startGame() {
        startTime = TimeUtils.millis();
        lastSpawnTime = TimeUtils.millis();
    }

    public boolean shouldSpawn() {
        if (TimeUtils.millis() - lastSpawnTime > SPAWN_COOL_DOWN) {
            lastSpawnTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    public void core_was_collected() {
        core_collected += 1;
    }

    public int getCoreCollected() {
        return core_collected;
    }

    public boolean victory() {
        return core_collected >= 3;
    }
}
