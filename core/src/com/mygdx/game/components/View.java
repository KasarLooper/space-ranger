package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class View implements Disposable {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public View(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }
    public View(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public boolean isHit(float tx, float ty) {
        return (tx >= x && tx <= x + width && ty >= y && ty <= y + height);
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
