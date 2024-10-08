package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameResources.COSMONAUT_ANIM_LEFT_IMG_PATTERN;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_JUMP_FORCE;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.BlockMap;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectType;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.BodyWrap;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.manager.AudioManager;

public class SpacemanObject extends GameObject {
    public BodyWrap body;

    private BlockMap blockMap;
    int defaultY;
    int defaultFrame;
    int speed;
    int jumpImpulse;
    Sprite sprite;
    int i;
    public boolean isRightDirection;
    public boolean isRightStep;
    public boolean isLeftStep;
    boolean isStop;
    boolean isJump;
    boolean isWalk;
    Texture[] left;
    long lastChangeIsWalkTime;
    long lastJumpTime;
    public int liveLeft;
    long accumulator;
    long lastUpdateTime;
    long damageTime;

    public int cristalCount, wreckCount;

    public SpacemanObject(int x, int y, WorldWrap world, BlockMap blockMap) {
        this(x, y, COSMONAUT_WIDTH, COSMONAUT_HEIGHT, COSMONAUT_ANIM_LEFT_IMG_PATTERN, 4,
                COSMONAUT_SPEED, COSMONAUT_JUMP_FORCE, world, blockMap);
    }

    protected SpacemanObject(int x, int y, int wight, int height, String texturePath, int defaultFrame, int speed, int jumpImpulse, WorldWrap world, BlockMap blockMap) {
        super(x, y, wight, height);
        body = createBody(x, y, world);

        defaultY = y;
        this.defaultFrame = defaultFrame;
        this.speed = speed;
        this.jumpImpulse = jumpImpulse;
        sprite = new Sprite(new Texture(String.format(texturePath, defaultFrame)));

        initTextures(defaultFrame);
        isRightDirection = true;
        this.blockMap = blockMap;

        cristalCount = 0;
        wreckCount = 0;
        accumulator = 0;

        isJump = false;
        isWalk = true;

        lastChangeIsWalkTime = TimeUtils.millis();
        lastJumpTime = TimeUtils.millis();
        lastUpdateTime = TimeUtils.millis();
        damageTime = 0;
    }

    protected BodyWrap createBody(float x, float y, WorldWrap world) {
        return BodyBuilder.init()
                .cords(x, y)
                .size(width, height)
                .shape(BodyBuilder.RECTANGLE)
                .createBody(world, this);
    }

    protected void initTextures(int defaultFrame) {
        left = new Texture[14];
        for (int i = 2; i <= 14; i+=2) {
            int j = i / 2 + 3;
            if (j > 7) j -= 7;
            left[i - 2] = new Texture(String.format(COSMONAUT_ANIM_LEFT_IMG_PATTERN, j));
            left[i - 1] = new Texture(String.format(COSMONAUT_ANIM_LEFT_IMG_PATTERN, j));
        }
        i = 0;
        liveLeft = 3;
    }

    public boolean isAlive() {
        return liveLeft > 0;
    }

    public void draw(SpriteBatch batch) {
        sprite.setBounds(cornerX, cornerY, width, height);
        boolean isRed = TimeUtils.millis() - damageTime <= GameSettings.LIGHTNING_DAMAGE_MILLIS;
        if (isRed) batch.setShader(GameResources.RED_SHADER);
        sprite.draw(batch);
        if (isRed) batch.setShader(null);
    }

    public void stepLeft() {
        body.setLinearVelocity(-speed, body.getLinearVelocity().y);
        isRightStep = false;
        isLeftStep = true;
        isRightDirection = false;
    }

    public void stepRight() {
        body.setLinearVelocity(speed, body.getLinearVelocity().y);
        isRightStep = true;
        isLeftStep = false;
        isRightDirection = true;
    }

    public void jump() {
        if (!isJump && TimeUtils.millis() - lastJumpTime > 100) {
            isJump = true;
            lastJumpTime = TimeUtils.millis();
            body.applyLinearImpulse(new Vector2(0, jumpImpulse), body.getWorldCenter(), false);
        }
    }

    public void stop() {
        body.setLinearVelocity(0, body.getLinearVelocity().y);
        isStop = true;
    }

    public void update() {
        updateFrames();
        updateJump();
    }

    protected void updateFrames() {
        if (isStop) {
            if (i == 0) {
                isStop = false;
                isLeftStep = false;
                isRightStep = false;
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
        }
        if (i != 0 || (isWalk && body.getLinearVelocity().x != 0)) {
            updateCurrentFrame();
        }
        sprite.setTexture(left[i]);
        sprite.setFlip(isRightDirection, false);
        if (isRightStep) body.setLinearVelocity(speed, body.getLinearVelocity().y);
        else if (isLeftStep) body.setLinearVelocity(-speed, body.getLinearVelocity().y);
    }

    protected void updateJump() {
        body.setAwake(true);
        isJump = !blockMap.isContact(getCenterX(), getCenterY(), (int) width, (int) height) && body.getLinearVelocity().y != 0;
        if (!isJump) isWalk = true;
        else if (TimeUtils.millis() - lastChangeIsWalkTime > 500) {
            isWalk = false;
            lastChangeIsWalkTime = TimeUtils.millis();
        }
    }

    private void updateCurrentFrame() {
        accumulator += TimeUtils.millis() - lastUpdateTime;
        lastUpdateTime = TimeUtils.millis();
        while (accumulator >= GameSettings.FRAME_DURATION) {
            accumulator -= GameSettings.FRAME_DURATION;
            i++;
            if (i >= left.length) i = 0;
        }
    }

    @Override
    public void hit(GObjectType type, MyGdxGame myGdxGame) {
        body.applyLinearImpulse(new Vector2(0, 3), body.getWorldCenter(), true);
        if (type == GObjectType.Enemy) {
            liveLeft -= 1;
            AudioManager.soundDamageCosmonaut.play(0.2f);
            damageTime = TimeUtils.millis();
        }
    }

    public void dispose() {
        if (left != null)
            for (Texture tex : left)
                tex.dispose();
    }

    public GObjectType type() {
        return GObjectType.Player;
    }

    public void staticDraw(SpriteBatch batch) {
        sprite.setBounds((SCREEN_WIDTH - width) / 2f, (SCREEN_HEIGHT - height) / 2f - 10f, width, height);
        boolean isRed = TimeUtils.millis() - damageTime <= GameSettings.LIGHTNING_DAMAGE_MILLIS;
        if (isRed) batch.setShader(GameResources.RED_SHADER);
        sprite.draw(batch);
        if (isRed) batch.setShader(null);
    }
}
