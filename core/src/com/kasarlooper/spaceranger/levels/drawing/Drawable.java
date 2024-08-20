package com.kasarlooper.spaceranger.levels.drawing;

public interface Drawable {
    void updateAndDraw(float delta);

    void dispose();

    boolean isDisposing();
}
