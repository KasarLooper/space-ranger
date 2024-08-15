package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.ASTEROID_SPEED;

import com.badlogic.gdx.physics.box2d.World;
import com.kasarlooper.spaceranger.MyGdxGame;

public class AsteroidObject extends PhysicsObject {
    public AsteroidObject(String texturePath, int x, int y, int wight, int height, World world, int playerX, int playerY) {
        super(texturePath, x, y, wight, height, world);
        float dx = playerX - x;
        float dy = playerY - y;
        float ratio = (float) Math.sqrt(dx * dx + dy * dy);
        float speedX = (float) ASTEROID_SPEED * dx / ratio;
        float speedY = (float) ASTEROID_SPEED * dy / ratio;
        body.setLinearVelocity(speedX, speedY);
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
