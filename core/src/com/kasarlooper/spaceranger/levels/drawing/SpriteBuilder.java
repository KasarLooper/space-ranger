package com.kasarlooper.spaceranger.levels.drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;

import java.util.function.Supplier;

public class SpriteBuilder {
    private final GraphicsRenderer renderer;
    private final GameObject object;
    private final SpriteBatch batch;

    private Texture[] textures;
    private Sprite[] sprites;
    private int spriteI = 0;
    private Supplier<Float> rot = () -> 0f;
    private Supplier<Boolean> isDestroy = () -> false;

    public SpriteBuilder(GraphicsRenderer renderer, GameObject object, SpriteBatch batch) {
        this.renderer = renderer;
        this.object = object;
        this.batch = batch;
    }

    public SpriteBuilder texture(String texturePath) {
        if (textures != null) throw new UnsupportedOperationException("Textures  are already set");
        textures = new Texture[]{new Texture(texturePath)};
        sprites = new Sprite[]{new Sprite(textures[0])};
        return this;
    }

    public SpriteBuilder textures(String[] texturePaths) {
        if (textures != null) throw new UnsupportedOperationException("Textures  are already set");
        textures = new Texture[texturePaths.length];
        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture(texturePaths[i]);
            sprites[i] = new Sprite(textures[i]);
        }
        return this;
    }

    public SpriteBuilder rotatable(Supplier<Float> rot) {
        this.rot = rot;
        for (Sprite sprite : sprites)
            sprite.setOrigin(object.width / 2f, object.height / 2f);
        return this;
    }

    public SpriteBuilder destroy(Supplier<Boolean> isDestroy) {
        this.isDestroy = isDestroy;
        return this;
    }

    public void create() {
        renderer.add(new Drawable() {
            @SuppressWarnings("NewApi")
            @Override
            public void updateAndDraw(float delta) {
                sprites[spriteI].setBounds(object.getCornerX(), object.getCornerY(), object.width, object.height);
                sprites[spriteI].setRotation(rot.get());
                sprites[spriteI].draw(batch);
            }

            @Override
            public void dispose() {
                for (int i = 0; i < sprites.length; i++)
                    textures[i].dispose();
            }

            @SuppressWarnings("NewApi")
            public boolean isDisposing() {
                return isDestroy.get();
            }
        });
    }
}
