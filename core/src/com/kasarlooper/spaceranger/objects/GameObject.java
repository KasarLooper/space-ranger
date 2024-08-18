package com.kasarlooper.spaceranger.objects;

import com.kasarlooper.spaceranger.MyGdxGame;

public class GameObject {
    public int x;
    public int y;
    public float width;
    public float height;

    public GameObject(int x, int y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
