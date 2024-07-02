package com.mygdx.game.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class MovingBackgroundView extends View{
    Texture texture;

    int texture1X;
    int texture2X;
    int speed = 2;
    Texture textureX1;
    Texture textureY1;
    Texture textureX2;
    Texture textureY2;
    Texture textureX3;
    Texture textureY3;
    Texture textureX4;
    Texture textureY4;


    // Создай дополнительные поля здесь

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1X = 0;
        texture2X = GameSettings.SCREEN_WIDTH;

        // Инициализируй необходимые поля здесь
        texture = new Texture(pathToTexture);
    }
    public void move(float dx, float dy) {

        // Реализуй логику здесь
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, texture1X, 0, GameSettings.SCREEN_WIDTH + 2, GameSettings.SCREEN_HEIGHT);
        batch.draw(texture, texture2X, 0, GameSettings.SCREEN_WIDTH + 2, GameSettings.SCREEN_HEIGHT);

        // Дорисуй необходимые изображения фона здесь
    }
    @Override public void dispose() {
        texture.dispose();
    }
}
