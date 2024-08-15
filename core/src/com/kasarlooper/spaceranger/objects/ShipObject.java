package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.COUNT_FRAMES_ONE_IMG;
import static com.kasarlooper.spaceranger.GameSettings.SPEED_SHIP;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;

public class ShipObject extends PhysicsObject {
    public int livesLeft;
    Sprite sprite;

    public long lastShotTime;

    public float shot_cool_down;
    private float degrees;
    int i = 0;
    private boolean isStop = false;

    public ShipObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        body.setLinearDamping(10f);
        livesLeft = 3;
        body.setLinearDamping(10);
        sprite = new Sprite(texture);
        sprite.setOrigin(wight / 2f, height / 2f);
        shot_cool_down = GameSettings.SHOOTING_COOL_DOWN;
    }

    public void move() {
        if (isStop) {
            return;
        }
        int dx = (int) (cos(toRadians(degrees + 90)) * SPEED_SHIP);
        int dy = (int) (sin(toRadians(degrees + 90)) * SPEED_SHIP);
        body.setLinearVelocity(dx, dy);
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
    public void hit(Type type, MyGdxGame myGdxGame) {
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        if (type == Type.Enemy || type == Type.EnemyBullet || type == Type.Bullet) {
            livesLeft -= 1;
        }
        if (type == Type.Asteroid) livesLeft = 0;
        if (livesLeft > 0) sprite.setTexture(new Texture(String.format(GameResources.SHIP_IMG_PATH, livesLeft)));
    }

    public float getRotation() {
        return sprite.getRotation() + 90;
    }

    public void setRotation(float degrees) {
        this.degrees = degrees;
        sprite.setRotation(degrees);
        isStop = false;
    }

    public void stop() {
        isStop = true;
    }

    public Type type() {
        return Type.Player;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public int getLivesLeft() {
        return livesLeft;
    }
}
