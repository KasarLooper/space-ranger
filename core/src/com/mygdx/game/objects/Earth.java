package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.EARTH_HEIGHT;
import static com.mygdx.game.GameSettings.EARTH_WIDTH;
import static com.mygdx.game.GameSettings.GROUND_HEIGHT;
import static com.mygdx.game.GameSettings.SCALE;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class Earth extends GameObject{
    Body body;
    Texture texture;
    float cameraX;
    float[] earthXs;

    public Earth(int y, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(def);

        EdgeShape shape = new EdgeShape();
        shape.set(new Vector2(-1000, y * SCALE), new Vector2(1000, y * SCALE));

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.friction = 0.5f;
        fixture.restitution = 0;
        Fixture fix = body.createFixture(fixture);
        fix.setUserData(this);

        shape.dispose();

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
            batch.draw(texture, x, GROUND_HEIGHT - EARTH_HEIGHT, EARTH_WIDTH, EARTH_HEIGHT);
    }
}
