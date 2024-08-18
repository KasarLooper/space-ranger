package com.kasarlooper.spaceranger.objects;

import static com.kasarlooper.spaceranger.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.kasarlooper.spaceranger.objects.physics.BodyBuilder;

public class PhysicsBlock extends Block {
    public int width;
    public int height;

    public Body body;
    public Texture texture;
    World world;


    public PhysicsBlock(int x, int y, int wight, int height, String texturePath, World world) {
        super(x, y);
        this.width = wight;
        this.height = height;
        this.x = x;
        this.y = y;

        texture = new Texture(texturePath);
        body = createBody(x, y, world);
        this.world = world;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture,
                getX() - (width / 2f),
                getY() - (height / 2f),
                width,
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


    private Body createBody(float x, float y, World world) {
        return BodyBuilder.init()
                .cords((x + width / 2f) * SCALE, (y + width / 2f) * SCALE)
                .size(width * SCALE, height * SCALE)
                .shape(BodyBuilder.RECTANGLE)
                .friction()
                .staticType()
                .createBody(world, this);
    }

    @Override
    public Type type() {
        return Type.Block;
    }
}
