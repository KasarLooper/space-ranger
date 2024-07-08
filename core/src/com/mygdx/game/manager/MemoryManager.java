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

    public static boolean lodeHasSeenSpaceIntro() {
        return preferences.getBoolean("hasSeenSpaceIntro", false);
    }

    public static void saveHasSeenSpaceIntro(boolean hasSeen) {
        preferences.putBoolean("hasSeenSpaceIntro", hasSeen);
        preferences.flush();
    }

    public static boolean loadHasSeenPlanetIntro() {
        return preferences.getBoolean("hasSeenPlanetIntro", false);
    }

    public static void saveHasSeenPlanetIntro(boolean hasSeen) {
        preferences.putBoolean("hasSeenPlanetIntro", hasSeen);
        preferences.flush();
    }

    public static void clear() {
        saveIsNextLevel(false);
        saveHasSeenSpaceIntro(false);
        saveHasSeenPlanetIntro(false);
    }
}
