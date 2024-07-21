package com.mygdx.game;

import static com.mygdx.game.GameSettings.BLOCK_SIZE;
import com.mygdx.game.objects.PhysicsBlock;
import java.util.List;

public class BlockMap {
    private PhysicsBlock[][] blocks;

    public BlockMap(List<PhysicsBlock> blockList, int worldWidth, int worldHeight) {
        blocks = new PhysicsBlock[worldHeight][worldWidth];
        //blockList = new ArrayList<>();
        //blockList.add(new PhysicsBlock(85, 1370 - 85, BLOCK_SIZE, BLOCK_SIZE, TEXTURE_BOX_BLACK, new World(new Vector2(0, 0), true)));
        for (PhysicsBlock block : blockList) {
            int x = block.x / BLOCK_SIZE;
            int y = 16 - (block.y - 95 + BLOCK_SIZE) / BLOCK_SIZE;
            blocks[y][x] = block;
        }
        /*
        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                System.out.print(blocks[y][x] != null ? "*" : " ");
            }
            System.out.println();
        }
         */
    }
}
