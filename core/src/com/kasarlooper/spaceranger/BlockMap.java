package com.kasarlooper.spaceranger;

import com.kasarlooper.spaceranger.levels.planet.objects.PhysicsPlatform;

import java.util.List;

public class BlockMap {
    private PhysicsPlatform[][] blocks;

    public BlockMap(List<PhysicsPlatform> blockList, int worldWidth, int worldHeight) {
        /*
        blocks = new PhysicsPlatform[worldHeight][worldWidth];

        for (PhysicsPlatform block : blockList) {
            int x = (block.getCenterX()) / BLOCK_SIZE;
            int y = 16 - (block.getCenterY() - 95 + BLOCK_SIZE) / BLOCK_SIZE;
            System.out.println(x + " " + y);
            blocks[y][x] = block;
        }
         */

        /*
        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                System.out.print(blocks[y][x] != null ? "*" : " ");
            }
            System.out.println();
        }
         */

    }

    public boolean isContact(int x, int y, int width, int height) {
        /*
        if (y == 135) return true;
        if (y < 217) return false;
        if ((y - 217) % BLOCK_SIZE > 0) return false;
        final int padding = 10;
        int leftX = (x + padding) / BLOCK_SIZE;
        int rightX = (x + width - padding) / BLOCK_SIZE;
        if (x % BLOCK_SIZE == 0) rightX -= 1;
        int blockY = 15 - (y - 217) / BLOCK_SIZE;
        if (blockY < 0) return false;
        return !(blocks[blockY][leftX] == null && blocks[blockY][rightX] == null);
         */
        return true;
     }
}
