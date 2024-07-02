package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.Bullet_Speed;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Type;
import com.mygdx.game.components.JoystickView;

public class BulletObject extends GameObject{

    public boolean wasHit;

    Sprite sprite;
    public BulletObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        body.setLinearVelocity(new Vector2((float) (cos(JoystickView.getDegrees()) * Bullet_Speed), (float) (sin(JoystickView.getDegrees()) * Bullet_Speed)));
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
    public void draw() {

    }

    public void setRotation(float degrees) {
        sprite.setRotation(degrees);
    }


    @Override
    public void hit(Type type) {
        wasHit = true;
    }

    public boolean Destroy() {
        return wasHit || !((getX() - width / 2 > 0 && getX() - width / 2 < SCREEN_WIDTH) && (getY() - height / 2 > 0 && getY() - height / 2 < SCREEN_HEIGHT));
    }
}
