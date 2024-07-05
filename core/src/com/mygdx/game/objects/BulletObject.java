package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BulletObject extends GameObject{
    private Type type;
    public boolean wasHit;

    Sprite sprite;
    public BulletObject(int x, int y, int wight, int height, String texturePath, World world, float degrees, int speed, boolean isEnemy) {
        super(texturePath, x, y, wight, height, world);
        body.setLinearVelocity(new Vector2((float) (cos(toRadians(degrees)) * speed), (float) (sin(toRadians(degrees)) * speed)));
        body.setBullet(true);
        sprite = new Sprite(texture);
        sprite.setRotation(degrees + 270);
        wasHit = false;
        if (isEnemy) type = Type.EnemyBullet;
        else type = Type.Bullet;
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
    public Type type() {
        return type;
    }
}
