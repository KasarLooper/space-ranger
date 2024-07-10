package com.mygdx.game.session;

import static com.mygdx.game.GameSettings.PLANET_SPAWN_COOL_DOWN;
import static com.mygdx.game.GameSettings.SPACE_SPAWN_COOL_DOWN;

import com.badlogic.gdx.utils.TimeUtils;

public class PlanetGameSession extends GameSession {
    private long lastLightningSpawnTime;

    @Override
    public void startGame() {
        super.startGame();
        coolDown = PLANET_SPAWN_COOL_DOWN;
    }

    @Override
    public boolean victory() {
        return false;
    }

    public boolean shouldSpawnLighting() {
        if (TimeUtils.millis() - lastLightningSpawnTime > (SPACE_SPAWN_COOL_DOWN + 500)) {
            lastLightningSpawnTime = TimeUtils.millis();
            return true;
        }
        return false;
    }
}
