package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Type;

public class ShipObject extends GameObject{
    int livesLeft;
    static Sprite sprite;

    public long lastShotTime;

    public float shot_cool_down;

    public ShipObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        livesLeft = 3;
        sprite = new Sprite(texture);
        shot_cool_down = GameSettings.SHOOTING_COOL_DOWN;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - width / 2f, getY() - height / 2f, width, height);
        sprite.draw(batch);
    }

    public boolean needToShoot() {
        if (TimeUtils.millis() - lastShotTime >= shot_cool_down) {
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    @Override
    public void hit(Type type) {
        if (type == Type.Enemy) {
            livesLeft -= 1;
        }
    }

    public static float getRotation() {
        return sprite.getRotation();
    }

    public void setRotation(float degrees) {
        sprite.setRotation(degrees);
    }

    public Type type() {
        return  Type.Ship;
    }

    @Override
    public void draw() {

    }

    public boolean isAlive() {
        return  livesLeft > 0;
    }
}
