package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Block extends GameObject{

    public Block(int x, int y, int wight, int height, String texturePath, World world) {
        super(texturePath, x, y, wight, height, world);
    }

    @Override
    protected Shape getShape(float x, float y, float width, float height) {
        /*
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(x * SCALE, y * SCALE);
        vertices[1] = new Vector2(x * SCALE, (y + height) * SCALE);
        vertices[2] = new Vector2((x + wight) * SCALE, (y + height) * SCALE);
        vertices[3] = new Vector2((x + wight) * SCALE, y * SCALE);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
         */
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width * SCALE / 2f, height * SCALE / 2f);

        return shape;
    }

    @Override
    protected BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.StaticBody;
    }

    @Override
    protected float getFriction() {
        return 0;
    }

}
