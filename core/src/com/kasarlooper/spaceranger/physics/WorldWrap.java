package com.kasarlooper.spaceranger.physics;

import static com.kasarlooper.spaceranger.GameSettings.POSITION_ITERATIONS;
import static com.kasarlooper.spaceranger.GameSettings.SCALE;
import static com.kasarlooper.spaceranger.GameSettings.STEP_TIME;
import static com.kasarlooper.spaceranger.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.manager.ContactManager;
import com.kasarlooper.spaceranger.objects.GameObject;

import java.util.HashSet;
import java.util.Set;

public class WorldWrap {
    final World world;
    private final Set<Body> bodies;
    private final Set<Body> noGravity;
    private float accumulator;
    private Box2DDebugRenderer renderer;

    public WorldWrap(float gravity, MyGdxGame game) {
        world = new World(new Vector2(0, -gravity), true);
        noGravity = new HashSet<>();
        bodies = new HashSet<>();
        accumulator = 0;
        renderer = new Box2DDebugRenderer(true, false, false, false, false, false);
        new ContactManager(world, game);
    }

    public void update(float delta) {
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            for (Body body : noGravity)
                body.applyForceToCenter(world.getGravity().scl(-body.getMass()), true);

            for (Body body : bodies)
                updateCords(body);

            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    private void updateCords(Body body) {
        GameObject object = (GameObject) body.getFixtureList().get(0).getUserData();
        object.setX((int) (body.getPosition().x / SCALE));
        object.setY((int) (body.getPosition().y / SCALE));
    }

    public Body createBody(BodyDef def, boolean isNoGravity) {
        Body body = world.createBody(def);
        bodies.add(body);
        if (isNoGravity) noGravity.add(body);
        return body;
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
        bodies.remove(body);
        noGravity.remove(body);
    }

    public void destroyAll() {
        for (Body body : bodies) world.destroyBody(body);
        bodies.clear();
        noGravity.clear();
    }

    public void render(Matrix4 matrix4) {
        renderer.render(world, matrix4);
    }


}
