package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class LightningBulletObject extends GameObject implements Drawable{
    public int width;
    public int height;

    public Body body;
    public Texture texture;
    World world;
    SpacemanObject spaceman;

    public LightningBulletObject(SpacemanObject spaceman, int wight, int height, World world) {
        this.width = wight;
        this.height = height;
        this.x = spaceman.getX();
        this.y = spaceman.getY();
        this.spaceman = spaceman;

        texture = new Texture("lightning_right.png");
        body = createBody(x, y, world);
        this.world = world;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,
                getX() - (width / 2f),
                getY() - (height / 2f),
                width,
                height);
    }

    public Type type() {
        return Type.Bullet;
    }

    public void dispose() {
        texture.dispose();
    }

    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    private Body createBody(float x, float y, World world) {
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

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;
    }

    protected Shape getShape(float x, float y, float width, float height) {
        //        shape.setRadius(getRadius());
        return new CircleShape();
    }

    protected BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.KinematicBody;
    }

    protected float getRestitution() {
        return 0f;
    }

    protected float getDensity() {
        return 0.1f;
    }

    protected float getFriction() {
        return 1f;
    }

    public void setCondition() {
        x = spaceman.getX();
        y = spaceman.getY();
        if (spaceman.isRightStep) {
            texture = new Texture("lightning_right.png");
        } else {
            texture = new Texture("lightning_left.png");
        }
    }

}