package com.kasarlooper.spaceranger.levels;

import com.kasarlooper.spaceranger.MyGdxGame;

public class GameObject {
    protected int cornerX;
    protected int cornerY;
    public float width;
    public float height;

    public GameObject(int x, int y, float width, float height) {
        this.cornerX = x;
        this.cornerY = y;
        this.width = width;
        this.height = height;
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

    public Type type() {
        return null;
    }

    public void hit(Type type, MyGdxGame myGdxGame) {
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
