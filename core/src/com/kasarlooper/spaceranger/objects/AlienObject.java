package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_HEIGHT;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.BlockMap;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;

import java.util.ArrayList;
import java.util.Collections;

public class AlienObject extends SpacemanObject{
    long lastCheckTime1, lastCheckTime2;
    float lastX, lastY;
    private long stopTime;

    public AlienObject(int x, int y, int wight, int height, String texturePath, int defaultFrame, int speed, int jumpImpulse, World world, BlockMap blockMap) {
        super(x, y, wight, height, texturePath, defaultFrame, speed, jumpImpulse, world, blockMap);
        lastCheckTime1 = TimeUtils.millis();
        lastX = getX();
        stopTime = 0;
    }

    @Override
    protected void initTextures(int defaultFrame) {
        left = new Texture[18];
        //right = new Texture[18];
        for (int i = 2; i <= 18; i+=2) {
            int j = i / 2 + 4;
            if (j > 9) j -= 9;
            left[i - 2] = new Texture(String.format(GameResources.ALIEN_ANIM_LEFT_IMG_PATTERN, j));
            left[i - 1] = new Texture(String.format(GameResources.ALIEN_ANIM_LEFT_IMG_PATTERN, j));
            //right[i - 2] = new Texture(String.format(GameResources.ALIEN_ANIM_RIGHT_IMG_PATTERN, j));
            //right[i - 1] = new Texture(String.format(GameResources.ALIEN_ANIM_RIGHT_IMG_PATTERN, j));
        }
        i = 0;
        isJump = false;
        liveLeft = 2;
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        if (type == Type.Bullet) {
            liveLeft--;
            myGdxGame.audioManager.soundDamageEnemy.play(0.2f);
            stop();
            body.applyLinearImpulse(new Vector2(
                    GameSettings.ALIEN_DAMAGE_IMPULSE * (isRightDirection ? -1 : 1), 0),
                    body.getWorldCenter(), false);
            stopTime = TimeUtils.millis();
            damageTime = TimeUtils.millis();
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    public void move(int playerX, int playerY, ArrayList<PhysicsBlock> blocks) {
        if (TimeUtils.millis() - stopTime < 300) return;
        boolean isRight = playerX > getX();
        jump();
        if (isRight) stepRight();
        else stepLeft();
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
