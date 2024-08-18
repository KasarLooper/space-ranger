package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public abstract class PhysicsObject extends GameObject {
    public Body body;
    public Texture texture;
    World world;


    PhysicsObject(String texturePath, int x, int y, int width, int height, World world) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;

        texture = new Texture(texturePath);
        body = createBody(x, y, world);
        this.world = world;
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

    public float getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public float getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(float x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(float y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    protected abstract Body createBody(float x, float y, World world);
}
