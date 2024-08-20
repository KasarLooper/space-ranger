package com.kasarlooper.spaceranger.manager;


import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;
import static com.kasarlooper.spaceranger.GameSettings.GROUND_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.MAP_HEIGHT;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.levels.drawing.GraphicsRenderer;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectImpl;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.levels.planet.objects.PhysicsBlock;

import java.util.ArrayList;
import java.util.Collections;

public class LevelMapManager {
    ArrayList<PhysicsBlock> physics;
    ArrayList<GameObject> mobSpawns;
    ArrayList<GameObject> resSpawns;
    int playerX, playerY;
    int capsuleStartX, capsuleStartY;
    int capsuleEndX, capsuleEndY;
    int playerPixelX;

    public void loadMap(WorldWrap world, GraphicsRenderer renderer) {
        physics = new ArrayList<>();
        mobSpawns = new ArrayList<>();
        resSpawns = new ArrayList<>();
        capsuleStartX = -1;
        capsuleStartY = -1;

        Texture texture = new Texture(GameResources.LEVEL_MAP_IMG_PATH);
        TextureData textureData = texture.getTextureData();
        if (!textureData.isPrepared()) textureData.prepare();
        Pixmap image = textureData.consumePixmap();

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
                initBlocks(red, green, blue, x, y, world, renderer);
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

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public ArrayList<GameObject> getMobSpawns() {
        Collections.sort(mobSpawns, (o1, o2) -> Integer.compare(o1.getCenterX(), o2.getCenterX()));
        return mobSpawns;
    }

    public ArrayList<GameObject> getResSpawns() {
        Collections.sort(resSpawns, (o1, o2) -> Integer.compare(o1.getCenterX(), o2.getCenterX()));
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
        return getY(capsuleStartY) - getY(capsuleEndY);
    }

    private void initPlayer(int red, int green, int blue, int x, int y, WorldWrap world) {
        if (red == 255 && green == 0 && blue == 0) {
            playerX = x * BLOCK_SIZE;
            playerY = GROUND_HEIGHT + 85 + (MAP_HEIGHT - y) * BLOCK_SIZE;
            playerPixelX = x;
        }
    }

    private void initBlocks(int red, int green, int blue, int x, int y, WorldWrap world, GraphicsRenderer renderer) {
        if (red == 0 && green == 255 && blue == 0) {
            physics.add(new PhysicsBlock(getX(x), getY(y), true, world, renderer));
        } else if (red == 0 && green == 0 && blue == 0) {
            physics.add(new PhysicsBlock(getX(x), getY(y), false, world, renderer));
        } else if (red == 0 && green == 0 && blue == 255) {
            resSpawns.add(new GObjectImpl(getX(x), getY(y)));
        } else if (red == 255 && green == 255 && blue == 0) {
            mobSpawns.add(new GObjectImpl(getX(x), getY(y)));
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
