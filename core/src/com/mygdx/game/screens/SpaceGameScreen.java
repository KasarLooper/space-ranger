package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.manager.ContactManager;
import com.mygdx.game.objects.ShipObject;

public class SpaceGameScreen extends GameScreen {
    MyGdxGame myGdxGame;
    public ShipObject shipObject;
    ContactManager contactManager;

    public SpaceGameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        this.myGdxGame = myGdxGame;
        contactManager = new ContactManager(myGdxGame.world);
        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT / 2,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );

    }
    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    protected void draw() {
        super.draw();
        shipObject.draw(myGdxGame.batch);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isTouched()) {
            Vector3 touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.setRotation(joystick.getDegrees() + 270f);
        }
    }
}
