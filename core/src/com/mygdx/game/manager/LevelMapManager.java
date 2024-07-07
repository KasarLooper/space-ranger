package com.mygdx.game.manager;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LevelMapManager {

    public void loadMap(String imagePath) {
        try {
            File file = new File(imagePath);
            BufferedImage image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    System.out.println("Pixel at (" + x + "," + y + ") - RGB: (" + red + "," + green + "," + blue + ")");
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void run(String image) {
        LevelMapManager mapManager = new  LevelMapManager();
        mapManager.loadMap(image);
    }
}
