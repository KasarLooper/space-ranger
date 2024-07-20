package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;

public class SpacemanObject extends PhysicsObject {
    int defaultY;
    int defaultFrame;
    int speed;
    int jumpImpulse;
    Sprite sprite;
    int i;
    public boolean isRightDirection;
    public boolean isRightStep;
    public boolean isLeftStep;
    boolean isStopping;
    boolean isJump;
    Texture[] left;
    Texture[] right;
    long jumpTime;
    public int liveLeft;

    public int cristalCount, wreckCount;
    private boolean isRightContact;
    private boolean isLeftContact;

    public SpacemanObject(int x, int y, int width, int height, String texturePath, int defaultFrame, int speed, int jumpImpulse, World world) {
        super(String.format(texturePath, defaultFrame), x, y, width, height, world);
        defaultY = y;
        this.defaultFrame = defaultFrame;
        this.speed = speed;
        this.jumpImpulse = jumpImpulse;
        sprite = new Sprite(texture);

        initTextures(defaultFrame);
        isRightDirection = true;

        cristalCount = 0;
        wreckCount = 0;
    }

    protected void initTextures(int defaultFrame) {
        left = new Texture[14];
        right = new Texture[14];
        for (int i = 2; i <= 14; i+=2) {
            int j = i / 2 + 3;
            if (j > 7) j -= 7;
            left[i - 2] = new Texture(String.format(GameResources.COSMONAUT_ANIM_LEFT_IMG_PATTERN, j));
            left[i - 1] = new Texture(String.format(GameResources.COSMONAUT_ANIM_LEFT_IMG_PATTERN, j));
            right[i - 2] = new Texture(String.format(GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN, j));
            right[i - 1] = new Texture(String.format(GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN, j));
        }
        i = 0;
        isJump = false;
        liveLeft = 3;
    }

    public boolean isAlive() {
        return liveLeft > 0;
    }

    @Override
    protected Shape getShape(float x, float y, float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width - 20) * SCALE / 2f, (height - 10) * SCALE / 2f);
        return shape;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - (width / 2f), getY() - (height / 2f), width, height);
        sprite.draw(batch);
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
        if (!isJump && TimeUtils.millis() - jumpTime > 50) {
            if (!(this instanceof AlienObject)) System.out.println("Jump");
            jumpTime = TimeUtils.millis();
            body.applyLinearImpulse(new Vector2(0, jumpImpulse), body.getWorldCenter(), false);
        }
    }

    public void stop() {
        body.setLinearVelocity(0, body.getLinearVelocity().y);
        isStopping = true;
    }

    public void updateFrames() {
        body.setAwake(true);
        if (isStopping) {
            if (i == 0) {
                isStopping = false;
                isLeftStep = false;
                isRightStep = false;
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
        }
        if (!isJump && Math.abs(body.getLinearVelocity().x) > 0.1) {
            i++;
            if (i >= right.length) i = 0;
        }
        sprite.setTexture(isRightDirection ? right[i] : left[i]);
        if (isRightStep && !isJump) body.setLinearVelocity(speed, body.getLinearVelocity().y);
        else if (isLeftStep && !isJump) body.setLinearVelocity(-speed, body.getLinearVelocity().y);
    }

    public void updateJump() {
        if (isJump) body.setAwake(true);
    }

    @Override
    public void hit(Type type) {
        //body.applyLinearImpulse(new Vector2(0, 3), body.getWorldCenter(), true);
        if (type == Type.Enemy) {
            liveLeft -= 1;
        }
    }

    public Type type() {
        return Type.Player;
    }

    @Override
    protected float getFriction() {
        return 0;
    }

    private int blockContactCount = 0;

    public void setJump(boolean isJump) {
        blockContactCount += isJump ? -1 : 1;
        if (blockContactCount < 0) blockContactCount = 0;
        this.isJump = blockContactCount == 0;
        if (!(this instanceof AlienObject)) System.out.println(blockContactCount);
    }

    public void setRightContact(boolean isRightContact) {
        this.isRightContact = isRightContact;
    }

    public void setLeftContact(boolean isLeftContact) {
        this.isLeftContact = isLeftContact;
    }
}
