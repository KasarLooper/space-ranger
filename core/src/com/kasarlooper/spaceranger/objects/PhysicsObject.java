package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;


public abstract class PhysicsObject extends GameObject {
    public Body body;
    public Texture texture;
    World world;


    PhysicsObject(String texturePath, int x, int y, float wight, float height, World world) {
        super(x, y, wight, height);
        this.x = x;
        this.y = y;

        texture = new Texture(texturePath);
        body = createBody(x, y, world);
        this.world = world;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture,
                getX() - (width / 2f),
                getY() - (height / 2f),
                width,
                height);
    }

    @Override
    public Type type() {
        return Type.Block;
    }

    public void dispose() {
        texture.dispose();
    }

    public float getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public float getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(float x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(float y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    public float getRadius() {
        return Math.max(width, height) * SCALE / 2f;
    }


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

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;
    }

    protected Shape getShape(float x, float y, float width, float height) {
        CircleShape shape = new CircleShape();
        shape.setRadius(getRadius());
        return shape;
    }

    protected BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.DynamicBody;
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
}
