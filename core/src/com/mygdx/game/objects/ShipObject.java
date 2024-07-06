package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.COUNT_FRAMES_ONE_IMG;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static com.mygdx.game.GameSettings.SPEED_SHIP;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class ShipObject extends GameObject{
    public int livesLeft;
    Sprite sprite;
    Random random = new Random();

    public long lastShotTime;

    public float shot_cool_down;
    private float degrees;
    int i = 0;

    public ShipObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        livesLeft = 3;
        body.setLinearDamping(10);
        sprite = new Sprite(texture);
        sprite.setOrigin(wight / 2f, height / 2f);
        shot_cool_down = GameSettings.SHOOTING_COOL_DOWN;
    }

    public Vector2 move() {
        int dx = (int) (cos(toRadians(degrees + 90)) * SPEED_SHIP);
        int dy = (int) (sin(toRadians(degrees + 90)) * SPEED_SHIP);
        setX(getX() + dx);
        setY(getY() + dy);
        return new Vector2(dx, dy);
    }

    public void moleHoleAnim() {
        i += 1;
    }

    public boolean isEnd() {
        return i >= 19 * COUNT_FRAMES_ONE_IMG;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - width / 2f, getY() - height / 2f, width, height);
        if (i > 0 && i < 19 * COUNT_FRAMES_ONE_IMG) sprite.setTexture(new Texture(String.format(GameResources.ANIM_SHIP_PORTAL_IMG_PATH_PATTERN,
                i / COUNT_FRAMES_ONE_IMG + 1)));
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
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        if (type == Type.Enemy || type == Type.EnemyBullet || type == Type.Bullet) {
            livesLeft -= 1;
        }
        if (livesLeft > 0) sprite.setTexture(new Texture(String.format(GameResources.SHIP_IMG_PATH, livesLeft)));
    }

    public float getRotation() {
        return sprite.getRotation() + 90;
    }

    public void setRotation(float degrees) {
        this.degrees = degrees;
        sprite.setRotation(degrees);
    }

    public Type type() {
        return Type.Ship;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public int getLivesLeft() {
        return livesLeft;
    }
}
