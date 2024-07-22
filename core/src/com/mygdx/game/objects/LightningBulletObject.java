package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.COSMONAUT_WIDTH;
import static com.mygdx.game.GameSettings.GRAVITY_PLANET_Y;
import static com.mygdx.game.GameSettings.LIGHTING_WIDTH;
import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class LightningBulletObject extends PhysicsObject{
    private final Type type;
    private long lastLightningSpawnTime;
    private boolean hasToBeDestroyed;

    Sprite sprite;
    public LightningBulletObject(SpacemanObject spaceman, World world) {
        super(spaceman.isRightDirection ? GameResources.LIGHTNING_RIGHT_IMG_PATH : GameResources.LIGHTNING_LEFT_IMG_PATH,
                spaceman.isRightDirection ? spaceman.getX() + (LIGHTING_WIDTH) / 2 + COSMONAUT_WIDTH : spaceman.getX() - (LIGHTING_WIDTH) / 2 - COSMONAUT_WIDTH,
                spaceman.getY(), LIGHTING_WIDTH, GameSettings.LIGHTING_HEIGHT, world);
        body.setBullet(true);
        sprite = new Sprite(texture);
        type = Type.Bullet;
        body.setType(BodyDef.BodyType.DynamicBody);
        lastLightningSpawnTime = TimeUtils.millis();
        hasToBeDestroyed = false;
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
        sprite.setBounds(getX() - width / 2f, getY() - height / 2f, width, height);
        sprite.draw(batch);
    }


    public boolean destroy() {
        if (TimeUtils.millis() - lastLightningSpawnTime > 1000 || hasToBeDestroyed) {
            lastLightningSpawnTime = TimeUtils.millis();
            world.destroyBody(body);
            return true;
        }
        return false;
    }

    @Override
    public void hit(Type type) {
        hasToBeDestroyed = true;
    }

    public Type type() {
        return type;
    }
}
