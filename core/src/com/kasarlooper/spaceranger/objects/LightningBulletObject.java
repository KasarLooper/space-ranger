package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.GRAVITY_PLANET_Y;
import static com.kasarlooper.spaceranger.GameSettings.LIGHTING_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;

public class LightningBulletObject extends PhysicsObject{
    private final Type type;
    private boolean hasToBeDestroyed;
    private static long lastShootTime;

    Sprite sprite;
    public LightningBulletObject(SpacemanObject spaceman, World world) {
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
    protected Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef();

        def.type = getBodyType();
        def.fixedRotation = true; // запрет на вращение
        Body body = world.createBody(def);

        Shape shape = getShape(x, y, (float) width,  (float) height);

        FixtureDef fixtureDef = new FixtureDef();
        //fixtureDef.filter.categoryBits = cBits; // биты

        fixtureDef.shape = shape;
        fixtureDef.density = getDensity();
        fixtureDef.friction = getFriction();
        fixtureDef.restitution = getRestitution();
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;
    }

    @Override
    public void draw(SpriteBatch batch) {
        body.applyForceToCenter(0f, (float) (-body.getMass() * GRAVITY_PLANET_Y), false);
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

    public boolean isHasToBeDestroyed() {
        return hasToBeDestroyed;
    }

    @Override
    public void hit(Type type) {
        hasToBeDestroyed = true;
    }

    public Type type() {
        return type;
    }
}
