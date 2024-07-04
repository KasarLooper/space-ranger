package com.mygdx.game.screens;

import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static com.mygdx.game.State.ENDED;
import static com.mygdx.game.State.PAUSED;
import static com.mygdx.game.State.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.JoystickView;
import com.mygdx.game.components.TextView;

public abstract class GameScreen extends ScreenAdapter implements InputProcessor {
    GameSession gameSession;
    MyGdxGame myGdxGame;
    JoystickView joystick;
    long showTime;
    State state;
    ButtonView pauseButton, endButton;


    public GameScreen(MyGdxGame game) {
        this.myGdxGame = game;
        pauseButton = new ButtonView(1200, 650, 50, 50, GameResources.PAUSE_ICON_IMG_PATH); // "pause_icon.png"
        endButton = new ButtonView(430, 446, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Surrender!");
    }

    @Override
    public void show() {
        joystick = new JoystickView(100, 100);
        showTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        drawDynamic();
        myGdxGame.batch.end();

        myGdxGame.batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, GameSettings.SCREEN_WIDTH, SCREEN_HEIGHT));
        myGdxGame.batch.begin();
        drawStatic();
        myGdxGame.batch.end();
    }

    public abstract void onPause();

    protected void drawStatic() {
        joystick.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
        if (gameSession.state == PAUSED) {
            endButton.draw(myGdxGame.batch);
        }
    }

    protected void drawDynamic() {
    }

    protected void moveCamera(Vector2 move) {
        myGdxGame.camera.position.x += move.x;
        myGdxGame.camera.position.y += move.y;
    }

    @Override
    public void dispose() {
        super.dispose();
        joystick.dispose();
        pauseButton.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && gameSession.state == PLAYING) joystick.onTouch(screenX, SCREEN_HEIGHT - screenY);
        if (pauseButton.isHit(screenX, SCREEN_HEIGHT - screenY)) onPause();
        if (gameSession.state == PAUSED && endButton.isHit(screenX, SCREEN_HEIGHT - screenY)) {
            gameSession.state = ENDED;
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && gameSession.state == PLAYING)
            joystick.toDefault();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && gameSession.state == PLAYING) joystick.onTouch(screenX, SCREEN_HEIGHT - screenY);
        else if (gameSession.state == PLAYING)
            joystick.toDefault();
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    enum State {
        play,
        pause
    }
}