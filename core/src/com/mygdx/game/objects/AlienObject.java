package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;

import java.util.ArrayList;

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
        if (isStuck()) {
            isRight = !isRight;
            isFindingPlatform = true;
        }
        if (hasToJump(blocks) && Math.abs(body.getLinearVelocity().y) < 0.5f) {
            jump();
            if (isFindingPlatform) {
                isFindingPlatform = false;
                isRight = !isRight;
            }
        }
        if (isRight) stepRight();
        else stepLeft();
    }

    private boolean isStuck() {
        if (TimeUtils.millis() - lastCheckTime > 100) {
            lastCheckTime = TimeUtils.millis();
            if (lastX == getX()) return true;
            else {
                lastX = getX();
                return false;
            }
        } else return false;
    }

    private boolean hasToJump(ArrayList<PhysicsBlock> blocks) {

        return true;
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
