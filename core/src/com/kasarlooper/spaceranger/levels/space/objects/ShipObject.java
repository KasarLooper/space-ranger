package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameSettings.COUNT_FRAMES_ONE_IMG;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SHIP_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SHIP_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SPEED_SHIP;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

public class ShipObject extends PhysicsObject {
    public Body body;

    public int livesLeft;
    Sprite sprite;

    public long lastShotTime;

    public float shot_cool_down;
    private float degrees;
    int i = 0;
    private boolean isStop = false;

    public ShipObject(int x, int y, WorldWrap world) {
        super(x, y, SHIP_WIDTH, SHIP_HEIGHT);
        body = createBody(x, y, world);
        body.setLinearDamping(10f);
        livesLeft = 3;
        body.setLinearDamping(10);
        sprite = new Sprite(new Texture(String.format(GameResources.SHIP_IMG_PATH, 3)));
        sprite.setOrigin(width / 2f, height / 2f);
        shot_cool_down = GameSettings.SHOOTING_COOL_DOWN;
    }

    @Override
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
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

    public void draw(SpriteBatch batch) {
        sprite.setBounds(cornerX, cornerY, width, height);
        if (i > 0 && i < 19 * COUNT_FRAMES_ONE_IMG) sprite.setTexture(new Texture(String.format(GameResources.ANIM_SHIP_PORTAL_IMG_PATH_PATTERN,
                i / COUNT_FRAMES_ONE_IMG + 1)));
        sprite.draw(batch);
    }

    public void staticDraw(SpriteBatch batch) {
        sprite.setBounds((SCREEN_WIDTH - width) / 2f, (SCREEN_HEIGHT - height) / 2f, width, height);
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
