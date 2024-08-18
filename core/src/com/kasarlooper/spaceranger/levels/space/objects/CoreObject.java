package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameResources.CORE_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.CORE_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.CORE_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

public class CoreObject extends PhysicsObject {

    public int x, y;

    public boolean wasHit;
    public boolean wasCollected;

    public CoreObject(int x, int y, WorldWrap world) {
        super(CORE_IMG_PATH, x, y, CORE_WIDTH, CORE_HEIGHT, world);
        this.x = x;
        this.y = y;
        wasHit = false;
    }

    @Override
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x * SCALE, y * SCALE)
                .size(width * SCALE, height * SCALE)
                .createBody(world, this);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x - width / 2f, y - height / 2f, width, height);
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        wasHit = true;
        if (type == Type.Player) {
            wasCollected = true;
        }
    }

    public boolean destroy() {
        return wasHit;
    }

    public Type type() {
        return Type.Core;
    }
}
