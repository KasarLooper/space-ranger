package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.physics.WorldWrap;


public abstract class PhysicsObject extends GameObject {
    public Texture texture;

    protected PhysicsObject(String texturePath, int x, int y, float wight, float height) {
        super(x, y, wight, height);
        this.x = x;
        this.y = y;

        texture = new Texture(texturePath);
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

    public float getRadius() {
        return Math.max(width, height) * SCALE / 2f;
    }

    protected abstract Body createBody(float x, float y, WorldWrap world);
}
