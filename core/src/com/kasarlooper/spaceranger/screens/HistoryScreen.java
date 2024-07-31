package com.kasarlooper.spaceranger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GameSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.components.MiddleButtonView;
import com.kasarlooper.spaceranger.components.MiddleTextView;
import com.kasarlooper.spaceranger.manager.MemoryManager;

public class HistoryScreen extends ScreenAdapter {
    private Screen nextScreen;
    private MyGdxGame game;
    private MiddleButtonView button;
    private MiddleTextView text;
    private String[] texts;
    private Texture photo;
    int i;

    public HistoryScreen(MyGdxGame game, String[] texts, Screen nextScreen, boolean isEnd) {
        this.game = game;
        this.texts = texts;
        this.nextScreen = nextScreen;
        button = new MiddleButtonView(50, 440, 70, game.commonWhiteFont, GameResources.BUTTON_IMG_PATH, "Дальше");
        text = new MiddleTextView(game.averageWhiteFont, texts[0]);
        if (isEnd) photo = new Texture(GameResources.BACKGROUND_END_IMG_PATH);
    }

    @Override
    public void show() {
        i = 0;
        text.setText(texts[i]);
    }

    @Override
    public void render(float delta) {

        handleInput();

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        game.batch.begin();
        if (i < texts.length) text.draw(game.batch);
        else if (photo != null)
            game.batch.draw(photo, 0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        button.draw(game.batch);
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            game.touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (button.isHit(game.touch.x, game.touch.y)) {
                i++;
                if (i < texts.length) {
                    text.setText(texts[i]);
                } else if (i > texts.length || photo == null) {
                    if (nextScreen instanceof SpaceGameScreen) game.spaceLevel();
                    else if (nextScreen instanceof PlanetGameScreen) game.planetLevel();
                    else {
                        game.setScreen(nextScreen);
                        MemoryManager.saveLevel(1);
                    }
                }
            }
        }
    }
    @Override
    public void dispose() {
        button.dispose();
        text.dispose();
        photo.dispose();
    }
}
