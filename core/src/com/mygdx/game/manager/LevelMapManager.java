package com.mygdx.game.manager;


import static com.mygdx.game.GameSettings.BLOCK_SIZE;
import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;
import static com.mygdx.game.GameSettings.GROUND_HEIGHT;
import static com.mygdx.game.GameSettings.MAP_HEIGHT;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.objects.DecorativeBlock;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.PhysicsBlock;

import java.util.ArrayList;
import java.util.Collections;

public class LevelMapManager {
    ArrayList<PhysicsBlock> physics;
    ArrayList<DecorativeBlock> decors;
    ArrayList<GameObject> mobSpawns;
    ArrayList<GameObject> resSpawns;
    int playerX, playerY;
    int capsuleStartX, capsuleStartY;
    int capsuleEndX, capsuleEndY;
    int playerPixelX;

    public void loadMap(World world) {
        physics = new ArrayList<>();
        decors = new ArrayList<>();
        mobSpawns = new ArrayList<>();
        resSpawns = new ArrayList<>();
        capsuleStartX = -1;
        capsuleStartY = -1;

        // Load the texture
        Texture texture = new Texture(GameResources.LEVEL_MAP_IMG_PATH);
        // Get the texture data
        TextureData textureData = texture.getTextureData();

        if (!textureData.isPrepared()) {
            textureData.prepare();
        }

        // Create a Pixmap from the texture data
        Pixmap image = textureData.consumePixmap();

        // Get image dimensions
        int width = image.getWidth();
        int height = image.getHeight();


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getPixel(x, y);
                int red = (rgb >> 24) & 0xFF;
                int green = (rgb >> 16) & 0xFF;
                int blue = (rgb >> 8) & 0xFF;
                initPlayer(red, green, blue, x, y, world);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getPixel(x, y);
                int red = (rgb >> 24) & 0xFF;
                int green = (rgb >> 16) & 0xFF;
                int blue = (rgb >> 8) & 0xFF;
                initBlocks(red, green, blue, x, y, world);
            }
        }

        for (int y = capsuleStartY; y < height; y++) {
            int rgb = image.getPixel(capsuleStartX, y);
            int red = (rgb >> 24) & 0xFF;
            int green = (rgb >> 16) & 0xFF;
            int blue = (rgb >> 8) & 0xFF;
            if (red != 255 && green != 255 && blue != 255) break;
            capsuleEndY++;
        }
        capsuleEndY--;

        for (int x = capsuleStartX; x < width; x++) {
            int rgb = image.getPixel(x, capsuleStartY);
            int red = (rgb >> 24) & 0xFF;
            int green = (rgb >> 16) & 0xFF;
            int blue = (rgb >> 8) & 0xFF;
            if (red != 255 && green != 255 && blue != 255) break;
            capsuleEndX++;
        }
        capsuleEndX--;
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

    public ArrayList<GameObject> getMobSpawns() {
        Collections.sort(mobSpawns, (o1, o2) -> Integer.compare(o1.x, o2.x));
        return mobSpawns;
    }

    public ArrayList<GameObject> getResSpawns() {
        Collections.sort(resSpawns, (o1, o2) -> Integer.compare(o1.x, o2.x));
        return resSpawns;
    }

    public int getCapsuleX() {
        return getX(capsuleStartX);
    }

    public int getCapsuleY() {
        return getY(capsuleStartY);
    }

    public int getCapsuleWidth() {
        return getX(capsuleEndX) - getX(capsuleStartX);
    }

    public int getCapsuleHeight() {
        return getY(capsuleEndY) - getY(capsuleStartY);
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
            physics.add(new PhysicsBlock(getX(x), getY(y),
                    GameSettings.BLOCK_SIZE, GameSettings.BLOCK_SIZE,
                    GameResources.TEXTURE_BOX_GREEN, world));
        } else if (red == 0 && green == 0 && blue == 0) {
            physics.add(new PhysicsBlock(getX(x), getY(y),
                    GameSettings.BLOCK_SIZE, GameSettings.BLOCK_SIZE,
                    GameResources.TEXTURE_BOX_BLACK, world));
        } else if (red == 0 && green == 0 && blue == 255) {
            resSpawns.add(new GameObject(getX(x), getY(y)));
        } else if (red == 255 && green == 255 && blue == 0) {
            mobSpawns.add(new GameObject(getX(x), getY(y)));
        } else if ((red == 255 && green == 255 && blue == 255 ||
                red == 255 && green == 0 && blue == 0) &&
                capsuleStartX == -1 && capsuleStartY == -1) {
            capsuleStartX = x;
            capsuleStartY = y;
            capsuleEndX = x;
            capsuleEndY = y;
        }
    }

    private int getX(int pixelX) {
        return playerX + (pixelX - playerPixelX) * BLOCK_SIZE;
    }

    private int getY(int pixelY) {
        return playerY + (MAP_HEIGHT - pixelY - 3) * BLOCK_SIZE;
    }
}
