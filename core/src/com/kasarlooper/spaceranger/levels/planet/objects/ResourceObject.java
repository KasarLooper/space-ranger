package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.CRYSTAL_IMG_PATH;
import static com.kasarlooper.spaceranger.GameResources.WRECKAGE_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.BodyWrap;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;

public class ResourceObject extends GameObject {
    public BodyWrap body;

    GObjectType type;
    boolean wasHit;
    private Texture texture;

    public ResourceObject(int x, int y, boolean isCrystal, WorldWrap world) {
        super(x, y, BLOCK_SIZE, BLOCK_SIZE);
        texture = new Texture(isCrystal ? CRYSTAL_IMG_PATH : WRECKAGE_IMG_PATH);
        body = createBody(x, y, world);
        type = GObjectType.Resource;
        wasHit = false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, cornerX, cornerY, width, height);
    }

    protected BodyWrap createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .friction()
                .createBody(world, this);
    }

    @Override
    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        if (type == GObjectType.Player) {
            wasHit = true;
        }
    }

    public GObjectType type() {
        return type;
    }

    public boolean destroy() {
        return wasHit;
    }
}
