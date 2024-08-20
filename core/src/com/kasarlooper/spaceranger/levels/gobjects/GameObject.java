package com.kasarlooper.spaceranger.levels.gobjects;

import com.kasarlooper.spaceranger.MyGdxGame;

public abstract class GameObject {
    protected int cornerX;
    protected int cornerY;
    public int width;
    public int height;

    public GameObject(int x, int y, int width, int height) {
        this.cornerX = x;
        this.cornerY = y;
        this.width = width;
        this.height = height;
    }

    public int getCornerX() {
        return cornerX;
    }

    public int getCornerY() {
        return cornerY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCenterX() {
        return cornerX + (int) (width / 2f);
    }

    public int getCenterY() {
        return cornerY + (int) (height / 2f);
    }

    public void setCornerX(int x) {
        this.cornerX = x;
    }

    public void setCornerY(int y) {
        this.cornerY = y;
    }

    public GameObject(int x, int y) {
        this(x, y, 0, 0);
    }

    public GObjectType type() {
        return null;
    }

    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        // вся физика ударов и т.п.
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + cornerX +
                ", y=" + cornerY +
                '}';
    }
}
