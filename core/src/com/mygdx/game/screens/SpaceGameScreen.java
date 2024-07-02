package com.mygdx.game.screens;

import static com.mygdx.game.GameSettings.BULLET_HEIGHT;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.manager.ContactManager;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.ShipObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SpaceGameScreen extends GameScreen {
    MyGdxGame myGdxGame;

    // Objects
    public ShipObject shipObject;
    ArrayList<BulletObject> bulletArray;
    ContactManager contactManager;
    MovingBackgroundView backgroundView;

    public SpaceGameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        contactManager = new ContactManager(myGdxGame.world);
        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT / 2,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
        bulletArray = new ArrayList<>();
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        final int padding = 50;
        if (shipObject.needToShoot()) {
            BulletObject Bullet = new BulletObject(
                    (int) (shipObject.getX() + cos(toRadians(shipObject.getRotation())) * (shipObject.getRadius() / 2 + BULLET_HEIGHT + padding)),
                    (int) (shipObject.getY() + sin(toRadians(shipObject.getRotation())) * (shipObject.getRadius() / 2 + BULLET_HEIGHT + padding)),
                    GameSettings.BULLET_WIDTH, BULLET_HEIGHT,
                    GameResources.BULLET_IMG_PATH,
                    myGdxGame.world, shipObject.getRotation()
            );
            bulletArray.add(Bullet);
        }
        myGdxGame.stepWorld();
        updateBullets();
        backgroundView.move();
    }

    @Override
    protected void draw() {
        backgroundView.draw(myGdxGame.batch);
        super.draw();
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) {
            //bullet.setRotation(shipObject.getRotation());
            bullet.draw(myGdxGame.batch);
        }
    }

    @Override
    protected void moveCamera(Vector2 move) {
        super.moveCamera(move);
        joystick.onCameraUpdate(move.x, move.y);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (TimeUtils.millis() - showTime < 100) return;
        if (Gdx.input.isTouched()) {
            //myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.setRotation(joystick.getDegrees());
            Vector2 difference = shipObject.move();
            moveCamera(difference);
        }
    }

    private void updateBullets() {
        // For Ship
        Iterator<BulletObject> iterator = bulletArray.iterator();
        while(iterator.hasNext()) {
            BulletObject bulletObject_now = iterator.next();
            if (bulletObject_now.destroy(shipObject.getX(), shipObject.getY())) {
                myGdxGame.world.destroyBody(bulletObject_now.body);
                iterator.remove();
            }
        }
    }
}
