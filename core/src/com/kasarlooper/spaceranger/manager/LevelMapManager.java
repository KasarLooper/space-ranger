package com.kasarlooper.spaceranger.manager;


import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;
import static com.kasarlooper.spaceranger.GameSettings.GROUND_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.MAP_HEIGHT;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.levels.gobjects.GObjectImpl;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.levels.planet.objects.PhysicsPlatform;
import com.kasarlooper.spaceranger.levels.rendering.GraphicsRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelMapManager {
    ArrayList<PhysicsPlatform> physics;
    ArrayList<GameObject> mobSpawns;
    ArrayList<GameObject> resSpawns;
    int playerX, playerY;
    int capsuleStartX, capsuleStartY;
    int capsuleEndX, capsuleEndY;
    int playerPixelX;
    private HashMap<Integer, List<Integer>> greenBlocksMap, blackBlocksMap;

    public void loadMap(WorldWrap world, GraphicsRenderer renderer) {
        physics = new ArrayList<>();
        mobSpawns = new ArrayList<>();
        resSpawns = new ArrayList<>();
        capsuleStartX = -1;
        capsuleStartY = -1;
        greenBlocksMap = new HashMap<>();
        blackBlocksMap = new HashMap<>();

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

        initPlatforms(world, renderer, greenBlocksMap, true);
        initPlatforms(world, renderer, blackBlocksMap, false);
    }

    private void initPlatforms(WorldWrap world, GraphicsRenderer renderer, Map<Integer, List<Integer>> cords, boolean isGreen) {
        for (Map.Entry<Integer, List<Integer>> entry : cords.entrySet()) {
            List<Integer> xs = entry.getValue();
            if (xs.isEmpty()) continue;
            int y = entry.getKey();
            int firstX = xs.get(0);
            int lastX = firstX;
            for (int i = 1; i < xs.size(); i++) {
                int curX = xs.get(i);
                if (curX - lastX == 1)
                    lastX = curX;
                else {
                    int platformX = (getX(lastX) + getX(firstX)) / 2;
                    int platformWidth = lastX - firstX + 1;
                    physics.add(new PhysicsPlatform(platformX, getY(y), platformWidth,
                            isGreen, world, renderer));
                    firstX = curX;
                    lastX = curX;
                }
            }
        }
    }

    @SuppressWarnings("NewApi")
    private void initBlocks(int red, int green, int blue, int x, int y, WorldWrap world, GraphicsRenderer renderer) {
        if (red == 0 && green == 255 && blue == 0) {
            List<Integer> list = greenBlocksMap.getOrDefault(y, new ArrayList<>());
            list.add(x);
            greenBlocksMap.put(y, list);
        } else if (red == 0 && green == 0 && blue == 0) {
            List<Integer> list = blackBlocksMap.getOrDefault(y, new ArrayList<>());
            list.add(x);
            blackBlocksMap.put(y, list);
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

    public ArrayList<PhysicsPlatform> getPhysics() {
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

    private int getX(int pixelX) {
        return playerX + (pixelX - playerPixelX) * BLOCK_SIZE;
    }

    private int getY(int pixelY) {
        return playerY + (MAP_HEIGHT - pixelY - 3) * BLOCK_SIZE;
    }
}
