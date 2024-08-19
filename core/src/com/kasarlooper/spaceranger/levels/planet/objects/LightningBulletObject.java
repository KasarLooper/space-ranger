package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.LIGHTING_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.GameObject;
import com.kasarlooper.spaceranger.levels.Type;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;

public class LightningBulletObject extends GameObject {
    public Body body;

    private final Type type;
    private boolean hasToBeDestroyed;
    private static long lastShootTime;

    Sprite sprite;
    private WorldWrap world;

    public LightningBulletObject(SpacemanObject spaceman, WorldWrap world) {
        super((spaceman.isRightDirection ? spaceman.getCenterX() + (LIGHTING_WIDTH) / 2 + COSMONAUT_WIDTH : spaceman.getCenterX() - (LIGHTING_WIDTH) / 2 - COSMONAUT_WIDTH),
                (spaceman.getCenterY()), LIGHTING_WIDTH, GameSettings.LIGHTING_HEIGHT);
        body = createBody(cornerX, cornerY, world);
        this.world = world;
        body.setBullet(true);
        sprite = new Sprite(new Texture(spaceman.isRightDirection ? GameResources.LIGHTNING_RIGHT_IMG_PATH : GameResources.LIGHTNING_LEFT_IMG_PATH));
        sprite.setBounds(cornerX - width / 2f, cornerY - height / 2f, width, height);
        type = Type.Bullet;
        body.setType(BodyDef.BodyType.DynamicBody);
        hasToBeDestroyed = false;
        lastShootTime = TimeUtils.millis();
    }

    public static boolean isShootTime() {
        return TimeUtils.millis() - lastShootTime > 1000;
    }

    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .sensor()
                .staticType()
                .createBody(world, this);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }


    public boolean destroyIfNeed() {
        if (hasToBeDestroyed || TimeUtils.millis() - lastShootTime > 1000) {
            world.destroyBody(body);
            return true;
        }
        return false;
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        hasToBeDestroyed = true;
    }

    public Type type() {
        return type;
    }
}
