package com.kasarlooper.spaceranger.levels.planet.objects;

import static com.kasarlooper.spaceranger.GameSettings.EARTH_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.EARTH_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.GROUND_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;
import com.kasarlooper.spaceranger.levels.physics.BodyBuilder;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;

public class Earth extends GameObject {
    Body body;
    Texture texture;
    float cameraX;
    float[] earthXs;

    public Earth(WorldWrap world) {
        super(0, GROUND_HEIGHT);
        body = BodyBuilder.init()
                .cords(0, 0)
                .size(0, 0)
                .shape(BodyBuilder.GROUND)
                .staticType()
                .friction()
                .createBody(world, this);

        texture = new Texture(GameResources.EARTH_BACKGROUND_IMG_PATH);
        cameraX = 0;
        earthXs = new float[(int) Math.ceil((double) SCREEN_WIDTH * 2d / (double) EARTH_WIDTH)];
        int i = 0;
        for (float x = -SCREEN_WIDTH / 2f; x < SCREEN_WIDTH * 1.5f; x += EARTH_WIDTH) {
            earthXs[i] = x;
            i++;
        }
    }

    public void draw(SpriteBatch batch, float newX) {
        if (newX < earthXs[0] + SCREEN_WIDTH / 2f)
            for (int i = 0; i < earthXs.length; i++)
                earthXs[i] -= EARTH_WIDTH;
        else if (newX > earthXs[earthXs.length - 1] - SCREEN_WIDTH / 2f)
            for (int i = 0; i < earthXs.length; i++)
                earthXs[i] += EARTH_WIDTH;


        for (float x : earthXs)
            batch.draw(texture, x, GROUND_HEIGHT - EARTH_HEIGHT / 2f, EARTH_WIDTH, EARTH_HEIGHT);
    }

    public void dispose() {
        texture.dispose();
    }
}
