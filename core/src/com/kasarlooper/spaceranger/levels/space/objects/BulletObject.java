package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameResources.BULLET_IMG_PATH;
import static com.kasarlooper.spaceranger.GameResources.ENEMY_BULLET_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.BULLET_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.BULLET_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.BULLET_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.math.Vector2;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.BodyWrap;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.levels.rendering.GraphicsRenderer;

public class BulletObject extends GameObject {
    public BodyWrap body;
    private GObjectType type;
    public boolean wasHit;

    public BulletObject(int x, int y, WorldWrap world, float degrees, boolean isEnemy, GraphicsRenderer gRenderer) {
        super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        body = createBody(x, y, world);
        body.setLinearVelocity(new Vector2((float) (cos(toRadians(degrees)) * BULLET_SPEED),
                (float) (sin(toRadians(degrees)) * BULLET_SPEED)));
        gRenderer.addSprite(this)
                .texture(isEnemy ? ENEMY_BULLET_IMG_PATH : BULLET_IMG_PATH)
                .destroy(body::isDestroyed)
                .rotatable(() -> degrees - 90)
                .create();
        body.setBullet(true);
        wasHit = false;
        if (isEnemy) type = GObjectType.EnemyBullet;
        else type = GObjectType.Bullet;
    }

    protected BodyWrap createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    @Override
    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        wasHit = true;
    }

    public boolean destroy(float centreX, float centreY) {
        return wasHit || !(getCenterX() > centreX - (width + SCREEN_WIDTH) / 2 &&
                getCenterX() < centreX + (width + SCREEN_WIDTH) / 2 &&
                getCenterY() > centreY - (height + SCREEN_HEIGHT) / 2 &&
                getCenterY() < centreY + (height + SCREEN_HEIGHT) / 2);
    }

    public GObjectType type() {
        return type;
    }
}
