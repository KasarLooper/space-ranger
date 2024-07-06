package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;

public class SpacemanObject extends GameObject{
    Sprite sprite;
    int i;
    boolean isRightDirection;
    boolean isRightStep;
    boolean isLeftStep;
    Texture[] left;
    Texture[] right;

    public SpacemanObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
        sprite = new Sprite(texture);
        left = new Texture[7];
        right = new Texture[7];
        for (int i = 1; i <= 7; i++) {
            int j = i + 3;
            if (j > 7) j -= 7;
            left[i - 1] = new Texture(String.format(GameResources.COSMONAUT_ANIM_LEFT_IMG_PATTERN, j));
            right[i - 1] = new Texture(String.format(GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN, j));
        }
        i = 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setBounds(getX() - (width / 2f), getY() - (height / 2f), width, height);
        if (isLeftStep) sprite.setTexture(left[i]);
        if (isRightStep) sprite.setTexture(right[i]);
        sprite.draw(batch);
    }

    public void stepLeft() {
    }

    public void stepRight() {
    }

    @Override
    public void hit(Type type) {
        // В мире то пока не никого, с кем сталкиваться?
    }

    public Type type() {
        return Type.Ship;
        // Если я правильно понял
    }
}
