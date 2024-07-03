package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Type;

public class EnemyBulletObject extends GameObject{

    Sprite sprite;

    private boolean wasHit;
    EnemyBulletObject(int x, int y, int wight, int height, World world, String texturePath) {
        super(texturePath, x, y, wight, height, world);
        body.setBullet(true);
        sprite = new Sprite(texture);
        wasHit = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - width / 2f, getY() - height / 2f, width, height);
        sprite.draw(batch);
    }

    @Override
    public void hit(Type type) {
        wasHit = true;
    }

    public boolean destroy(int centreX, int centreY) {
        return wasHit || !(getX() > centreX - (width + SCREEN_WIDTH) / 2 &&
                getX() < centreX + (width + SCREEN_WIDTH) / 2 &&
                getY() > centreY - (height + SCREEN_HEIGHT) / 2 &&
                getY() < centreY + (height + SCREEN_HEIGHT) / 2);
    }
}
