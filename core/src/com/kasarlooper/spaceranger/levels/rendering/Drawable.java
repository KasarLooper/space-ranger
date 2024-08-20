package com.kasarlooper.spaceranger.levels.rendering;

public interface Drawable {
    void updateAndDraw(float delta);

    void dispose();

    boolean isDisposing();
}
