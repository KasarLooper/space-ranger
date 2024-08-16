package com.kasarlooper.spaceranger.objects;

import com.badlogic.gdx.physics.box2d.World;

public class ResourceObject extends PhysicsObject {
    Type type;
    boolean wasHit;

    public ResourceObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        type = Type.Resource;
        wasHit = false;
    }

    @Override
    protected float getFriction() {
        return 1;
    }

    @Override
    public void hit(Type type) {
        if (type == Type.Player) {
            wasHit = true;
        }
    }

    public Type type() {
        return type;
    }

    public boolean destroy() {
        return wasHit;
    }
}
