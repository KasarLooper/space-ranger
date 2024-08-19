package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.CRYSTAL_IMG_PATH;
import static com.kasarlooper.spaceranger.GameResources.WRECKAGE_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;

import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.objects.PhysicsObject;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.BodyBuilder;
import com.kasarlooper.spaceranger.physics.WorldWrap;

public class ResourceObject extends PhysicsObject {
    public Body body;

    Type type;
    boolean wasHit;

    public ResourceObject(int x, int y, boolean isCrystal, WorldWrap world) {
        super(isCrystal ? CRYSTAL_IMG_PATH : WRECKAGE_IMG_PATH, x, y, BLOCK_SIZE, BLOCK_SIZE);
        body = createBody(x, y, world);
        type = Type.Resource;
        wasHit = false;
    }

    @Override
    protected Body createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .friction()
                .createBody(world, this);
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        if (type == Type.Player) {
            wasHit = true;
        }
    }

    public Type type() {
        return type;
    }

    public boolean destroy() {
        return wasHit;
    }
}
