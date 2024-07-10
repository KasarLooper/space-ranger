package com.mygdx.game.objects;

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

    public void hit(Type type) {
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
