package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.components.TextView;
import com.mygdx.game.manager.MemoryManager;

public class MemoriesScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView title;
    ButtonView space;
    ButtonView planet;
    ButtonView back;
    boolean isSpaceOpen, isPlanetOpen;

    public MemoriesScreen(MyGdxGame game) {
        this.myGdxGame = game;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        title = new TextView(myGdxGame.commonWhiteFont, 450, 550, "Воспоминания");
        space = new ButtonView(430, 376, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Закрыто");
        planet = new ButtonView(430, 256, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Закрыто");
        back = new ButtonView(430, 36, 440, 70, myGdxGame.averageWhiteFont, GameResources.BUTTON_IMG_PATH, "Назад");
        isSpaceOpen = false;
        isPlanetOpen = false;
    }

    @Override
    public void show() {
        super.show();
        if (MemoryManager.loadIsPassedSpace()) {
            space.setText("Битва в космосе");
            isSpaceOpen = true;
        }
        if (MemoryManager.loadIsPassedPlanet()) {
            planet.setText("Крушение на планете");
            isPlanetOpen = true;
        }
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        title.draw(myGdxGame.batch);
        space.draw(myGdxGame.batch);
        planet.draw(myGdxGame.batch);
        back.draw(myGdxGame.batch);

        myGdxGame.batch.end();


    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));


            if (space.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && isSpaceOpen) {
                myGdxGame.setScreen(myGdxGame.spaceHistory);
            }
            if (planet.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && isPlanetOpen) {
                myGdxGame.setScreen(myGdxGame.planetHistory);
            }

            if (back.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        }
    }
}
