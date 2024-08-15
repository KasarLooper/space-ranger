package com.kasarlooper.spaceranger.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.kasarlooper.spaceranger.MyGdxGame;

public class CoreObject extends PhysicsObject {

    public int x, y;

    public boolean wasHit;
    public boolean wasCollected;

    public CoreObject(int x, int y, int wight, int height, World world, String texturePath) {
        super(texturePath, x, y, wight, height, world);
        this.x = x;
        this.y = y;
        wasHit = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        wasHit = true;
        if (type == Type.Player) {
            wasCollected = true;
        }
    }

    public boolean destroy() {
        return wasHit;
    }

    public Type type() {
        return Type.Core;
    }
}
