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

    public static void saveIsFinal(boolean isFinal) {
        preferences.putBoolean("isFinal", isFinal);
        preferences.flush();
    }

    public static boolean loadIsFinal() {
        return preferences.getBoolean("isFinal", false);
    }

    public static boolean lodeNeedToSpaceIntro() {
        return preferences.getBoolean("hasSeenSpaceIntro", false);
    }

    public static void saveNeedToSpaceIntro(boolean hasSeen) {
        preferences.putBoolean("hasSeenSpaceIntro", hasSeen);
        preferences.flush();
    }

    public static boolean loadNeedToPlanetIntro() {
        return preferences.getBoolean("hasSeenPlanetIntro", false);
    }

    public static void saveNeedToPlanetIntro(boolean hasSeen) {
        preferences.putBoolean("hasSeenPlanetIntro", hasSeen);
        preferences.flush();
    }

    public static void clear() {
        saveIsNextLevel(false);
        saveNeedToSpaceIntro(false);
        saveNeedToPlanetIntro(false);
    }
}
