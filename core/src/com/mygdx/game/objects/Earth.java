package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Earth extends GameObject{
    Sprite sprite;

    public Earth(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        sprite = new Sprite(texture);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
