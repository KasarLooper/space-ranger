package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameResources.CORE_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.CORE_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.CORE_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

public class CoreObject extends PhysicsObject {
    public Body body;

    public boolean wasHit;
    public boolean wasCollected;
    private Texture texture;

    public CoreObject(int x, int y, WorldWrap world) {
        super(x, y, CORE_WIDTH, CORE_HEIGHT);
        body = createBody(x, y, world);
        texture = new Texture(CORE_IMG_PATH);
        wasHit = false;
    }

    @Override
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, cornerX, cornerY, width, height);
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
