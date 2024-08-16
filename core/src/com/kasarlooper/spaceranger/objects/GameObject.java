package com.kasarlooper.spaceranger.objects;

import com.kasarlooper.spaceranger.MyGdxGame;

public class GameObject {
    public int x;
    public int y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
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
