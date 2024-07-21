package com.mygdx.game;

import static com.mygdx.game.GameSettings.BLOCK_SIZE;
import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.physics.box2d.Fixture;
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

        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                System.out.print(blocks[y][x] != null ? "*" : " ");
            }
            System.out.println();
        }

    }

    public boolean isContact(int x, int y, int width, int height) {
        if (y == 135) return true;
        if (y < 217) return false;
        if ((y - 217) % BLOCK_SIZE > 0) return false;
        int leftX = x / BLOCK_SIZE;
        int rightX = (x + width) / BLOCK_SIZE;
        if (x % BLOCK_SIZE == 0) rightX -= 1;
        int blockY = 15 - (y - 217) / BLOCK_SIZE;
        if (blockY < 0) return false;
        return !(blocks[blockY][leftX] == null && blocks[blockY][rightX] == null);
        /*
        int bottomY = (int) (y - height / 2f);
        int dy = Math.abs(bottomY - bottomY / BLOCK_SIZE * BLOCK_SIZE - 50);
        //System.out.println(dy);
        if (dy < 0 || dy > BLOCK_SIZE / 5)
            return false;
        int leftX = ((x - width / 2 + width / 2 + 10)) / BLOCK_SIZE;
        int rightX = ((x + width / 2 + width / 2 - 10)) / BLOCK_SIZE;
        int blockY = 15 - (((y - height / 2 - 50)) / BLOCK_SIZE);
        //System.out.println(leftX + " " + rightX + " " + blockY);
        if (blockY == 15)
            return true;
        if (blockY < 0)
            return false;
        return blocks[blockY][leftX] != null || blocks[blockY][rightX] != null;
         */
     }
}
