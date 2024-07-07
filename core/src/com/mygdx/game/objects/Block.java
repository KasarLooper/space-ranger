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
import com.badlogic.gdx.physics.box2d.World;

public class Block{
    Body body;

    public int wight;
    public int height;
    public int x;
    public int y;

    public Texture texture;

    public Block(int x, int y, int wight, int height, String texturePath, World world) {
        this.x = x;
        this.y = y;
        this.wight = wight;
        this.height = height;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);
        texture = new Texture(texturePath);

        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(x, y);
        vertices[1] = new Vector2(x, y + height);
        vertices[2] = new Vector2(x + wight, y + height);
        vertices[3] = new Vector2(x + wight, y);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);


        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.friction = 0.5f;
        fixture.restitution = 0.3f;
        body.createFixture(fixture);

        shape.dispose();

    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture,
                x, y,
                wight,
                height);
    }

    public void dispose() {
        texture.dispose();
    }

    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }
}
