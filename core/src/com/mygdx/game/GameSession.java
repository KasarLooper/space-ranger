package com.mygdx.game;

import static com.mygdx.game.GameSettings.SPAWN_COOL_DOWN;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {
    private long startTime;

    private long pauseTime;
    private long lastSpawnTime;
    private int core_collected;

    public State state;

    public GameSession() {
        core_collected = 0;
    }

    public void startGame() {
        startTime = TimeUtils.millis();
        lastSpawnTime = TimeUtils.millis();
        state = State.PLAYING;
    }

    public void pauseGame() {
        pauseTime = TimeUtils.millis();
        state = State.PAUSED;
    }

    public void resumeGame() {
        state = State.PLAYING;
        startTime += TimeUtils.millis() - pauseTime;
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
        if (core_collected >= 3) {
            state = State.ENDED;
            return true;
        }
        return false;
    }
}
