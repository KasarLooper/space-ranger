package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.LIGHTNING_LEFT_IMG_PATH;
import static com.kasarlooper.spaceranger.GameResources.LIGHTNING_RIGHT_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.LIGHTING_WIDTH;

import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.BodyWrap;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.levels.rendering.GraphicsRenderer;

public class LightningBulletObject extends GameObject {
    public BodyWrap body;
    private final GObjectType type;
    private boolean hasToBeDestroyed;
    private static long lastShootTime;
    private WorldWrap world;

    public LightningBulletObject(SpacemanObject spaceman, WorldWrap world, GraphicsRenderer gRenderer) {
        super((spaceman.isRightDirection ? spaceman.getCenterX() + (LIGHTING_WIDTH) / 2 + COSMONAUT_WIDTH : spaceman.getCenterX() - (LIGHTING_WIDTH) / 2 - COSMONAUT_WIDTH),
                (spaceman.getCenterY()), LIGHTING_WIDTH, GameSettings.LIGHTING_HEIGHT);
        body = createBody(cornerX, cornerY, world);
        gRenderer.addSprite(this)
                .texture(spaceman.isRightDirection ? LIGHTNING_RIGHT_IMG_PATH : LIGHTNING_LEFT_IMG_PATH)
                .destroy(body::isDestroyed)
                .create();
        this.world = world;
        body.setBullet(true);
        type = GObjectType.Bullet;
        hasToBeDestroyed = false;
        lastShootTime = TimeUtils.millis();
    }

    public static boolean isShootTime() {
        return TimeUtils.millis() - lastShootTime > 1000;
    }

    protected BodyWrap createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .sensor()
                .staticType()
                .createBody(world, this);
    }

    public boolean destroyIfNeed() {
        if (hasToBeDestroyed || TimeUtils.millis() - lastShootTime > 1000) {
            body.destroy();
            return true;
        }
        return false;
    }

    @Override
    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        hasToBeDestroyed = true;
    }

    public GObjectType type() {
        return type;
    }
}
