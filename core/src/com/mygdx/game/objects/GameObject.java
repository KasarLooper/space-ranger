package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Type;


public class GameObject {
    public int wight;
    public int height;

    public Body body;
    Texture texture;

    GameObject(String texturePath, int x, int y, int wight, int height, World world) {
        this.wight = wight;
        this.height = height;

        texture = new Texture(texturePath);
        //body = createBody(x, y, world);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture,
                getX() - (wight / 2f),
                getY() - (height / 2f),
                wight,
                height);
    }
    public void hit(Type type) {
        //
    }

    public Type type() {
        return  null;
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
