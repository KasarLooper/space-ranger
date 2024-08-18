package com.kasarlooper.spaceranger.objects;

import com.kasarlooper.spaceranger.MyGdxGame;

public class GameObject {
    public int x;
    public int y;
    public int width;
    public int height;

    public GameObject(int x, int y) {
        this(x, y, 0, 0);
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
