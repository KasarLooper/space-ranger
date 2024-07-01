package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Type;

public class ShipObject extends GameObject{
    int livesLeft;

    public ShipObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        livesLeft = 3;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void hit(Type type) {
        if (type == Type.Enemy) {
            livesLeft -= 1;
        }
    }

    public Type type() {
        return  Type.Ship;
    }

    public boolean isAlive() {
        return  livesLeft > 0;
    }
}
