package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.BULLET_HEIGHT;
import static com.mygdx.game.GameSettings.SPEED_ENEMY;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class EnemyObject extends GameObject{
    public int x, y;
    private long lastShootTime;

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
        if (type == Type.Ship || type == Type.Enemy) {
            wasHit = true;
        }
    }

    private BulletObject tryShoot() {
        final int padding = 50;
        if (TimeUtils.millis() - lastShootTime > GameSettings.ENEMY_SHOOT_COOL_DOWN) {
            lastShootTime = TimeUtils.millis();
            return new BulletObject((int) (getX() + width / 2 + cos(toRadians(getRotation())) * (getRadius() / 2 + BULLET_HEIGHT + padding)),
                    (int) (getY() + height / 2 + sin(toRadians(getRotation())) * (getRadius() / 2 + BULLET_HEIGHT + padding)),
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                    GameResources.BULLET_IMG_PATH, world,
                    getRotation(), GameSettings.Bullet_Speed);
        }
        return null;
    }

    public BulletObject move() {
        sprite.setRotation(sprite.getRotation() + 0.5f);
        sprite.setBounds(getX(), getY(), width, height);
        int dx = (int) (cos(toRadians(getRotation())) * SPEED_ENEMY);
        int dy = (int) (sin(toRadians(getRotation())) * SPEED_ENEMY);
        setX(getX() + dx);
        setY(getY() + dy);
        System.out.printf("%d %d\n", getX(), getY());
        if (true) return tryShoot();
        else return null;
    }

    public float getRotation() {
        return sprite.getRotation() - 90;
    }

    public boolean destroy() {
        return wasHit;
    }

    public Type type() {
        return Type.Enemy;
    }

}
