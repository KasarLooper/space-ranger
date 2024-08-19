package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.LIGHTING_WIDTH;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

public class LightningBulletObject extends PhysicsObject {
    private final Type type;
    private boolean hasToBeDestroyed;
    private static long lastShootTime;

    Sprite sprite;

    public LightningBulletObject(SpacemanObject spaceman, WorldWrap world) {
        super(spaceman.isRightDirection ? GameResources.LIGHTNING_RIGHT_IMG_PATH : GameResources.LIGHTNING_LEFT_IMG_PATH,
                (int) (spaceman.isRightDirection ? spaceman.getX() + (LIGHTING_WIDTH) / 2 + COSMONAUT_WIDTH : spaceman.getX() - (LIGHTING_WIDTH) / 2 - COSMONAUT_WIDTH),
                (int) (spaceman.getY()), LIGHTING_WIDTH, GameSettings.LIGHTING_HEIGHT, world);
        body.setBullet(true);
        sprite = new Sprite(texture);
        type = Type.Bullet;
        body.setType(BodyDef.BodyType.DynamicBody);
        hasToBeDestroyed = false;
        lastShootTime = TimeUtils.millis();
    }

    public static boolean isShootTime() {
        return TimeUtils.millis() - lastShootTime > 1000;
    }

    @Override
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .sensor()
                .staticType()
                .createBody(world, this);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(Math.round(getX() - width / 2f), Math.round(getY() - height / 2f), width, height);
        sprite.draw(batch);
    }


    public boolean destroyIfNeed() {
        if (hasToBeDestroyed || TimeUtils.millis() - lastShootTime > 1000) {
            world.destroyBody(body);
            return true;
        }
        return false;
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        hasToBeDestroyed = true;
    }

    public Type type() {
        return type;
    }
}