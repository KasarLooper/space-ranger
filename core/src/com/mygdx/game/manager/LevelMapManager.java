package com.mygdx.game.manager;


import static com.mygdx.game.GameSettings.BLOCK_SIZE;
import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;
import static com.mygdx.game.GameSettings.GROUND_HEIGHT;
import static com.mygdx.game.GameSettings.MAP_HEIGHT;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.objects.DecorativeBlock;
import com.mygdx.game.objects.PhysicsBlock;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class LevelMapManager {
    ArrayList<PhysicsBlock> physics;
    ArrayList<DecorativeBlock> decors;
    int playerX;
    int playerY;
    int playerPixelX;

    public void loadMap(World world) {
        physics = new ArrayList<>();
        decors = new ArrayList<>();

        try {

            File file = new File(System.getProperty("user.dir") + "/assets/" + GameResources.LEVEL_MAP_IMG_PATH);
            BufferedImage image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    initPlayer(red, green, blue, x, y, world);
                }
            }

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    initBlocks(red, green, blue, x, y, world);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public ArrayList<PhysicsBlock> getPhysics() {
        return physics;
    }

    public ArrayList<DecorativeBlock> getDecors() {
        return decors;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    private void initPlayer(int red, int green, int blue, int x, int y, World world) {
        if (red == 255 && green == 0 && blue == 0) {
            playerX = x * BLOCK_SIZE;
            playerY = GROUND_HEIGHT + COSMONAUT_HEIGHT / 2 + (MAP_HEIGHT - y) * BLOCK_SIZE;
            playerPixelX = x;
        }
    }

    private void initBlocks(int red, int green, int blue, int x, int y, World world) {
        if (red == 0 && green == 255 && blue == 0) {
            physics.add(new PhysicsBlock( playerX + (x - playerPixelX) * BLOCK_SIZE,
                    playerY + (MAP_HEIGHT - y - 3) * BLOCK_SIZE,
                    GameSettings.BLOCK_SIZE, GameSettings.BLOCK_SIZE,
                    GameResources.TEXTURE_BOX_GREEN, world));
        } else if (red == 0 && green == 0 && blue == 0) {
            physics.add(new PhysicsBlock( playerX + (x - playerPixelX) * BLOCK_SIZE,
                    playerY + (MAP_HEIGHT - y - 3) * BLOCK_SIZE,
                    GameSettings.BLOCK_SIZE, GameSettings.BLOCK_SIZE,
                    GameResources.TEXTURE_BOX_BLACK, world));
        }
    }
}
