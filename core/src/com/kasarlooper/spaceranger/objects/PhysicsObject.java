package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.physics.WorldWrap;


public abstract class PhysicsObject extends GameObject {
    protected PhysicsObject(int x, int y, float wight, float height) {
        super(x, y, wight, height);
    }

    @Override
    public Type type() {
        return Type.Block;
    }

    public float getRadius() {
        return Math.max(width, height) * SCALE / 2f;
    }

    protected abstract Body createBody(float x, float y, WorldWrap world);
}
