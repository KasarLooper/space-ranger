package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.JoystickView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.manager.ContactManager;
import com.mygdx.game.objects.ShipObject;

public class SpaceGameScreen extends GameScreen {
    MyGdxGame myGdxGame;
    public ShipObject shipObject;
    ContactManager contactManager;
    MovingBackgroundView backgroundView;



    public SpaceGameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        contactManager = new ContactManager(myGdxGame.world);
        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );


    }
    @Override
    public void render(float delta) {
        super.render(delta);
//        backgroundView.move();
    }

    @Override
    protected void draw() {
        super.draw();
        backgroundView.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
    }
}
