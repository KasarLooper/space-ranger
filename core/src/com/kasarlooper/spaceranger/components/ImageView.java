package com.kasarlooper.spaceranger.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends View{
    Texture texture;

    public ImageView(float x, float y, String imagePath) {
        super(x, y);
        texture = new Texture(imagePath);
        this.width = texture.getWidth() ;
        this.height = texture.getHeight() ;
    }

    // просто на всякий случай)
    public ImageView(float height, float wight, float x, float y, String imagePath) {
        super(x, y);
        texture = new Texture(imagePath);
        this.width = wight;
        this.height = height;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
