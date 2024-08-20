package com.kasarlooper.spaceranger.levels.physics;

import static com.kasarlooper.spaceranger.GameSettings.EARTH_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.GROUND_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;

public class BodyBuilder {
    public interface ShapeBuilder {
        Shape getShape(float width, float height);
    }

    public static final ShapeBuilder CIRCLE = (width, height) -> {
        CircleShape shape = new CircleShape();
        shape.setRadius(Math.max(width, height) / 2f);
        return shape;
    };
    public static final ShapeBuilder RECTANGLE = (width, height) -> {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);
        return shape;
    };
    public static final ShapeBuilder GROUND = (width, height) -> {
        EdgeShape shape = new EdgeShape();
        shape.set(new Vector2(-1000, GROUND_HEIGHT * SCALE + EARTH_HEIGHT * SCALE / 2f - 1),
                new Vector2(1000, GROUND_HEIGHT * SCALE + EARTH_HEIGHT * SCALE / 2f - 1));
        return shape;
    };

    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private Vector2 cords, size;
    private ShapeBuilder shapeBuilder;

    private BodyBuilder() {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        shapeBuilder = CIRCLE;

        fixtureDef = new FixtureDef();
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
    }

    public static BodyBuilder init() {
        return new BodyBuilder();
    }

    // Required
    public BodyBuilder cords(float x, float y) {
        this.cords = new Vector2(x * SCALE, y * SCALE);
        return this;
    }

    // Required
    public BodyBuilder size(float x, float y) {
        this.size = new Vector2(x * SCALE, y * SCALE);
        return this;
    }

    // Default: dynamic
    public BodyBuilder staticType() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return this;
    }

    public BodyBuilder enableRotation() {
        bodyDef.fixedRotation = false;
        return this;
    }

    // Default: circle
    public BodyBuilder shape(ShapeBuilder builder) {
        shapeBuilder = builder;
        return this;
    }

    public BodyBuilder friction() {
        fixtureDef.friction = 1f;
        return this;
    }

    public BodyBuilder sensor() {
        fixtureDef.isSensor = true;
        return this;
    }

    public BodyWrap createBody(WorldWrap world, GameObject object) {
        if (cords == null) throw new RuntimeException("No cords specified");
        if (size == null) throw new RuntimeException("No size specified");

        Shape shape = shapeBuilder.getShape(size.x, size.y);
        fixtureDef.shape = shape;

        Body body = world.createBody(bodyDef, fixtureDef.isSensor);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(object);

        shape.dispose();
        if (!shapeBuilder.equals(GROUND)) body.setTransform(cords.x, cords.y, 0);
        return new BodyWrap(body, world);
    }
}
