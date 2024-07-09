package com.mygdx.game.screens;

import static com.mygdx.game.GameResources.ALIEN_ANIM_RIGHT_IMG_PATTERN;
import static com.mygdx.game.GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN;
import static com.mygdx.game.GameSettings.ALIEN_HEIGHT;
import static com.mygdx.game.GameSettings.ALIEN_JUMP_FORCE;
import static com.mygdx.game.GameSettings.ALIEN_SPEED;
import static com.mygdx.game.GameSettings.ALIEN_WIDTH;
import static com.mygdx.game.GameSettings.CAMERA_Y_FROM_CENTER;
import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;
import static com.mygdx.game.GameSettings.COSMONAUT_JUMP_FORCE;
import static com.mygdx.game.GameSettings.COSMONAUT_SPEED;
import static com.mygdx.game.GameSettings.COSMONAUT_WIDTH;
import static com.mygdx.game.GameSettings.GROUND_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static com.mygdx.game.State.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameResources;
import com.mygdx.game.GraphicsSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.MovingBackgroundLeftRightView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.manager.LevelMapManager;
import com.mygdx.game.objects.AlienObject;
import com.mygdx.game.objects.Block;
import com.mygdx.game.objects.Earth;
import com.mygdx.game.objects.SpacemanObject;

public class PlanetGameScreen extends GameScreen {

    MovingBackgroundView backgroundView;

    LevelMapManager loader;
    SpacemanObject spaceman;
    AlienObject alien;
    Earth earth;
    Block[] blocks;

    ButtonView jumpButton;
    boolean isJump;
    private int padding = 0;

    public PlanetGameScreen(MyGdxGame game) {
        super(game);
        loader = new LevelMapManager();
        blocks = loader.loadMap();
        backgroundView = new MovingBackgroundLeftRightView(GameResources.BACKGROUND_2_IMG_PATH, GraphicsSettings.DEPTH_PLANET_BACKGROUND_SPEED_RATIO);
        spaceman = new SpacemanObject(
                0, GROUND_HEIGHT + COSMONAUT_HEIGHT / 2 + padding,
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                COSMONAUT_ANIM_RIGHT_IMG_PATTERN, 4,
                COSMONAUT_SPEED, COSMONAUT_JUMP_FORCE,
                myGdxGame.planet);
        alien = new AlienObject(
                 -300, GROUND_HEIGHT + COSMONAUT_HEIGHT / 2 + padding,
                ALIEN_WIDTH, ALIEN_HEIGHT, ALIEN_ANIM_RIGHT_IMG_PATTERN, 5,
                ALIEN_SPEED, ALIEN_JUMP_FORCE,
                myGdxGame.planet);
        earth = new Earth(GROUND_HEIGHT, myGdxGame.planet);
        jumpButton = new ButtonView(1150, 25, 100, 100, GameResources.JUMP_BUTTON_IMG_PATH);
        isJump = false;
    }

    @Override
    public void render(float delta) {
        myGdxGame.camera.position.x = spaceman.getX();
        myGdxGame.camera.position.y = spaceman.getY() + GROUND_HEIGHT + CAMERA_Y_FROM_CENTER;
        super.render(delta);
        if (gameSession.state == com.mygdx.game.State.PLAYING) {
            backgroundView.move(spaceman.getX(), spaceman.getY());
            if (isJump)
                spaceman.jump();
            spaceman.updateFrames();
            alien.move(spaceman.getX(), spaceman.getY(), blocks);
            alien.updateFrames();
            myGdxGame.stepWorld(myGdxGame.planet);
            spaceman.updateJump();
            alien.updateJump();
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void drawDynamic() {
        backgroundView.draw(myGdxGame.batch);
        spaceman.draw(myGdxGame.batch);
        alien.draw(myGdxGame.batch);
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
                0, GROUND_HEIGHT + COSMONAUT_HEIGHT / 2 + padding,
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                COSMONAUT_ANIM_RIGHT_IMG_PATTERN, 4,
                COSMONAUT_SPEED, COSMONAUT_JUMP_FORCE,
                myGdxGame.planet);
        alien = new AlienObject(
                -300, GROUND_HEIGHT + COSMONAUT_HEIGHT / 2 + padding,
                ALIEN_WIDTH, ALIEN_HEIGHT, ALIEN_ANIM_RIGHT_IMG_PATTERN, 5,
                ALIEN_SPEED, ALIEN_JUMP_FORCE,
                myGdxGame.planet);
    }

    @Override
    public void dispose() {
        super.dispose();
        spaceman.dispose();
        alien.dispose();
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
        if (screenX > SCREEN_WIDTH / 2 && gameSession.state == PLAYING) {
            if (!pauseButton.isHit(screenX, Gdx.graphics.getHeight() - screenY) && !jumpButton.isHit(screenX, Gdx.graphics.getHeight() - screenY)) {
                isJump = false;
            }
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            joystick.left();
            spaceman.stepLeft();
        }
        else if (keycode == Input.Keys.D) {
            joystick.right();
            spaceman.stepRight();
        }
        if (keycode == Input.Keys.SPACE)
            isJump = true;
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
            joystick.toDefault();
            spaceman.stop();
        }
        if (keycode == Input.Keys.SPACE)
            isJump = false;
        return super.keyUp(keycode);
    }
}
