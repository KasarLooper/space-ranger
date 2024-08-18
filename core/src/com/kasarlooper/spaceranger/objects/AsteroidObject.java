package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameResources.ASTEROID_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.ASTEROID_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.ASTEROID_WIDTH_MAX;
import static com.kasarlooper.spaceranger.GameSettings.ASTEROID_WIDTH_MIN;
import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.physics.BodyBuilder;

import java.util.Random;

public class AsteroidObject extends PhysicsObject {
    private static final Random rd = new Random();

    public AsteroidObject(int x, int y, World world, int playerX, int playerY) {
        super(ASTEROID_IMG_PATH, x, y, ASTEROID_WIDTH_MIN + rd.nextInt(ASTEROID_WIDTH_MAX - ASTEROID_WIDTH_MIN),
                ASTEROID_WIDTH_MIN + rd.nextInt(ASTEROID_WIDTH_MAX - ASTEROID_WIDTH_MIN), world);
        float dx = playerX - x;
        float dy = playerY - y;
        float ratio = (float) Math.sqrt(dx * dx + dy * dy);
        float speedX = (float) ASTEROID_SPEED * dx / ratio;
        float speedY = (float) ASTEROID_SPEED * dy / ratio;
        body.setLinearVelocity(speedX, speedY);
    }

    @Override
    protected Body createBody(float x, float y, World world) {
        return BodyBuilder.init()
                .cords(x * SCALE, y * SCALE)
                .size(width * SCALE, height * SCALE)
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
