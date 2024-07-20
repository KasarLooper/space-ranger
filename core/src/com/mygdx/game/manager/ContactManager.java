package com.mygdx.game.manager;

import static com.mygdx.game.GameSettings.BLOCK_SIZE;
import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;
import static com.mygdx.game.GameSettings.SCALE;
import static com.mygdx.game.objects.Type.Player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.mygdx.game.objects.AlienObject;
import com.mygdx.game.objects.Earth;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.PhysicsBlock;
import com.mygdx.game.objects.PhysicsObject;
import com.mygdx.game.objects.SpacemanObject;

import java.util.concurrent.Executor;

public class ContactManager {
    World world;

    public ContactManager(World world) {
        this.world = world;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                ((GameObject) fixA.getUserData()).hit(((GameObject) fixB.getUserData()).type());
                ((GameObject) fixB.getUserData()).hit(((GameObject) fixA.getUserData()).type());

                GameObject a = (GameObject) fixA.getUserData();
                GameObject b = (GameObject) fixB.getUserData();

                if (a instanceof SpacemanObject && (b instanceof PhysicsBlock)) {
                    if (isDownContact(fixA, fixB, contact, false)) ((SpacemanObject) a).setJump(false);
                    if (isRightContact(fixA, fixB, contact)) ((SpacemanObject) a).setRightContact(true);
                    if (isLeftContact(fixA, fixB, contact)) ((SpacemanObject) a).setLeftContact(true);
                } else if (b instanceof SpacemanObject && (a instanceof PhysicsBlock)) {
                    if (isDownContact(fixB, fixA, contact, false)) ((SpacemanObject) b).setJump(false);
                    if (isRightContact(fixB, fixA, contact)) ((SpacemanObject) b).setRightContact(true);
                    if (isLeftContact(fixB, fixA, contact)) ((SpacemanObject) b).setLeftContact(true);
                }

                if (a instanceof SpacemanObject && (b instanceof Earth)) ((SpacemanObject) a).setJump(false);
                else if (b instanceof SpacemanObject && (a instanceof Earth)) ((SpacemanObject) b).setJump(false);
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                GameObject a = (GameObject) fixA.getUserData();
                GameObject b = (GameObject) fixB.getUserData();

                if (a instanceof SpacemanObject && (b instanceof PhysicsBlock)) {
                    if (((PhysicsBlock)b).isContact(a)) ((SpacemanObject) a).setJump(true);
                    if (isRightContact(fixA, fixB, contact)) ((SpacemanObject) a).setRightContact(false);
                    if (isLeftContact(fixA, fixB, contact)) ((SpacemanObject) a).setLeftContact(false);
                } else if (b instanceof SpacemanObject && (a instanceof PhysicsBlock)) {
                    if (((PhysicsBlock)a).isContact(b)) ((SpacemanObject) b).setJump(true);
                    if (isRightContact(fixB, fixA, contact)) ((SpacemanObject) b).setRightContact(false);
                    if (isLeftContact(fixB, fixA, contact)) ((SpacemanObject) b).setLeftContact(false);
                }

                if (a instanceof SpacemanObject && (b instanceof Earth)) ((SpacemanObject) a).setJump(true);
                else if (b instanceof SpacemanObject && (a instanceof Earth)) ((SpacemanObject) b).setJump(true);
            }

            private boolean isLeftContact(Fixture playerFixture, Fixture blockFixture, Contact contact) {
                return true;
            }

            private boolean isRightContact(Fixture playerFixture, Fixture blockFixture, Contact contact) {
                return true;
            }

            public boolean isDownContact(Fixture playerFixture, Fixture blockFixture, Contact contact, boolean isFar) {
                if (playerFixture.getBody().getPosition().y - blockFixture.getBody().getPosition().y <= (BLOCK_SIZE + COSMONAUT_HEIGHT) / (2f) * SCALE
                        && Math.abs(playerFixture.getBody().getPosition().x - blockFixture.getBody().getPosition().x) < BLOCK_SIZE / (2f) * SCALE) {
                    return true;
                }
                return false;
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
