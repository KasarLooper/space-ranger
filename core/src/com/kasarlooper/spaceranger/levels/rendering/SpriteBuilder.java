package com.kasarlooper.spaceranger.levels.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;

import java.util.function.Supplier;

public class SpriteBuilder {
    private final GraphicsRenderer renderer;
    private final GameObject object;
    private final SpriteBatch batch;
    private final boolean isEffect;

    private Texture[] textures;
    private Sprite[] sprites;
    private int spriteI = 0;
    private Supplier<Float> rot = () -> 0f;
    private Supplier<Boolean> isDestroy = () -> false;
    private Runnable everyUpdate = () -> {
    };
    private Supplier<Boolean> updateCondition = () -> true;
    private int updateCoolDown = Integer.MAX_VALUE;

    public SpriteBuilder(GraphicsRenderer renderer, GameObject object, SpriteBatch batch, boolean isEffect) {
        this.renderer = renderer;
        this.object = object;
        this.batch = batch;
        this.isEffect = isEffect;
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

    public SpriteBuilder loop(Runnable everyUpdate, int updateCoolDown) {
        this.everyUpdate = everyUpdate;
        this.updateCoolDown = updateCoolDown;
        return this;
    }

    public SpriteBuilder loop(Runnable everyUpdate, int updateCoolDown, Supplier<Boolean> updateCondition) {
        loop(everyUpdate, updateCoolDown);
        this.updateCondition = updateCondition;
        return this;
    }

    public SpriteBuilder destroy(Supplier<Boolean> isDestroy) {
        this.isDestroy = isDestroy;
        return this;
    }

    public void create() {
        renderer.add(new Drawable() {
            private float accumulator = 0;

            @SuppressWarnings("NewApi")
            @Override
            public void updateAndDraw(float delta) {
                accumulator += delta;
                if (updateCondition.get() && accumulator >= updateCoolDown / 1000f) {
                    everyUpdate.run();
                    accumulator -= updateCoolDown / 1000f;
                }

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
