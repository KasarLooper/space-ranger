package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.JoystickView;

public abstract class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    JoystickView joystick;

    public GameScreen(MyGdxGame game) {
        this.myGdxGame = game;
    }

    @Override
    public void show() {
        joystick = new JoystickView(100, 100);
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        draw();
        myGdxGame.batch.end();
    }

    protected void draw() {
        joystick.draw(myGdxGame.batch);
    }

    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            joystick.onTouch((int) touch.x, (int) touch.y);
        } else {
            joystick.toDefault();
        }
    }
}
