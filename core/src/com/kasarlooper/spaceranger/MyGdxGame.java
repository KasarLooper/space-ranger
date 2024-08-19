package com.kasarlooper.spaceranger;

import static com.kasarlooper.spaceranger.GraphicsSettings.END_HISTORY_ARRAY;
import static com.kasarlooper.spaceranger.GraphicsSettings.PLANET_HISTORY_ARRAY;
import static com.kasarlooper.spaceranger.GraphicsSettings.SPACE_HISTORY_ARRAY;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.kasarlooper.spaceranger.levels.LevelManager;
import com.kasarlooper.spaceranger.manager.AudioManager;
import com.kasarlooper.spaceranger.manager.LevelMapManager;
import com.kasarlooper.spaceranger.manager.MemoryManager;
import com.kasarlooper.spaceranger.screens.GameScreen;
import com.kasarlooper.spaceranger.screens.HistoryScreen;
import com.kasarlooper.spaceranger.screens.MemoriesScreen;
import com.kasarlooper.spaceranger.screens.MenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public Vector3 touch;
	public OrthographicCamera camera;
	public GameScreen spaceLevel, planetLevel;
	public MenuScreen menuScreen;
	public MemoriesScreen memoriesScreen;
	public BitmapFont commonWhiteFont;
	public BitmapFont averageWhiteFont;
	public LevelMapManager levelMapManager;
	public HistoryScreen spaceHistory;
	public HistoryScreen planetHistory;
	public HistoryScreen endHistory;
	public boolean isInPlot;

	@Override
	public void create () {
		Box2D.init();

		commonWhiteFont = FontBuilder.generate(50, Color.WHITE, GameResources.FONT_PATH);
		averageWhiteFont = FontBuilder.generate(25, Color.WHITE, GameResources.FONT_PATH);

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

		spaceLevel = LevelManager.getSpaceLevel(this);
		planetLevel = LevelManager.getPlanetLevel(this);

		menuScreen = new MenuScreen(this);
		memoriesScreen = new MemoriesScreen(this);
		spaceHistory = new HistoryScreen(this, SPACE_HISTORY_ARRAY, spaceLevel, false);
		planetHistory = new HistoryScreen(this, PLANET_HISTORY_ARRAY, planetLevel, false);
		endHistory = new HistoryScreen(this, END_HISTORY_ARRAY, menuScreen, true);
		setScreen(menuScreen);

		levelMapManager = new LevelMapManager();
	}

	public boolean win() {
		if (getScreen() instanceof GameScreen) {
			((GameScreen) getScreen()).win();
			return true;
		}
		return false;
	}

	public boolean lose() {
		if (getScreen() instanceof GameScreen) {
			((GameScreen) getScreen()).lose();
			return true;
		}
		return false;
	}

	public void mainMenuMusic() {
		AudioManager.spaceMusic.stop();
		AudioManager.planetMusic.stop();
	}

	public void passSpaceLevel() {
		MemoryManager.saveIsPassedSpace(true);
		MemoryManager.saveLevel(2);
	}

	public void passPlanetLevel() {
		MemoryManager.saveIsPassedPlanet(true);
		MemoryManager.saveLevel(3);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void exit() {
		Gdx.app.exit();
	}
}
