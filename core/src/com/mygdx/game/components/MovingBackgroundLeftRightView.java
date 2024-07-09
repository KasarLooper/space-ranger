package com.mygdx.game.components;

import static com.mygdx.game.GameSettings.CAMERA_Y_FROM_CENTER;
import static com.mygdx.game.GameSettings.GROUND_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingBackgroundLeftRightView extends MovingBackgroundView{
    public MovingBackgroundLeftRightView(String pathToTexture) {
        super(pathToTexture);
    }

    public MovingBackgroundLeftRightView(String pathToTexture, float ratio) {
        super(pathToTexture, ratio);
    }

    @Override
    public void move(float newX, float newY) {
        depthMove(newX, newY);

        float left = Math.min(Math.min(texture1X, texture2X), Math.min(texture3X, texture4X));
        float right = Math.max(Math.max(texture1X, texture2X), Math.max(texture3X, texture4X)) + SCREEN_WIDTH;

        boolean isLeftEmpty = cameraX < left;
        boolean isRightEmpty = cameraX + SCREEN_WIDTH > right;

        if (isLeftEmpty) {
            texture1X -= SCREEN_WIDTH;
            texture2X -= SCREEN_WIDTH;
            texture3X -= SCREEN_WIDTH;
            texture4X -= SCREEN_WIDTH;
        } else if (isRightEmpty) {
            texture1X += SCREEN_WIDTH;
            texture2X += SCREEN_WIDTH;
            texture3X += SCREEN_WIDTH;
            texture4X += SCREEN_WIDTH;
        }
    }

    protected float getNewY(float newY) {
        return newY - SCREEN_HEIGHT / 2f + GROUND_HEIGHT + CAMERA_Y_FROM_CENTER;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, texture1X, texture1Y, SCREEN_WIDTH, SCREEN_HEIGHT * 2f);
        batch.draw(texture, texture2X, texture2Y, SCREEN_WIDTH, SCREEN_HEIGHT * 2f);
    }
}
