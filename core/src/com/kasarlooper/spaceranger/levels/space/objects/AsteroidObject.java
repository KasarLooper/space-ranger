package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameResources.ASTEROID_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.ASTEROID_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.ASTEROID_WIDTH_MAX;
import static com.kasarlooper.spaceranger.GameSettings.ASTEROID_WIDTH_MIN;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

import java.util.Random;

public class AsteroidObject extends PhysicsObject {
    public Body body;

    private static final Random rd = new Random();
    private Texture texture;

    public AsteroidObject(int x, int y, WorldWrap world, int playerX, int playerY) {
        super(x, y, ASTEROID_WIDTH_MIN + rd.nextInt(ASTEROID_WIDTH_MAX - ASTEROID_WIDTH_MIN),
                ASTEROID_WIDTH_MIN + rd.nextInt(ASTEROID_WIDTH_MAX - ASTEROID_WIDTH_MIN));
        body = createBody(x, y, world);
        texture = new Texture(ASTEROID_IMG_PATH);
        float dx = playerX - x;
        float dy = playerY - y;
        float ratio = (float) Math.sqrt(dx * dx + dy * dy);
        float speedX = (float) ASTEROID_SPEED * dx / ratio;
        float speedY = (float) ASTEROID_SPEED * dy / ratio;
        body.setLinearVelocity(speedX, speedY);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, cornerX, cornerY, width, height);
    }

    @Override
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        super.hit(type, myGdxGame);
    }

    @Override
    public Type type() {
        return Type.Asteroid;
    }
}
