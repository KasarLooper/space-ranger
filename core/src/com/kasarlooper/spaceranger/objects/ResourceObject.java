package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameResources.CRYSTAL_IMG_PATH;
import static com.kasarlooper.spaceranger.GameResources.WRECKAGE_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;

public class ResourceObject extends PhysicsObject {
    Type type;
    boolean wasHit;

    public ResourceObject(int x, int y, boolean isCrystal, World world) {
        super(isCrystal ? CRYSTAL_IMG_PATH : WRECKAGE_IMG_PATH, x, y, BLOCK_SIZE, BLOCK_SIZE, world);
        type = Type.Resource;
        wasHit = false;
    }

    @Override
    protected float getFriction() {
        return 1;
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
