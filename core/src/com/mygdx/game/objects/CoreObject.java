package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class CoreObject extends GameObject {

    public int x, y;

    public boolean wasHit;

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
    public void hit(Type type) {
        if (type == Type.Ship || type == Type.Bullet) {
            wasHit = true;
        }
    }

    public boolean destroy() {
        return wasHit;
    }

    public Type type() {
        return Type.Core;
    }
}
