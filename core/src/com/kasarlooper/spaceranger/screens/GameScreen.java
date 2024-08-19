package com.kasarlooper.spaceranger.screens;

import static com.kasarlooper.spaceranger.GameSettings.SCALE;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;
import static com.kasarlooper.spaceranger.State.ENDED;
import static com.kasarlooper.spaceranger.State.PAUSED;
import static com.kasarlooper.spaceranger.State.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.components.ButtonView;
import com.kasarlooper.spaceranger.components.JoystickView;
import com.kasarlooper.spaceranger.components.MovingBackgroundView;
import com.kasarlooper.spaceranger.levels.space.SpaceGameScreen;
import com.kasarlooper.spaceranger.manager.AudioManager;
import com.kasarlooper.spaceranger.physics.WorldWrap;
import com.kasarlooper.spaceranger.session.GameSession;

public abstract class GameScreen extends ScreenAdapter implements InputProcessor {
    protected WorldWrap world;
    protected GameSession session;
    protected MyGdxGame myGdxGame;
    protected JoystickView joystick;
    long showTime;
    ButtonView pauseButton, endButton, continueButton, restartButton, nextButton;
    MovingBackgroundView black_out_on_pause;

    private boolean isReload = false;
    private float camX;
    private float camY;

    private boolean isCollidersShown;
    private boolean isGraphicsShown;


    public GameScreen(MyGdxGame game) {
        this.myGdxGame = game;
        pauseButton = new ButtonView(1200, 650, 50, 50, GameResources.PAUSE_ICON_IMG_PATH); // "pause_icon.png"
        endButton = new ButtonView(430, 516, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Меню");
        continueButton = new ButtonView(430, 406, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Продолжить");
        restartButton = new ButtonView(430, 416, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Заново");
        nextButton = new ButtonView(430, 416, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Далее");
        black_out_on_pause = new MovingBackgroundView(GameResources.BLACKOUT_IMG_PATH);

        isCollidersShown = true;
        isGraphicsShown = true;
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
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        ScreenUtils.clear(108f / 255f, 123f / 255f, 188f / 255f, 0);

        if (isGraphicsShown) {
            myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
            myGdxGame.batch.begin();
            drawDynamic();
            myGdxGame.batch.end();
        }

        if (isCollidersShown) {
            world.render(new Matrix4().setToOrtho2D((getCenterX() - SCREEN_WIDTH / 2f) * SCALE,
                    (getCenterY() - SCREEN_HEIGHT / 2f) * SCALE,
                    GameSettings.SCREEN_WIDTH * SCALE, SCREEN_HEIGHT * SCALE));
        }

        myGdxGame.batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, GameSettings.SCREEN_WIDTH, SCREEN_HEIGHT));
        myGdxGame.batch.begin();
        drawStatic();
        myGdxGame.batch.end();
    }

    @Override
    public void pause() {
        if (session.state == PLAYING)
            session.pauseGame();

    }

    protected abstract float getCenterX();

    protected abstract float getCenterY();

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
                endButton.draw(myGdxGame.batch);
                if (session.victory() && myGdxGame.isInPlot) nextButton.draw(myGdxGame.batch);
                else restartButton.draw(myGdxGame.batch);
                break;
        }
    }

    protected void drawDynamic() {
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
        joystick.toDefault();
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
            AudioManager.spaceMusic.stop();
            isReload = true;
            camX = myGdxGame.camera.position.x;
            camY = myGdxGame.camera.position.y;
            myGdxGame.camera.position.x = myGdxGame.camera.viewportWidth / 2;
            myGdxGame.camera.position.y = myGdxGame.camera.viewportHeight / 2;
            myGdxGame.mainMenuMusic();
        }
        if (session.state == ENDED && session.victory() && myGdxGame.isInPlot && nextButton.isHit(screenX, SCREEN_HEIGHT - screenY)) {
            isReload = true;
            camX = myGdxGame.camera.position.x;
            camY = myGdxGame.camera.position.y;
            myGdxGame.camera.position.x = myGdxGame.camera.viewportWidth / 2;
            myGdxGame.camera.position.y = myGdxGame.camera.viewportHeight / 2;

            myGdxGame.mainMenuMusic();
            if (this instanceof SpaceGameScreen) myGdxGame.setScreen(myGdxGame.planetHistory);
            else myGdxGame.setScreen(myGdxGame.endHistory);
        }
        if (session.state == ENDED && !(session.victory() && myGdxGame.isInPlot) && restartButton.isHit(screenX, SCREEN_HEIGHT - screenY)) {
            isReload = true;
            camX = myGdxGame.camera.position.x;
            camY = myGdxGame.camera.position.y;
            myGdxGame.camera.position.x = myGdxGame.camera.viewportWidth / 2;
            myGdxGame.camera.position.y = myGdxGame.camera.viewportHeight / 2;

            restartGame();
        }
        if ((session.state == PAUSED) && continueButton.isHit(screenX, SCREEN_HEIGHT - screenY)) {
            session.state = PLAYING;
        }
        return true;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
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
        if (keycode == Input.Keys.E)
            isCollidersShown = !isCollidersShown;
        else if (keycode == Input.Keys.R) {
            isGraphicsShown = !isGraphicsShown;
        }
        return true;
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