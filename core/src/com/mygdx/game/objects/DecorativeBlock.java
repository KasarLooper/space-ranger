package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DecorativeBlock extends Block implements Drawable {
    int width;
    int height;
    Texture texture;

    public DecorativeBlock(int x, int y, int wight, int height, String texturePath) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.width = wight;
        this.height = height;
        texture = new Texture(texturePath);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x - width / 2f, y - height / 2f, width, height);
    }
}
