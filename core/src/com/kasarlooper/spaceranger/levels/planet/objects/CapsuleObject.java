package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.CAPSULE_IMG_PATH;

import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.rendering.GraphicsRenderer;

public class CapsuleObject extends GameObject {
    public CapsuleObject(int x, int y, int width, int height, GraphicsRenderer gRenderer) {
        super(x, y - height, width, height);
        gRenderer.addSprite(this)
                .texture(CAPSULE_IMG_PATH)
                .create();
    }

    public boolean isCollision(int x, int y) {
        return x >= this.cornerX && x <= this.cornerX + width && y >= this.cornerY && y <= this.cornerY + height;
    }
}
