package com.kasarlooper.spaceranger.components;

import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MiddleButtonView extends ButtonView{
    public MiddleButtonView(float y, float width, float height, BitmapFont font, String texturePath, String text) {
        super((SCREEN_WIDTH - width) / 2f, y, width, height, font, texturePath, text);
    }

    public MiddleButtonView(float y, float width, float height, String texturePath) {
        super((SCREEN_WIDTH - width) / 2f, y, width, height, texturePath);
    }
}
