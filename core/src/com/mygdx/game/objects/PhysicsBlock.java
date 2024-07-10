package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsBlock extends Block implements Drawable {
    public int width;
    public int height;

    public Body body;
    public Texture texture;
    World world;


    public PhysicsBlock(int x, int y, int wight, int height, String texturePath, World world) {
        super(x, y);
        this.width = wight;
        this.height = height;
        this.x = x;
        this.y = y;

        texture = new Texture(texturePath);
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

    static int cnt = 0;

    @Override
    public void hit(Type type) {
        if (type == Type.Player) System.out.println(getX() + " " + getY());
    }

    @Override
    public Type type() {
        return Type.Block;
    }

    protected Shape getShape(float x, float y, float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width) * SCALE / 2f, (height) * SCALE / 2f);

        return shape;
    }

    protected BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.StaticBody;
    }

    protected float getFriction() {
        return 0;
    }

    protected float getRestitution() {
        return 0f;
    }

    protected float getDensity() {
        return 0.1f;
    }
}
