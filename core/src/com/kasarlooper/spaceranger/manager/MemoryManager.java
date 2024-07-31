package com.kasarlooper.spaceranger.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static boolean loadIsFirstLevel() {
        return preferences.getBoolean("isFirstLevel", true);
    }

    public static void saveIsFirstLevel(boolean isNextLevel) {
        preferences.putBoolean("isFirstLevel", isNextLevel);
        preferences.flush();
    }


    public static boolean loadIsPassedSpace() {
        return preferences.getBoolean("isPassedSpace", false);
    }

    public static void saveIsPassedSpace(boolean isPassedSpace) {
        preferences.putBoolean("isPassedSpace", isPassedSpace);
        preferences.flush();
    }

    public static boolean loadIsPassedPlanet() {
        return preferences.getBoolean("isPassedPlanet", false);
    }

    public static void saveIsPassedPlanet(boolean isPassedPlanet) {
        preferences.putBoolean("isPassedPlanet", isPassedPlanet);
        preferences.flush();
    }

    public static void clear() {
        Gdx.app.getPreferences("User saves").clear();
        preferences.flush();
    }
}
