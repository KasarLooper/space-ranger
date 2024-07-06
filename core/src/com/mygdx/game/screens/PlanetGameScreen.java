package com.mygdx.game.screens;

import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.objects.ShipObject;

public class PlanetGameScreen extends GameScreen {

    MovingBackgroundView backgroundView;

    ShipObject shipObject;



    public PlanetGameScreen(MyGdxGame game) {
        super(game);
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_2_IMG_PATH);
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
        myGdxGame.camera.position.x = shipObject.getX();
        myGdxGame.camera.position.y = shipObject.getY();
        backgroundView.move(shipObject.getX(), shipObject.getY());
        myGdxGame.stepWorld();
        if (joystick.isTouched()) {
            shipObject.setRotation(joystick.getDegrees());
            shipObject.move();
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void drawDynamic() {
        backgroundView.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        super.drawDynamic();
    }

    @Override
    public void restartGame() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }
}
