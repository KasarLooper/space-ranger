package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class EnemyObject extends GameObject{

    public int x, y;

    Sprite sprite;

    public boolean wasHit;

    public EnemyObject(int x, int y, int width, int height, World world, String texturePath) {
        super(texturePath, x, y, width, height, world);
        this.x = x;
        this.y = y;
        wasHit = false;
        sprite = new Sprite(texture);
        sprite.setBounds(x, y, width, height);
        sprite.setOrigin(width / 2f, height / 2f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void hit(Type type) {
        if (type == Type.Ship || type == Type.Bullet) {
            wasHit = true;
        }
    }

    // Типо они крутятся :)
    public void move() {
        sprite.setRotation(sprite.getRotation() + 1);
    }



    public boolean destroy() {
        return wasHit;
    }

    public Type type() {
        return Type.Enemy;
    }

}
