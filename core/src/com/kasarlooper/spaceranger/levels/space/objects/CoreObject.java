package com.kasarlooper.spaceranger.levels.space.objects;

import static com.kasarlooper.spaceranger.GameResources.CORE_IMG_PATH;
import static com.kasarlooper.spaceranger.GameSettings.CORE_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.CORE_WIDTH;

import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.BodyWrap;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.levels.rendering.GraphicsRenderer;
import com.kasarlooper.spaceranger.levels.space.effects.BoomEffect;

public class CoreObject extends GameObject {
    private final GraphicsRenderer gRenderer;
    public BodyWrap body;
    public boolean wasHit;
    public boolean wasCollected;

    public CoreObject(int x, int y, WorldWrap world, GraphicsRenderer gRenderer) {
        super(x, y, CORE_WIDTH, CORE_HEIGHT);
        body = createBody(x, y, world);
        gRenderer.addSprite(this)
                .texture(CORE_IMG_PATH)
                .destroy(body::isDestroyed)
                .create();
        this.gRenderer = gRenderer;
        wasHit = false;
    }

    protected BodyWrap createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .createBody(world, this);
    }

    @Override
    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        wasHit = true;
        if (type == GObjectType.Player)
            wasCollected = true;
        else BoomEffect.boom(getCenterX(), getCenterY(), gRenderer);
    }

    public boolean destroy() {
        return wasHit;
    }

    public GObjectType type() {
        return GObjectType.Core;
    }
}
