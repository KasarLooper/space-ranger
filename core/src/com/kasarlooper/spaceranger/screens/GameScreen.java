package com.kasarlooper.spaceranger.screens;

import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;
import static com.kasarlooper.spaceranger.State.ENDED;
import static com.kasarlooper.spaceranger.State.PAUSED;
import static com.kasarlooper.spaceranger.State.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.manager.MemoryManager;
import com.kasarlooper.spaceranger.session.GameSession;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.components.ButtonView;
import com.kasarlooper.spaceranger.components.JoystickView;
import com.kasarlooper.spaceranger.components.MovingBackgroundView;

public abstract class GameScreen extends ScreenAdapter implements InputProcessor {
    protected GameSession session;
    MyGdxGame myGdxGame;
    JoystickView joystick;
    long showTime;
    ButtonView pauseButton, endButton, continueButton, restartButton;
    MovingBackgroundView black_out_on_pause;

    private boolean isReload = false;
    private float camX;
    private float camY;


    public GameScreen(MyGdxGame game) {
        this.myGdxGame = game;
        pauseButton = new ButtonView(1200, 650, 50, 50, GameResources.PAUSE_ICON_IMG_PATH); // "pause_icon.png"
        endButton = new ButtonView(430, 516, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Меню");
        continueButton = new ButtonView(430, 406, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Продолжить");
        restartButton = new ButtonView(430, 416, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Заново");
        black_out_on_pause = new MovingBackgroundView(GameResources.BLACKOUT_IMG_PATH);
    }

    @Override
    public void show() {
        joystick = new JoystickView(25, 25);
        showTime = TimeUtils.millis();
        if (isReload) {
            myGdxGame.camera.position.x = camX;
            myGdxGame.camera.position.y = camY;
        }
        showTime = TimeUtils.millis();
        restartGame();
    }

    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        ScreenUtils.clear(108f / 255f, 123f / 255f, 188f / 255f, 0);

        if (session.victory()) {
            restartButton = new ButtonView(430, 416, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Далее");
        }

        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        drawDynamic();
        myGdxGame.batch.end();

        myGdxGame.batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, GameSettings.SCREEN_WIDTH, SCREEN_HEIGHT));
        myGdxGame.batch.begin();
        drawStatic();
        myGdxGame.batch.end();
    }

    public void onPause() {
        switch (session.state) {
            case PLAYING:
                session.pauseGame();
                break;

            case PAUSED:
                session.resumeGame();
                break;
        }
    }

    protected void drawStatic() {
        joystick.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
        if (session.state == null) {
            session.startGame();
        }
        switch (session.state) {
            case PLAYING:
                break;
            case PAUSED:
                black_out_on_pause.draw(myGdxGame.batch);
                endButton.draw(myGdxGame.batch);
                continueButton.draw(myGdxGame.batch);
                break;
            case ENDED:
                black_out_on_pause.draw(myGdxGame.batch);
                restartButton.draw(myGdxGame.batch);
                endButton.draw(myGdxGame.batch);
                break;
        }
    }

    protected void drawDynamic() {
        //debugRenderer.render(myGdxGame.planet, myGdxGame.camera.combined);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (joystick != null) joystick.dispose();
        if (pauseButton != null) pauseButton.dispose();
        if (endButton != null) endButton.dispose();
        if (continueButton != null) continueButton.dispose();
        if (restartButton != null) restartButton.dispose();
        if (black_out_on_pause != null) black_out_on_pause.dispose();
    }


    public void restartGame() {
        session.startGame();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && session.state == PLAYING) joystick.onTouch(screenX, SCREEN_HEIGHT - screenY);
        if (pauseButton.isHit(screenX, SCREEN_HEIGHT - screenY))
            onPause();
        if ((session.state == PAUSED || session.state == ENDED) && endButton.isHit(screenX, SCREEN_HEIGHT - screenY)) {
            session.state = ENDED;
            myGdxGame.setScreen(myGdxGame.menuScreen);
            myGdxGame.audioManager.spaceMusic.stop();
            isReload = true;
            camX = myGdxGame.camera.position.x;
            camY = myGdxGame.camera.position.y;
            myGdxGame.camera.position.x = myGdxGame.camera.viewportWidth / 2;
            myGdxGame.camera.position.y = myGdxGame.camera.viewportHeight / 2;
            myGdxGame.mainMenuMusic();
        }
        if (session.state == ENDED && restartButton.isHit(screenX, SCREEN_HEIGHT - screenY)) {
            isReload = true;
            camX = myGdxGame.camera.position.x;
            camY = myGdxGame.camera.position.y;
            myGdxGame.camera.position.x = myGdxGame.camera.viewportWidth / 2;
            myGdxGame.camera.position.y = myGdxGame.camera.viewportHeight / 2;
            if (myGdxGame.isContinue && session.victory()) {
                if (this instanceof SpaceGameScreen) {
                    myGdxGame.setScreen(myGdxGame.planetHistory);
                } else {
                    myGdxGame.setScreen(myGdxGame.endHistory);
                }
                MemoryManager.saveIsFirstLevel(myGdxGame.isFirstLevel);
                myGdxGame.mainMenuMusic();
            } else {
                if (this instanceof SpaceGameScreen)
                    myGdxGame.spaceLevel();
                else if (this instanceof PlanetGameScreen)
                    myGdxGame.planetLevel();
            }
            restartGame();
        }
        if ((session.state == PAUSED) && continueButton.isHit(screenX, SCREEN_HEIGHT - screenY)) {
            session.state = PLAYING;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && session.state == PLAYING)
            joystick.toDefault();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && session.state == PLAYING) joystick.onTouch(screenX, SCREEN_HEIGHT - screenY);
        else if (session.state == PLAYING)
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

    public abstract void win();

    public abstract void lose();

    enum State {
    }
}