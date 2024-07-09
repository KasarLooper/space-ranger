package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;

public class AlienObject extends SpacemanObject{
    public AlienObject(int x, int y, int wight, int height, String texturePath, int defaultFrame, int speed, int jumpImpulse, World world) {
        super(x, y, wight, height, texturePath, defaultFrame, speed, jumpImpulse, world);
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
    }

    public void move(int playerX, int playerY, Block[] blocks) {
        if (playerX < getX()) stepLeft();
        else if (playerX > getX()) stepRight();
    }
}
