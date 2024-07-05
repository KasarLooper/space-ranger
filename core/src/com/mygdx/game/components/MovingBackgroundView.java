package com.mygdx.game.components;

import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingBackgroundView extends View{
    Texture texture;

    float cameraX, cameraY;

    float texture1X, texture1Y;
    float texture2X, texture2Y;
    float texture3X, texture3Y;
    float texture4X, texture4Y;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture = new Texture(pathToTexture);

        cameraX = 0;
        cameraY = 0;

        texture1X = 0;
        texture1Y = 0;

        texture2X = SCREEN_WIDTH;
        texture2Y = 0;

        texture3X = 0;
        texture3Y = SCREEN_HEIGHT;

        texture4X = SCREEN_WIDTH;
        texture4Y = SCREEN_HEIGHT;
    }
    public void move(float newX, float newY) {
        cameraX = newX - SCREEN_WIDTH / 2f;
        cameraY = newY - SCREEN_HEIGHT / 2f;

        float left = Math.min(Math.min(texture1X, texture2X), Math.min(texture3X, texture4X));
        float right = Math.max(Math.max(texture1X, texture2X), Math.max(texture3X, texture4X)) + SCREEN_WIDTH;
        float down = Math.min(Math.min(texture1Y, texture2Y), Math.min(texture3Y, texture4Y));
        float up = Math.max(Math.max(texture1Y, texture2Y), Math.max(texture3Y, texture4Y)) + SCREEN_HEIGHT;

        boolean isLeftEmpty = cameraX < left;
        boolean isRightEmpty = cameraX + SCREEN_WIDTH > right;
        boolean isDownEmpty = cameraY < down;
        boolean isUpEmpty = cameraY + SCREEN_HEIGHT > up;

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

        if (isDownEmpty) {
            texture1Y -= SCREEN_HEIGHT;
            texture2Y -= SCREEN_HEIGHT;
            texture3Y -= SCREEN_HEIGHT;
            texture4Y -= SCREEN_HEIGHT;
        } else if (isUpEmpty) {
            texture1Y += SCREEN_HEIGHT;
            texture2Y += SCREEN_HEIGHT;
            texture3Y += SCREEN_HEIGHT;
            texture4Y += SCREEN_HEIGHT;
        }
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, texture1X, texture1Y, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.draw(texture, texture2X, texture2Y, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.draw(texture, texture3X, texture3Y, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.draw(texture, texture4X, texture4Y, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    @Override public void dispose() {
        texture.dispose();
    }
}

