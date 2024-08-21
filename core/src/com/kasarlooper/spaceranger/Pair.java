package com.kasarlooper.spaceranger;

import java.util.Objects;

public class Pair {
    public Pair(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x, y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Float.compare(x, pair.x) == 0 && Float.compare(y, pair.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
