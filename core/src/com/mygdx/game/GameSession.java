package com.mygdx.game;

public class GameSession {

    private int core_collected;

    public GameSession() {
        core_collected = 0;
    }

    public void core_was_collected() {
        core_collected += 1;
    }

    public boolean victory() {
        return core_collected >= 3;
    }
}
