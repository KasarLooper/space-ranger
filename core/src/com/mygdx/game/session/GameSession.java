package com.mygdx.game.session;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.State;

public abstract class GameSession {
    private long startTime;

    protected int coolDown;
    protected long pauseTime;
    protected long lastSpawnTime;

    public State state;

    public GameSession() {
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
        if (TimeUtils.millis() - lastSpawnTime > coolDown) {
            lastSpawnTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    public abstract boolean victory();
}
