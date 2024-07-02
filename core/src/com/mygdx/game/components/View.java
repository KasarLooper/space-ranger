package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class View implements Disposable {
    protected float x;
    protected float y;

    public View(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
    }

    public void onCameraUpdate(float dx, float dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void dispose() {
    }
}
