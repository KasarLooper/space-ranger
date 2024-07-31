package com.kasarlooper.spaceranger.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static int loadLevel() {
        return preferences.getInteger("i", 1);
    }

    public static void saveLevel(int i) {
        preferences.putInteger("i", i);
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
