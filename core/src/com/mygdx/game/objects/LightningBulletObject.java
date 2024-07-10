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
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;

public class LightningBulletObject extends PhysicsObject{
    private final Type type;
    private long lastLightningSpawnTime;

    Sprite sprite;
    public LightningBulletObject(SpacemanObject spaceman, World world) {
        super(spaceman.isRightStep ? GameResources.LIGHTNING_RIGHT_IMG_PATH : GameResources.LIGHTNING_LEFT_IMG_PATH, spaceman.isRightStep ? spaceman.getX() + spaceman.height : spaceman.getX() - spaceman.height, spaceman.getY(), GameSettings.LIGHTING_WIDTH, GameSettings.LIGHTING_HEIGHT, world);
        body.setBullet(true);
        sprite = new Sprite(texture);
        type = Type.Bullet;
        body.setType(BodyDef.BodyType.DynamicBody);
        lastLightningSpawnTime = TimeUtils.millis();
    }


    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - width / 2f, getY() - height / 2f, width, height);
        body.setLinearVelocity(body.getLinearVelocity().x, 0);
        sprite.draw(batch);
    }


    public boolean destroy() {
        if (TimeUtils.millis() - lastLightningSpawnTime > 1000) {
            lastLightningSpawnTime = TimeUtils.millis();
            return true;
        }
        return false;
    }


    public Type type() {
        return type;
    }
}
