package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Earth{
    Body body;

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
        body.createFixture(fixture);

        shape.dispose();
    }
}
