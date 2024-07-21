package com.mygdx.game;

import com.mygdx.game.objects.PhysicsBlock;

import java.util.List;

public class BlockArrayWrap {
    private PhysicsBlock[][] blocks;

    public BlockArrayWrap(List<PhysicsBlock> blockList, int worldWidth, int worldHeight) {
        blocks = new PhysicsBlock[worldHeight][worldWidth];
    }
}
