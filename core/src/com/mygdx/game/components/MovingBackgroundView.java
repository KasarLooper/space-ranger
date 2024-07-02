package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class MovingBackgroundView extends View{
    Texture texture;

    int texture1X;
    int texture2X;
    int speed = 2;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1X = 0;
        texture2X = GameSettings.SCREEN_WIDTH;
        texture = new Texture(pathToTexture);
    }
    public void move() {
        /*
        texture1X -= speed;
        texture2X -= speed;

        if (texture1X <= -GameSettings.SCREEN_WIDTH) {
            texture1X = GameSettings.SCREEN_WIDTH;
        }
        if (texture2X <= -GameSettings.SCREEN_WIDTH) {
            texture2X = GameSettings.SCREEN_WIDTH;
        }
         */
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, texture1X, 0, GameSettings.SCREEN_WIDTH + 2, GameSettings.SCREEN_HEIGHT);
        batch.draw(texture, texture2X, 0, GameSettings.SCREEN_WIDTH + 2, GameSettings.SCREEN_HEIGHT);
    }
    @Override public void dispose() {
        texture.dispose();
    }
}
