package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameResources.ENEMY_SHIP_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.BULLET_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_CHANCE_CHANGE_AIM;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_CHECK_ANGLE;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_CHECK_DISTANCE;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_SHOOT_ANGLE;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_TO_PLAYER_ROTATION_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_USUAL_ROTATION_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.ENEMY_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SPEED_ENEMY;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

import java.util.Random;

public class EnemyObject extends PhysicsObject {
    private final WorldWrap world;
    public Body body;

    public int x, y;
    private long lastShootTime;
    private float aim;
    private boolean hasAim;

    Sprite sprite;
    Random rd;
    public boolean wasHit;

    public EnemyObject(int x, int y, WorldWrap world) {
        super(ENEMY_SHIP_IMG_PATH, x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
        body = createBody(x, y, world);
        this.world = world;
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
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        if (type == Type.Player || type == Type.Enemy || type == Type.Bullet || type == Type.Asteroid) {
            wasHit = true;
        }
    }

    private BulletObject tryShoot() {
        final int padding = 50;
        if (TimeUtils.millis() - lastShootTime > GameSettings.ENEMY_SHOOT_COOL_DOWN) {
            lastShootTime = TimeUtils.millis();
            return new BulletObject((int) (getX() + cos(toRadians(getRotation())) * (getRadius() / 2 + BULLET_HEIGHT + padding)),
                    (int) (getY() + sin(toRadians(getRotation())) * (getRadius() / 2 + BULLET_HEIGHT + padding)),
                    world, getRotation(), true);
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
        } else {
            sprite.setRotation(sprite.getRotation() + ENEMY_USUAL_ROTATION_SPEED);
            hasAim = false;
        }
        sprite.setBounds(getX() - width / 2f, getY() - height / 2f, width, height);
        int dx = (int) (cos(toRadians(getRotation() % 360)) * SPEED_ENEMY);
        int dy = (int) (sin(toRadians(getRotation() % 360)) * SPEED_ENEMY);
        body.setLinearVelocity(dx, dy);
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
