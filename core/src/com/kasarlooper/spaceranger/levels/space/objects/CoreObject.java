package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameResources.CORE_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.CORE_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.CORE_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.BodyWrap;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;

public class CoreObject extends GameObject {
    public BodyWrap body;

    public boolean wasHit;
    public boolean wasCollected;
    private Texture texture;

    public CoreObject(int x, int y, WorldWrap world) {
        super(x, y, CORE_WIDTH, CORE_HEIGHT);
        body = createBody(x, y, world);
        texture = new Texture(CORE_IMG_PATH);
        wasHit = false;
    }

    protected BodyWrap createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, cornerX, cornerY, width, height);
    }

    @Override
    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        wasHit = true;
        if (type == GObjectType.Player) {
            wasCollected = true;
        }
    }

    public boolean destroy() {
        return wasHit;
    }

    public GObjectType type() {
        return GObjectType.Core;
    }
}
