package com.mygdx.game.screens;

import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;
import static com.mygdx.game.GameSettings.COSMONAUT_WIDTH;
import static com.mygdx.game.GameSettings.GROUND_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static com.mygdx.game.State.PLAYING;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.objects.Block;
import com.mygdx.game.objects.Earth;
import com.mygdx.game.objects.SpacemanObject;

public class PlanetGameScreen extends GameScreen {

    MovingBackgroundView backgroundView;

    SpacemanObject spaceman;
    Earth earth;
    Block block;

    ButtonView jumpButton;
    boolean isJump;

    public PlanetGameScreen(MyGdxGame game) {
        super(game);
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_2_IMG_PATH);
        spaceman = new SpacemanObject(
                0, GROUND_HEIGHT + COSMONAUT_HEIGHT / 2,
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                String.format(GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN, 4),
                myGdxGame.planet
        );
        earth = new Earth(GROUND_HEIGHT, myGdxGame.planet);
        block = new Block(100, 200, 100, 100, GameResources.BOOM_IMG_PATH, myGdxGame.planet);
        jumpButton = new ButtonView(1150, 25, 100, 100, GameResources.JUMP_BUTTON_IMG_PATH);
        isJump = false;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (gameSession.state == com.mygdx.game.State.PLAYING) {
            myGdxGame.camera.position.x = spaceman.getX();
            myGdxGame.camera.position.y = spaceman.getY();
            backgroundView.move(spaceman.getX(), spaceman.getY());
            if (isJump)
                spaceman.jump();
            spaceman.updateFrames();
            myGdxGame.stepWorld(myGdxGame.planet);
            spaceman.updateJump();
        }
        System.out.println(spaceman.getX() + " " + spaceman.getY());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void drawDynamic() {
        backgroundView.draw(myGdxGame.batch);
        spaceman.draw(myGdxGame.batch);
        block.draw(myGdxGame.batch);
        super.drawDynamic();
    }

    @Override
    public void drawStatic() {
        super.drawStatic();
        jumpButton.draw(myGdxGame.batch);
    }

    @Override
    public void restartGame() {
        super.restartGame();
        myGdxGame.planet.destroyBody(spaceman.body);
        spaceman = new SpacemanObject(
                0, GROUND_HEIGHT + COSMONAUT_HEIGHT / 2 + 100,
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                String.format(GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN, 4),
                myGdxGame.planet);
    }

    @Override
    public void dispose() {
        super.dispose();
        spaceman.dispose();
        jumpButton.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && gameSession.state == PLAYING) {
            if (joystick.getDegrees() % 360 > 0 && joystick.getDegrees() % 360 <= 180)
                spaceman.stepLeft();
            else spaceman.stepRight();
        }
        if (jumpButton.isHit(screenX, Gdx.graphics.getHeight() - screenY))
            isJump = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && gameSession.state == PLAYING) spaceman.stop();
        if (jumpButton.isHit(screenX, Gdx.graphics.getHeight() - screenY))
            isJump = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && gameSession.state == PLAYING) {
            if (joystick.getDegrees() % 360 > 0 && joystick.getDegrees() % 360 <= 180)
                spaceman.stepLeft();
            else spaceman.stepRight();
        }
        return true;
    }
}
