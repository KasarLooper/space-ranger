package com.mygdx.game.objects;

public class GameObject {
    public int x;
    public int y;

    public Type type() {
        return  null;
    }

    public void hit(Type type) {
        // вся физика ударов и т.п.
    }
}
