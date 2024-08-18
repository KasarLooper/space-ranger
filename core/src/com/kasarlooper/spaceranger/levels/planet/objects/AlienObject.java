package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.ALIEN_ANIM_LEFT_IMG_PATTERN;
import static com.kasarlooper.spaceranger.GameSettings.ALIEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.ALIEN_JUMP_FORCE;
import static com.kasarlooper.spaceranger.GameSettings.ALIEN_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.ALIEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.BlockMap;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.manager.AudioManager;
import com.kasarlooper.spaceranger.objects.Type;
import com.kasarlooper.spaceranger.physics.WorldWrap;

import java.util.ArrayList;

public class AlienObject extends SpacemanObject{
    long lastCheckTime1, lastCheckTime2;
    float lastX, lastY;
    private long stopTime;

    public AlienObject(int x, int y, WorldWrap world, BlockMap blockMap) {
        super(x, y, ALIEN_WIDTH, ALIEN_HEIGHT, ALIEN_ANIM_LEFT_IMG_PATTERN, 5,
                ALIEN_SPEED, ALIEN_JUMP_FORCE, world, blockMap);
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
            left[i - 2] = new Texture(String.format(ALIEN_ANIM_LEFT_IMG_PATTERN, j));
            left[i - 1] = new Texture(String.format(ALIEN_ANIM_LEFT_IMG_PATTERN, j));
        }
        i = 0;
        isJump = false;
        liveLeft = 2;
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        if (type == Type.Bullet) {
            liveLeft--;
            AudioManager.soundDamageEnemy.play(0.2f);
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
