package com.kasarlooper.spaceranger.levels;

import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.planet.PlanetGameScreen;
import com.kasarlooper.spaceranger.levels.space.SpaceGameScreen;
import com.kasarlooper.spaceranger.screens.GameScreen;

public class LevelManager {
    public static GameScreen getSpaceLevel(MyGdxGame game) {
        return new SpaceGameScreen(game);
    }

    public static GameScreen getPlanetLevel(MyGdxGame game) {
        return new PlanetGameScreen(game);
    }
}
