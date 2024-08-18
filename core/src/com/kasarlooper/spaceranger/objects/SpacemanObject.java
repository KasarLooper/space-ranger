package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameResources.COSMONAUT_ANIM_LEFT_IMG_PATTERN;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_JUMP_FORCE;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_SPEED;
import static com.kasarlooper.spaceranger.GameSettings.COSMONAUT_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.SCALE;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.BlockMap;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;

public class SpacemanObject extends PhysicsObject {
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

    public SpacemanObject(int x, int y, World world, BlockMap blockMap) {
        this(x, y, COSMONAUT_WIDTH, COSMONAUT_HEIGHT, COSMONAUT_ANIM_LEFT_IMG_PATTERN, 4,
                COSMONAUT_SPEED, COSMONAUT_JUMP_FORCE, world, blockMap);
    }

    protected SpacemanObject(int x, int y, int wight, int height, String texturePath, int defaultFrame, int speed, int jumpImpulse, World world, BlockMap blockMap) {
        super(String.format(texturePath, defaultFrame), x, y, wight, height, world);
        defaultY = y;
        this.defaultFrame = defaultFrame;
        this.speed = speed;
        this.jumpImpulse = jumpImpulse;
        sprite = new Sprite(texture);

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

    protected void initTextures(int defaultFrame) {
        left = new Texture[14];
        //right = new Texture[14];
        for (int i = 2; i <= 14; i+=2) {
            int j = i / 2 + 3;
            if (j > 7) j -= 7;
            left[i - 2] = new Texture(String.format(COSMONAUT_ANIM_LEFT_IMG_PATTERN, j));
            left[i - 1] = new Texture(String.format(COSMONAUT_ANIM_LEFT_IMG_PATTERN, j));
            //right[i - 2] = new Texture(String.format(GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN, j));
            //right[i - 1] = new Texture(String.format(GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN, j));
        }
        i = 0;
        liveLeft = 3;
    }

    public boolean isAlive() {
        return liveLeft > 0;
    }

    @Override
    protected Shape getShape(float x, float y, float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width) * SCALE / 2f, (height) * SCALE / 2f);
        return shape;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - width / 2f, getY() - (height) / 2f, width, height);
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
        isJump = !blockMap.isContact((int) getX(), (int ) getY(), width, height) && body.getLinearVelocity().y != 0;
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
            //System.out.println(accumulator);
        }
    }

    @Override
    public void hit(Type type, MyGdxGame myGdxGame) {
        body.applyLinearImpulse(new Vector2(0, 3), body.getWorldCenter(), true);
        if (type == Type.Enemy) {
            liveLeft -= 1;
            myGdxGame.audioManager.soundDamageCosmonaut.play(0.2f);
            damageTime = TimeUtils.millis();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (left != null)
            for (Texture tex : left)
                tex.dispose();
        /*
        if (right != null)
            for (Texture tex : right)
                tex.dispose();
         */
    }

    public Type type() {
        return Type.Player;
    }

    @Override
    protected float getFriction() {
        return 0;
    }

    public void staticDraw(SpriteBatch batch) {
        sprite.setBounds((SCREEN_WIDTH - width) / 2f, (SCREEN_HEIGHT - height) / 2f - 10f, width, height);
        boolean isRed = TimeUtils.millis() - damageTime <= GameSettings.LIGHTNING_DAMAGE_MILLIS;
        if (isRed) batch.setShader(GameResources.RED_SHADER);
        sprite.draw(batch);
        if (isRed) batch.setShader(null);
    }
}
