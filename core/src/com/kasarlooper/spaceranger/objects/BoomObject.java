package com.kasarlooper.spaceranger.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.GameResources;

public class BoomObject{
    int i = 3;
    private float x, y;
    private int width;
    private int height;
    private Texture texture;

    public BoomObject(float x, float y) {
        this.x = x;
        this.y = y;
        width = 1;
        height = 1;
        texture = new Texture(GameResources.BOOM_IMG_PATH);
    }


    public void Boom_action() {
        if(width >= 100 || height >= 100)
            i = -3;
        width += i;
        height += i;

    }
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public boolean isNotAlive() {
        return width <= 0 || height <= 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
