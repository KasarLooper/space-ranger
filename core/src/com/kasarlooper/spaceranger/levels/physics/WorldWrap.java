package com.kasarlooper.spaceranger.levels.physics;

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
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.manager.ContactManager;

import java.util.HashSet;
import java.util.Set;

public class WorldWrap {
    final World world;
    private final Set<BodyWrap> bodies;
    private float accumulator;
    private Box2DDebugRenderer renderer;

    public WorldWrap(float gravity, MyGdxGame game) {
        world = new World(new Vector2(0, -gravity), true);
        bodies = new HashSet<>();
        accumulator = 0;
        renderer = new Box2DDebugRenderer(true, false, false, false, false, false);
        new ContactManager(world, game);
    }

    public void update(float delta) {
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            for (BodyWrap wrap : bodies)
                updateCords(wrap.body);

            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    private void updateCords(Body body) {
        if (body.getFixtureList().size == 0) return;
        GameObject object = (GameObject) body.getFixtureList().get(0).getUserData();
        object.setCornerX((int) (body.getPosition().x / SCALE - object.width / 2f));
        object.setCornerY((int) (body.getPosition().y / SCALE - object.height / 2f));
    }

    public Body createBody(BodyDef def) {
        Body body = world.createBody(def);
        BodyWrap bw = new BodyWrap(body, this);
        bodies.add(bw);
        return body;
    }

    void removeBody(BodyWrap wrap) {
        bodies.remove(wrap);
    }

    @SuppressWarnings("NewApi")
    public void destroyAll() {
        for (Object bodyWrap : bodies.toArray())
            ((BodyWrap) bodyWrap).destroy();
        bodies.clear();
    }

    public void render(Matrix4 matrix4) {
        renderer.render(world, matrix4);
    }
}
