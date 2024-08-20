package com.kasarlooper.spaceranger.levels.space.effects;

import static com.kasarlooper.spaceranger.GameResources.BOOM_IMG_PATH;

import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.rendering.GraphicsRenderer;

public class BoomEffect extends GameObject {
    int i = 3;

    public static void boom(int x, int y, GraphicsRenderer gRenderer) {
        new BoomEffect(x, y, gRenderer);
    }

    private BoomEffect(int x, int y, GraphicsRenderer gRenderer) {
        super(x, y, 1, 1);
        gRenderer.addEffect(this)
                .texture(BOOM_IMG_PATH)
                .destroy(this::isNotAlive)
                .loop(this::update, GameSettings.BOOM_EFFECT_FRAME_COOL_DOWN)
                .create();

    }


    private void update() {
        if (width >= 100 || height >= 100)
            i = -3;
        width += i;
        height += i;

    }

    private boolean isNotAlive() {
        return width <= 0 || height <= 0;
    }
}
