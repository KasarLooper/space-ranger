package com.kasarlooper.spaceranger.objects;

import com.kasarlooper.spaceranger.MyGdxGame;

public class GameObject {
    protected int x;
    protected int y;
    public float width;
    public float height;

    public GameObject(int x, int y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GameObject(int x, int y) {
        this(x, y, 0, 0);
    }

    public Type type() {
        return  null;
    }

    public void hit(Type type, MyGdxGame myGdxGame) {
        // вся физика ударов и т.п.
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
