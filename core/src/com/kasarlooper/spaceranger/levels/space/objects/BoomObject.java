package com.kasarlooper.spaceranger.levels.space.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;

public class BoomObject extends GameObject {
    int i = 3;
    private Texture texture;

    public BoomObject(int x, int y) {
        super(x, y, 1, 1);
        texture = new Texture(GameResources.BOOM_IMG_PATH);
    }


    public void Boom_action() {
        if(width >= 100 || height >= 100)
            i = -3;
        width += i;
        height += i;

    }
    public void draw(SpriteBatch batch) {
        batch.draw(texture, cornerX, cornerY, width, height);
    }

    public boolean isNotAlive() {
        return width <= 0 || height <= 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
