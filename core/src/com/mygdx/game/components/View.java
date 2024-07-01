package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class View implements Disposable {
    protected int x;
    protected int y;

    public View(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
    }
}
