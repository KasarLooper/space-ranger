package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.MiddleButtonView;
import com.mygdx.game.components.MiddleTextView;

public class HistoryScreen extends ScreenAdapter {
    private Screen nextScreen;
    private MyGdxGame game;
    private MiddleButtonView button;
    private MiddleTextView text;
    private String[] texts;
    int i;

    public HistoryScreen(MyGdxGame game, String[] texts, Screen nextScreen) {
        this.game = game;
        this.texts = texts;
        this.nextScreen = nextScreen;
        i = 0;
        button = new MiddleButtonView(50, 440, 70, game.commonWhiteFont, GameResources.BUTTON_IMG_PATH, "Дальше");
        text = new MiddleTextView(game.averageWhiteFont, texts[0]);
    }

    @Override
    public void render(float delta) {

        handleInput();

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        game.batch.begin();
        button.draw(game.batch);
        text.draw(game.batch);
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            game.touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (button.isHit(game.touch.x, game.touch.y)) {
                i++;
                if (i >= texts.length) {
                    if (nextScreen instanceof SpaceGameScreen) {
                        game.spaceLevel();
                        game.showSpaceIntro();
                    }
                    else if (nextScreen instanceof PlanetGameScreen) {
                        game.planetLevel();
                        game.showPlanetIntro();
                    }
                }
                else text.setText(texts[i]);
            }
        }
    }
    @Override
    public void dispose() {
        button.dispose();
        text.dispose();
    }
}
