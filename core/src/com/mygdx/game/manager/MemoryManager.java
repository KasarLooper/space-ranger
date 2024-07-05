package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static boolean loadIsNextLevel() {
        return preferences.getBoolean("isNextLevel", false);
    }

    public static void saveIsNextLevel(boolean isNextLevel) {
        preferences.putBoolean("isNextLevel", isNextLevel);
        preferences.flush();
    }
}
