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

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.drawing.GraphicsRenderer;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;

import java.util.Random;

public class EnemyObject extends GameObject {
    private final WorldWrap world;
    public Body body;

    private long lastShootTime;
    private float aim;
    private boolean hasAim;
    private float rotation;

    Random rd;
    public boolean wasHit;

    public EnemyObject(int x, int y, WorldWrap world, GraphicsRenderer gRenderer) {
        super(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
        body = createBody(x, y, world);
        this.world = world;
        rd = new Random();
        wasHit = false;
        rotation = 0;
        hasAim = false;
        gRenderer.addSprite(this)
                .texture(ENEMY_SHIP_IMG_PATH)
                .rotatable(() -> rotation)
                .destroy(() -> false)
                .create();
    }

    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    @Override
    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        if (type == GObjectType.Player || type == GObjectType.Enemy || type == GObjectType.Bullet || type == GObjectType.Asteroid) {
            wasHit = true;
        }
    }

    private BulletObject tryShoot() {
        final int padding = 50;
        if (TimeUtils.millis() - lastShootTime > GameSettings.ENEMY_SHOOT_COOL_DOWN) {
            lastShootTime = TimeUtils.millis();
            return new BulletObject((int) (getCenterX() + cos(toRadians(getRotation())) * (ENEMY_WIDTH / 4 + BULLET_HEIGHT + padding)),
                    (int) (getCenterY() + sin(toRadians(getRotation())) * (ENEMY_HEIGHT / 4 + BULLET_HEIGHT + padding)),
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
            rotation += da;
            aim -= da;
        } else {
            rotation += ENEMY_USUAL_ROTATION_SPEED;
            hasAim = false;
        }
        int dx = (int) (cos(toRadians(getRotation() % 360)) * SPEED_ENEMY);
        int dy = (int) (sin(toRadians(getRotation() % 360)) * SPEED_ENEMY);
        body.setLinearVelocity(dx, dy);
        if (playerDistance < GameSettings.ENEMY_SHOOT_DISTANCE) return tryShoot();
        else return null;
    }

    private float getAngleOfPlayer(float playerX, float playerY) {
        double[] A = {playerX - getCenterX(), playerY - getCenterY()};
        double[] B = {cos(toRadians(getRotation())), sin(toRadians(getRotation()))};
        double dotProduct = A[0] * B[0] + A[1] * B[1];
        double crossProduct = A[0] * B[1] - A[1] * B[0];
        double angle = Math.atan2(crossProduct, dotProduct);
        return (float) Math.toDegrees(angle);
    }

    private float getPlayerDistance(float playerX, float playerY) {
        float dx = playerX - getCenterX();
        float dy = playerY - getCenterY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public float getRotation() {
        return rotation - 90;
    }

    public boolean destroy() {
        return wasHit;
    }

    public GObjectType type() {
        return GObjectType.Enemy;
    }

}
