package com.mygdx.game.manager;


import com.sun.imageio.plugins.png.PNGImageWriter;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;

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

    public static void main(String[] args) {
        method2();
    }

    private static void method3() {
        String imagePattern = "/home/sasha/java/java-projects/space-ranger/assets/cosmonaut_anim_right/cosmonaut_anim_right_%d.png";
        for (int i = 1; i <= 7; i++) {
            try {
                String imagePath = String.format(imagePattern, i);
                File file = new File(imagePath);
                BufferedImage image = ImageIO.read(file);

                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                int minX = imageWidth;
                int maxX = 0;
                int minY = imageHeight;
                int maxY = 0;

                for (int y = 0; y < imageHeight; y++) {
                    for (int x = 0; x < imageWidth; x++) {
                        if (image.getRGB(x, y) > 0) {
                            if (x <= minX) {
                                minX = x;
                            } else if (x >= maxX) {
                                maxX = x;
                            }
                            if (y <= minY) {
                                minY = y;
                            } else if (y >= maxY) {
                                maxY = y;
                            }
                        }
                    }
                }
                int width = maxX - minX;
                int height = maxY - minY;

                imageWidth = 241;
                imageHeight = 321;

                int startX = minX - (imageWidth - width) / 2;
                int startY = minY - (imageHeight - height) / 2;
                int endX = maxX + (imageWidth - width) / 2;
                int endY = maxY + (imageHeight - height) / 2;

                System.out.println(startX + " " + startY + " " + endX + " " + endY);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private static void method1() {
        String imagePattern = "/home/sasha/java/java-projects/space-ranger/assets/cosmonaut_anim_right/cosmonaut_anim_right_%d.png";
        for (int i = 1; i <= 7; i++) {
            try {
                String imagePath = String.format(imagePattern, i);
                File file = new File(imagePath);
                BufferedImage image = ImageIO.read(file);

                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                int minX = imageWidth;
                int maxX = 0;
                int minY = imageHeight;
                int maxY = 0;

                for (int y = 0; y < imageHeight; y++) {
                    for (int x = 0; x < imageWidth; x++) {
                        if (new Color(image.getRGB(x, y)).getAlpha() > 0) {
                            if (x <= minX) {
                                minX = x;
                            } else if (x >= maxX) {
                                maxX = x;
                            }
                            if (y <= minY) {
                                minY = y;
                            } else if (y >= maxY) {
                                maxY = y;
                            }
                        }
                    }
                }
                int width = maxX - minX;
                int height = maxY - minY;
                float ratio = Math.max((float) width / (float) imageWidth, (float) height / (float) imageHeight);
                int newWidth = Math.round((float) imageWidth * ratio);
                int newHeight = Math.round((float) imageHeight * ratio);

                int startX = minX - (newWidth - width) / 2;
                int startY = minY - (newHeight - height) / 2;
                int endX = maxX + (newWidth - width) / 2;
                int endY = maxY + (newHeight - height) / 2;

                System.out.println(startX + " " + startY + " " + endX + " " + endY);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private static void method2() {
        String imagePattern = "/home/sasha/java/java-projects/space-ranger/assets/cosmonaut_anim_right/cosmonaut_anim_right_%d.png";
        int maxWidth = 0;
        int maxHeight = 0;
        for (int i = 1; i <= 7; i++) {
            try {
                String imagePath = String.format(imagePattern, i);
                File file = new File(imagePath);
                BufferedImage image = ImageIO.read(file);

                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                int minX = imageWidth;
                int maxX = 0;
                int minY = imageHeight;
                int maxY = 0;

                for (int y = 0; y < imageHeight; y++) {
                    for (int x = 0; x < imageWidth; x++) {
                        if (image.getRGB(x, y) != 0) {
                            if (x <= minX) {
                                minX = x;
                            } else if (x >= maxX) {
                                maxX = x;
                            }
                            if (y <= minY) {
                                minY = y;
                            } else if (y >= maxY) {
                                maxY = y;
                            }
                        }
                    }
                }
                int width = maxX - minX;
                int height = maxY - minY;
                if (width > maxWidth)
                    maxWidth = width;
                if (height > maxHeight)
                    maxHeight = height;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        System.out.println(maxWidth + " " + maxHeight);
    }
}
