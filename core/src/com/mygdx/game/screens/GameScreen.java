package com.mygdx.game.screens;

import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;

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
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.JoystickView;

public abstract class GameScreen extends ScreenAdapter implements InputProcessor {
    MyGdxGame myGdxGame;
    JoystickView joystick;
    long showTime;
    State state;
    ButtonView pauseButton;

    public GameScreen(MyGdxGame game) {
        this.myGdxGame = game;
        pauseButton = new ButtonView(1200, 650, 50, 50, GameResources.PAUSE_ICON_IMG_PATH); // "pause_icon.png"
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

    protected void drawStatic() {
        joystick.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
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
        joystick.onTouch(screenX, SCREEN_HEIGHT - screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        joystick.toDefault();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        joystick.onTouch(screenX, SCREEN_HEIGHT - screenY);
        return false;
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