package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;

public class BoomObject extends GameObject{
    int i = 3;
    public BoomObject(int x, int y, World world) {
        super(GameResources.BOOM_IMG_PATH, x, y, 1, 1, world);
    }


    public void Boom_action() {
        if(width >= 100 || height >= 100) {
            i = -3;
        }
        width += i;
        height += i;

    }
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public boolean isNotAlive() {
        return width <= 0 || height <= 0;
    }
}
