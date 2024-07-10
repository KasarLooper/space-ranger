package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.World;

public class ResourceObject extends PhysicsObject {
    ResourceObject(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
    }
}
