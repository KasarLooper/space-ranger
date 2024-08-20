package com.kasarlooper.spaceranger.levels.rendering;

import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;

import java.util.HashSet;
import java.util.Set;

public class GraphicsRenderer {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Matrix4 staticMatrix;
    private final Set<Drawable> drawables;
    private final Set<Drawable> effects;

    public GraphicsRenderer(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
        staticMatrix = new Matrix4().setToOrtho2D(0, 0, GameSettings.SCREEN_WIDTH, SCREEN_HEIGHT);
        drawables = new HashSet<>();
        effects = new HashSet<>();
    }

    @SuppressWarnings("NewApi")
    public void render(float delta) {
        camera.update();
        for (Drawable drawable : drawables)
            if (drawable.isDisposing()) drawable.dispose();
        drawables.removeIf(Drawable::isDisposing);
        effects.removeIf(Drawable::isDisposing);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Drawable drawable : drawables)
            drawable.updateAndDraw(delta);
        for (Drawable effect : effects)
            effect.updateAndDraw(delta);
        batch.end();

        batch.setProjectionMatrix(staticMatrix);
        batch.begin();
        // Draw ship or spaceman
        batch.end();
    }

    public SpriteBuilder addSprite(GameObject object) {
        return new SpriteBuilder(this, object, batch, false);
    }

    public SpriteBuilder addEffect(GameObject object) {
        return new SpriteBuilder(this, object, batch, true);
    }

    void add(Drawable sprite) {
        drawables.add(sprite);
    }

    public boolean isEffectDrawn() {
        return effects.isEmpty();
    }

    public void dispose() {
        for (Drawable drawable : drawables)
            drawable.dispose();
        for (Drawable effect : effects)
            effect.dispose();
        drawables.clear();
        effects.clear();
    }
}
