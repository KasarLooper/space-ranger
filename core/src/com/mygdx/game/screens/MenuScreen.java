package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GraphicsSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.components.TextView;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView titleView;
    ButtonView startButtonView;
    ButtonView memoryButton;
    ButtonView exitButtonView;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_MENU_IMG_PATH);
        titleView = new TextView(myGdxGame.commonWhiteFont, 465, 550, "Space ranger");
        startButtonView = new ButtonView(430, 396, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Играть");
        memoryButton = new ButtonView(430, 276, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Воспоминания");
        exitButtonView = new ButtonView(430, 156, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Выход");
    }
    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleView.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);
        memoryButton.draw(myGdxGame.batch);
        startButtonView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.isContinue = true;
                if (myGdxGame.isFirstLevel) myGdxGame.setScreen(myGdxGame.spaceHistory);
                else myGdxGame.setScreen(myGdxGame.planetHistory);
            }
            if (memoryButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.isContinue = false;
                myGdxGame.setScreen(myGdxGame.memoriesScreen);
            }

            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                Gdx.app.exit();
            }
        }
    }
    @Override
    public void dispose() {
        backgroundView.dispose();
        titleView.dispose();
        startButtonView.dispose();
        exitButtonView.dispose();
    }
}
