package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class SpacemanObject extends GameObject{
    Sprite sprite;

    public SpacemanObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        sprite = new Sprite(texture);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - (width / 2f), getY() - (height / 2f), width, height);
        sprite.draw(batch);
    }

    @Override
    public void hit(Type type) {
        // В мире то пока не никого, с кем сталкиваться?
    }

    public Type type() {
        return Type.Ship;
        // сли я правильно понял
    }
}
