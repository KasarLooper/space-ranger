package com.mygdx.game;

import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Random;

public class EntitySpawner {
    private final float minDistance = 50f;
    private final ArrayList<Pair> pairs;
    private final Random rd;

    public EntitySpawner() {
        pairs = new ArrayList<>();
        rd = new Random();
    }

    public Pair newPair(float playerX, float playerY, int width, int height, float degrees) {
        while (true) {
            float x, y;
            float randomDX = rd.nextInt(SCREEN_WIDTH / 2 - width / 2);
            float randomDY = rd.nextInt(SCREEN_HEIGHT / 2 - height / 2);

            if (rd.nextBoolean()) x = playerX - (SCREEN_WIDTH + width) / 2f - randomDX;
            else x = playerX + (SCREEN_WIDTH + width) / 2f + randomDX;

            if (rd.nextBoolean()) y = playerY - (SCREEN_HEIGHT + height) / 2f - randomDY;
            else y = playerY + (SCREEN_HEIGHT + height) / 2f + randomDY;

            if (isFarEnough(x, y)) {
                Pair result = new Pair(x, y);
                pairs.add(result);
                return result;
            }
        }
    }

    private boolean isFarEnough(float x, float y) {
        for (Pair pair : pairs) {
            if (getDistance(x, y, pair.x, pair.y) < minDistance)
                return false;
        }
        return true;
    }

    private float getDistance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) sqrt(dx * dx + dy * dy);
    }

    public static class Pair {
        Pair(float x, float y) {
            this.x = x;
            this.y = y;
        }
        public float x, y;
    }
}
