package com.kasarlooper.spaceranger.levels.drawing;

import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.kasarlooper.spaceranger.GameSettings;

public class GraphicsRenderer {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Matrix4 staticMatrix;

    public GraphicsRenderer(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
        staticMatrix = new Matrix4().setToOrtho2D(0, 0, GameSettings.SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void render(float delta) {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Draw
        batch.end();

        batch.setProjectionMatrix(staticMatrix);
        batch.begin();
        // Draw ship or spaceman
        batch.end();
    }

    public boolean isEffectDrawn() {
        return true;
    }
}
