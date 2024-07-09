package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.BULLET_HEIGHT;
import static com.mygdx.game.GameSettings.ENEMY_CHANCE_CHANGE_AIM;
import static com.mygdx.game.GameSettings.ENEMY_CHECK_ANGLE;
import static com.mygdx.game.GameSettings.ENEMY_CHECK_DISTANCE;
import static com.mygdx.game.GameSettings.ENEMY_SHOOT_ANGLE;
import static com.mygdx.game.GameSettings.ENEMY_TO_PLAYER_ROTATION_SPEED;
import static com.mygdx.game.GameSettings.ENEMY_USUAL_ROTATION_SPEED;
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

import java.util.Random;

public class EnemyObject extends PhysicsObject {
    public int x, y;
    private long lastShootTime;
    private float aim;
    private boolean hasAim;

    Sprite sprite;
    Random rd;
    public boolean wasHit;

    public EnemyObject(int x, int y, int width, int height, World world, String texturePath) {
        super(texturePath, x, y, width, height, world);
        this.x = x;
        this.y = y;
        rd = new Random();
        wasHit = false;
        sprite = new Sprite(texture);
        sprite.setBounds(x, y, width, height);
        sprite.setOrigin(width / 2f, height / 2f);
        hasAim = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void hit(Type type) {
        if (type == Type.Player || type == Type.Enemy || type == Type.Bullet) {
            wasHit = true;
        }
    }

    private BulletObject tryShoot() {
        final int padding = 50;
        if (TimeUtils.millis() - lastShootTime > GameSettings.ENEMY_SHOOT_COOL_DOWN) {
            lastShootTime = TimeUtils.millis();
            return new BulletObject((int) (getX() + width / 2 + cos(toRadians(getRotation())) * (getRadius() / 2 + BULLET_HEIGHT + padding)),
                    (int) (getY() + height / 2 + sin(toRadians(getRotation())) * (getRadius() / 2 + BULLET_HEIGHT + padding)),
                    GameSettings.BULLET_WIDTH, BULLET_HEIGHT,
                    GameResources.ENEMY_BULLET_IMG_PATH, world,
                    getRotation(), GameSettings.Bullet_Speed, true);
        }
        return null;
    }

    public BulletObject move(float playerX, float playerY) {
        float playerAngle = getAngleOfPlayer(playerX, playerY);
        float playerDistance = getPlayerDistance(playerX, playerY);
        if (Math.abs(playerAngle) < ENEMY_CHECK_ANGLE && playerDistance < ENEMY_CHECK_DISTANCE) {
            if (!hasAim /*|| rd.nextInt(100) < ENEMY_CHANCE_CHANGE_AIM*/) {
                aim = -playerAngle - rd.nextInt(ENEMY_SHOOT_ANGLE) * 2 + ENEMY_CHANCE_CHANGE_AIM;
            }
            if (!hasAim) hasAim = true;
            float da = Math.min(ENEMY_TO_PLAYER_ROTATION_SPEED, Math.abs(aim));
            if (aim < 0) da = -da;
            sprite.setRotation(sprite.getRotation() + da);
            aim -= da;
            /*
            if (Math.abs(aim) <= ENEMY_TO_PLAYER_ROTATION_SPEED) sprite.setRotation(sprite.getRotation() + aim);
            else {
                float da = (aim > 0 ? 1 : -1) * ENEMY_TO_PLAYER_ROTATION_SPEED;
                aim -= da;
                sprite.setRotation(sprite.getRotation() + da);
            }
             */
        } else {
            sprite.setRotation(sprite.getRotation() + ENEMY_USUAL_ROTATION_SPEED);
            hasAim = false;
        }
        sprite.setBounds(getX(), getY(), width, height);
        int dx = (int) (cos(toRadians(getRotation())) * SPEED_ENEMY);
        int dy = (int) (sin(toRadians(getRotation())) * SPEED_ENEMY);
        setX(getX() + dx);
        setY(getY() + dy);
        if (playerDistance < GameSettings.ENEMY_SHOOT_DISTANCE) return tryShoot();
        else return null;
    }

    private float getAngleOfPlayer(float playerX, float playerY) {
        double[] A = {playerX - getX(), playerY - getY()};
        double[] B = {cos(toRadians(getRotation())), sin(toRadians(getRotation()))};
        double dotProduct = A[0] * B[0] + A[1] * B[1];
        double crossProduct = A[0] * B[1] - A[1] * B[0];
        double angle = Math.atan2(crossProduct, dotProduct);
        return (float) Math.toDegrees(angle);
    }

    private float getPlayerDistance(float playerX, float playerY) {
        float dx = playerX - getX();
        float dy = playerY - getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
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
