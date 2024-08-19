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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

public class BulletObject extends PhysicsObject {
    public Body body;

    private Type type;
    public boolean wasHit;

    Sprite sprite;

    public BulletObject(int x, int y, WorldWrap world, float degrees, boolean isEnemy) {
        super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        body = createBody(x, y, world);
        body.setLinearVelocity(new Vector2((float) (cos(toRadians(degrees)) * BULLET_SPEED),
                (float) (sin(toRadians(degrees)) * BULLET_SPEED)));
        body.setBullet(true);
        sprite = new Sprite(new Texture(isEnemy ? ENEMY_BULLET_IMG_PATH : BULLET_IMG_PATH));
        sprite.setRotation(degrees + 270);
        wasHit = false;
        if (isEnemy) type = Type.EnemyBullet;
        else type = Type.Bullet;
    }

    @Override
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    public void draw(SpriteBatch batch) {
        sprite.setBounds(cornerX, cornerY, width, height);
        sprite.draw(batch);
    }


    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        wasHit = true;
    }

    public boolean destroy(float centreX, float centreY) {
        return wasHit || !(getCenterX() > centreX - (width + SCREEN_WIDTH) / 2 &&
                getCenterX() < centreX + (width + SCREEN_WIDTH) / 2 &&
                getCenterY() > centreY - (height + SCREEN_HEIGHT) / 2 &&
                getCenterY() < centreY + (height + SCREEN_HEIGHT) / 2);
    }
    public Type type() {
        return type;
    }
}
