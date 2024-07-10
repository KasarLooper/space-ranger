package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;

public class LightningBulletObject extends PhysicsObject{
    private final Type type;
    private long lastLightningSpawnTime;

    Sprite sprite;
    public LightningBulletObject(int wight, int height,SpacemanObject spaceman, World world) {
        super(spaceman.isRightStep ? GameResources.LIGHTNING_RIGHT_IMG_PATH : GameResources.LIGHTNING_LEFT_IMG_PATH, spaceman.getX(), spaceman.getY(), wight, height, world);
        body.setBullet(true);
        sprite = new Sprite(texture);
        type = Type.Bullet;

        lastLightningSpawnTime = TimeUtils.millis();
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }


    public boolean destroy() {
        if (TimeUtils.millis() - lastLightningSpawnTime > 500) {
            lastLightningSpawnTime = TimeUtils.millis();
            return true;
        }
        return false;
    }


    public Type type() {
        return type;
    }
}
