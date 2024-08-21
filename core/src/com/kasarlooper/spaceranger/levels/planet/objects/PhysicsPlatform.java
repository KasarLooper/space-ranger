package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.TEXTURE_BOX_BLACK;
import static com.kasarlooper.spaceranger.GameResources.TEXTURE_BOX_GREEN;
import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;

import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.BodyWrap;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.levels.rendering.GraphicsRenderer;

public class PhysicsPlatform extends GameObject {
    public BodyWrap body;
    WorldWrap world;


    public PhysicsPlatform(int x, int y, int width, boolean isGreen, WorldWrap world, GraphicsRenderer renderer) {
        super(x, y, BLOCK_SIZE * width, BLOCK_SIZE);
        this.cornerX = x;
        this.cornerY = y;

        renderer.addSprite(this)
                .texture(isGreen ? TEXTURE_BOX_GREEN : TEXTURE_BOX_BLACK)
                .create();
        body = createBody(x, y, world);
        this.world = world;
    }

    private BodyWrap createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .shape(BodyBuilder.RECTANGLE)
                .friction()
                .staticType()
                .createBody(world, this);
    }

    @Override
    public GObjectType type() {
        return GObjectType.Block;
    }
}
