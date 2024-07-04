package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Type;

public class EnemyObject extends GameObject{

    public int x, y;

    Sprite sprite;

    public boolean wasHit;

    public EnemyObject(int x, int y, int wight, int height, World world, String texturePath) {
        super(texturePath, x, y, wight, height, world);
        this.x = x;
        this.y = y;
        wasHit = false;
        sprite = new Sprite(texture);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void hit(Type type) {
        if (type == Type.Ship || type == Type.Enemy) {
            wasHit = true;
        }
    }

    // Типо они крутятся :)
    public void move() {
        sprite.setRotation(sprite.getRotation() + 20);
    }



    public boolean destroy() {
        return wasHit;
    }

    public Type type() {
        return Type.Enemy;
    }

}
