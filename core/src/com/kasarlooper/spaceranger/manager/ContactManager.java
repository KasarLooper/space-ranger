package com.kasarlooper.spaceranger.manager;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.levels.gobjects.GameObject;

public class ContactManager {
    World world;
    MyGdxGame myGdxGame;

    public ContactManager(World world, MyGdxGame game) {
        this.world = world;
        this.myGdxGame = game;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                ((GameObject) fixA.getUserData()).hit(((GameObject) fixB.getUserData()).type(), myGdxGame);
                ((GameObject) fixB.getUserData()).hit(((GameObject) fixA.getUserData()).type(), myGdxGame);

            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

}
