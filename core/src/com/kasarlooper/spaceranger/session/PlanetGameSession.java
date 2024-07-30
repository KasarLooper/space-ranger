package com.kasarlooper.spaceranger.session;

import static com.kasarlooper.spaceranger.GameSettings.LIGHTNING_COOL_DOWN;
import static com.kasarlooper.spaceranger.GameSettings.PLANET_SPAWN_COOL_DOWN;

import com.badlogic.gdx.utils.TimeUtils;

public class PlanetGameSession extends GameSession {
    private long lastLightningSpawnTime;
    private long lastAlienSpawnTime;
    private boolean isVictory;

    @Override
    public void startGame() {
        super.startGame();
        coolDown = PLANET_SPAWN_COOL_DOWN;
        isVictory = false;
    }

    public void setVictory() {
        isVictory = true;
    }

    @Override
    public boolean victory() {
        return isVictory;
    }

    public boolean shouldSpawnLighting() {
        if (TimeUtils.millis() - lastLightningSpawnTime > (LIGHTNING_COOL_DOWN)) {
            lastLightningSpawnTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    public boolean shouldSpawnCore() {
        if (TimeUtils.millis() - lastAlienSpawnTime > (coolDown)) {
            lastAlienSpawnTime = TimeUtils.millis();
            return true;
        }
        return false;
    }
}
