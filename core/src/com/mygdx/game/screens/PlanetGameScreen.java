package com.mygdx.game.screens;

import static com.mygdx.game.GameSettings.CORE_HEIGHT;
import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;
import static com.mygdx.game.GameSettings.COSMONAUT_WIDTH;

import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.objects.Earth;
import com.mygdx.game.objects.SpacemanObject;

public class PlanetGameScreen extends GameScreen {

    MovingBackgroundView backgroundView;

    SpacemanObject spaceman;
    Earth earth;

    public PlanetGameScreen(MyGdxGame game) {
        super(game);
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_2_IMG_PATH);
        spaceman = new SpacemanObject(
                0, 0,
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                GameResources.COSMONAUT_ANIM_LEFT_1,
                myGdxGame.planet
        );
        earth = new Earth(
                -100, -100,
                163, 1216,
                GameResources.EARTH_IMG_PATH,
                myGdxGame.planet
        );

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (gameSession.state == com.mygdx.game.State.PLAYING) {
            myGdxGame.camera.position.x = spaceman.getX();
            myGdxGame.camera.position.y = spaceman.getY();
            backgroundView.move(spaceman.getX(), spaceman.getY());
            myGdxGame.stepWorld(myGdxGame.planet);
        }
//        if (joystick.isTouched()) {
//            spaceman.setRotation(joystick.getDegrees());
//            spaceman.move();
//        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void drawDynamic() {
        backgroundView.draw(myGdxGame.batch);
        spaceman.draw(myGdxGame.batch);
        earth.draw(myGdxGame.batch);
        super.drawDynamic();
    }

    @Override
    public void drawStatic() {
        //
    }

    @Override
    public void restartGame() {
        super.restartGame();
        myGdxGame.planet.destroyBody(spaceman.body);
        spaceman = new SpacemanObject(
                0, 0,
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                GameResources.COSMONAUT_ANIM_LEFT_1,
                myGdxGame.planet
        );
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
