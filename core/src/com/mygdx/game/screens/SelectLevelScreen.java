package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.components.TextView;


public class SelectLevelScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView titleView;
    ButtonView spaceLevelButton, planetLevelButton, returnButton;
    ImageView blocked, swords;

    public SelectLevelScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(myGdxGame.commonWhiteFont, 465, 550, "Choose Level");
        spaceLevelButton = new ButtonView(430, 276, 440, 70, myGdxGame.commonWhiteFont, GameResources.BUTTON_IMG_PATH, "Space Level");
        planetLevelButton = new ButtonView(430, 176, 440, 70, myGdxGame.commonWhiteFont, GameResources.BUTTON_IMG_PATH, "Planet Level");
        returnButton = new ButtonView(430, 76, 440, 70, myGdxGame.commonWhiteFont, GameResources.BUTTON_IMG_PATH, "Go back");
        blocked = new ImageView(430, 176, GameResources.MIDDLE_BLACKOUT_IMG_PATH);
        swords = new ImageView(520, 380, GameResources.SWORDS_IMG_PATH);
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

        spaceLevelButton.draw(myGdxGame.batch);
        planetLevelButton.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        if (!myGdxGame.canAccessPlanetLevel)
            blocked.draw(myGdxGame.batch);
        swords.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (spaceLevelButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.spaceScreen);
                Gdx.input.setInputProcessor(myGdxGame.spaceScreen);
            }
            else if (planetLevelButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && myGdxGame.canAccessPlanetLevel) {
                myGdxGame.setScreen(myGdxGame.planetScreen);
                Gdx.input.setInputProcessor(myGdxGame.planetScreen);
            }
            else if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        }
    }
    @Override
    public void dispose() {
        backgroundView.dispose();
        titleView.dispose();
        spaceLevelButton.dispose();
        planetLevelButton.dispose();
        returnButton.dispose();
    }
}
