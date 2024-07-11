package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.BLOCK_SIZE;
import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;

import java.util.ArrayList;
import java.util.Collections;

public class AlienObject extends SpacemanObject{
    boolean isFindingPlatform;
    long lastCheckTime;
    float lastX;

    public AlienObject(int x, int y, int wight, int height, String texturePath, int defaultFrame, int speed, int jumpImpulse, World world) {
        super(x, y, wight, height, texturePath, defaultFrame, speed, jumpImpulse, world);
        lastCheckTime = TimeUtils.millis();
        lastX = getX();
    }

    @Override
    protected void initTextures(int defaultFrame) {
        left = new Texture[18];
        right = new Texture[18];
        for (int i = 2; i <= 18; i+=2) {
            int j = i / 2 + 4;
            if (j > 9) j -= 9;
            left[i - 2] = new Texture(String.format(GameResources.ALIEN_ANIM_LEFT_IMG_PATTERN, j));
            left[i - 1] = new Texture(String.format(GameResources.ALIEN_ANIM_LEFT_IMG_PATTERN, j));
            right[i - 2] = new Texture(String.format(GameResources.ALIEN_ANIM_RIGHT_IMG_PATTERN, j));
            right[i - 1] = new Texture(String.format(GameResources.ALIEN_ANIM_RIGHT_IMG_PATTERN, j));
        }
        i = 0;
        isJump = false;
        liveLeft = 2;
    }

    @Override
    public void hit(Type type) {
        if (type == Type.Bullet) liveLeft--;
    }

    public void move(int playerX, int playerY, ArrayList<PhysicsBlock> blocks) {
        boolean isRight = playerX > getX();
        if (isStuck() && !isReachPlayer(playerX)) {
            isRight = !isRight;
            isFindingPlatform = true;
        }
        if (hasToJump(blocks, isRight) && Math.abs(body.getLinearVelocity().y) < 0.5f) {
            jump();
            if (isFindingPlatform) {
                isFindingPlatform = false;
                isRight = !isRight;
            }
        }
        if (isRight) stepRight();
        else stepLeft();
    }

    private boolean isReachPlayer(int playerX) {
        return Math.abs(getX() - playerX) <= BLOCK_SIZE / 2f;
    }

    private boolean isStuck() {
        if (TimeUtils.millis() - lastCheckTime > 1000) {
            lastCheckTime = TimeUtils.millis();
            if (lastX == getX()) return true;
            else {
                lastX = getX();
                return false;
            }
        } else return false;
    }

    private boolean hasToJump(ArrayList<PhysicsBlock> blocks, boolean isRight) {
        ArrayList<Pair> distances = getBlockDistances(blocks, isRight);
        int height = getCeilHeight(distances);
        if (height == 0) return false;
        for (Pair cords : distances) {
            if (cords.y <= 0) continue;
            if (cords.x == 1 && cords.y <= height) {
                System.out.printf("%d %d\n", cords.x, cords.y);
                return true;
            }
            if (height == 1) continue;
            if (cords.x == 2 && cords.y <= (height - 2) * 3) {
                System.out.printf("%d %d\n", cords.x, cords.y);
                return true;
            }
            if (height == 2) continue;
            if (cords.x == 3 && cords.y <= 1) {
                System.out.printf("%d %d\n", cords.x, cords.y);
                return true;
            }
        }
        return false;
    }

    private int getCeilHeight(ArrayList<Pair> distances) {
        for (Pair pair : distances) {
            if (pair.x > 1) return 3;
            if (pair.y < 5) return pair.y - 2;
        }
        return 3;
    }

    private ArrayList<Pair> getBlockDistances(ArrayList<PhysicsBlock> blocks, boolean isRight) {
        ArrayList<Pair> result = new ArrayList<>();
        for (PhysicsBlock block : blocks) {
            float dx = isRight ? block.getX() - getX() : getX() - block.getX();
            if (dx <= BLOCK_SIZE / 2f) continue;
            if (dx >= BLOCK_SIZE * 5.5f) continue;
            dx += BLOCK_SIZE / 2f;
            int blockX = (int) Math.floor(dx / BLOCK_SIZE);
            int blockY = (int) Math.floor((block.getY() - BLOCK_SIZE / 2f - getY() + COSMONAUT_HEIGHT / 2f) / BLOCK_SIZE) + 1;
            result.add(new Pair(blockX, blockY));
        }
        Collections.sort(result, (o1, o2) -> Integer.compare(o1.x, o2.x));
        return result;
    }

    private static class Pair {
        public int x;
        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    @Override
    public Type type() {
        return Type.Enemy;
    }

    public boolean isAlive() {
        return liveLeft > 0;
    }

    @Override
    protected float getFriction() {
        return 0;
    }
}
