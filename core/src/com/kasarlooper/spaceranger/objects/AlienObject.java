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
        for (int i = 2; i <= 18; i+=2) {
            int j = i / 2 + 4;
            if (j > 9) j -= 9;
            left[i - 2] = new Texture(String.format(GameResources.ALIEN_ANIM_LEFT_IMG_PATTERN, j));
            left[i - 1] = new Texture(String.format(GameResources.ALIEN_ANIM_LEFT_IMG_PATTERN, j));
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

    @Override
    public Type type() {
        return Type.Enemy;
    }
}
