package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.CAPSULE_IMG_PATH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.objects.GameObject;

public class CapsuleObject extends GameObject {
    Texture texture;

    public CapsuleObject(int x, int y, float width, float height) {
        super(x, y, width, height);
        texture = new Texture(CAPSULE_IMG_PATH);
    }

    public boolean isCollision(int x, int y) {
        return x >= this.x && x <= this.x + width && y <= this.y && y >= this.y - height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y - height, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
