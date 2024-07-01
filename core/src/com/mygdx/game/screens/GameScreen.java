package com.mygdx.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.MyGdxGame;

public abstract class GameScreen extends ScreenAdapter {
    MyGdxGame game;

    public GameScreen(MyGdxGame game) {
        this.game = game;
    }
}
