package com.mygdx.game.manager;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.objects.Block;
import com.mygdx.game.objects.PhysicsBlock;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class LevelMapManager {

    public Block[] loadMap() {
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
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    private Block getBlock(int red, int green, int blue, int x, int y, World world) {
        Color color = new Color(red / 255f, green / 255f, blue / 255f);
        if (red == 0 && green == 0 && blue == 0) {
            return new PhysicsBlock(x, y, GameSettings.BLOCK_SIZE, GameSettings.BLOCK_SIZE, GameResources.BOOM_IMG_PATH, world);
        }
        return null;
    }
}
