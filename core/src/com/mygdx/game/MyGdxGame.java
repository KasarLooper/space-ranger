package com.mygdx.game;

import static com.mygdx.game.GameSettings.POSITION_ITERATIONS;
import static com.mygdx.game.GameSettings.STEP_TIME;
import static com.mygdx.game.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.manager.AudioManager;
import com.mygdx.game.manager.LevelMapManager;
import com.mygdx.game.manager.MemoryManager;
import com.mygdx.game.screens.HistoryScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.PlanetGameScreen;
import com.mygdx.game.screens.SelectLevelScreen;
import com.mygdx.game.screens.SpaceGameScreen;

public class MyGdxGame extends Game {
	public World space;
	public World planet;

	public boolean canAccessPlanetLevel;
	private float accumulator;

	public State state;


	public SpriteBatch batch;
	public Vector3 touch;
	public OrthographicCamera camera;
	public SpaceGameScreen spaceScreen;
	public PlanetGameScreen planetScreen;
	public MenuScreen menuScreen;
	public SelectLevelScreen selectLevelScreen;
	HistoryScreen historyScreen;
	public BitmapFont commonWhiteFont;
	public BitmapFont averageWhiteFont;
	public LevelMapManager levelMapManager;

	public AudioManager audioManager;
	@Override
	public void create () {
		canAccessPlanetLevel = MemoryManager.loadIsNextLevel();
		Box2D.init();
		space = new World(new Vector2(0, 0), true);
		planet = new World(new Vector2((float) GameSettings.GRAVITY_PLANET_X, (float) GameSettings.GRAVITY_PLANET_Y), true);

		commonWhiteFont = FontBuilder.generate(50, Color.WHITE, GameResources.FONT_PATH);
		averageWhiteFont = FontBuilder.generate(25, Color.WHITE, GameResources.FONT_PATH);

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

		spaceScreen = new SpaceGameScreen(this);
		planetScreen = new PlanetGameScreen(this);
		menuScreen = new MenuScreen(this);
		selectLevelScreen = new SelectLevelScreen(this);
		historyScreen = new HistoryScreen(this, new String[]{
				"Ты, капитан космического корабля \"Авангард\", отправился на одиночную экспедицию к новой обитаемой планете через кротовую нору.",
				"Твоя цель ясна: установить контакт с новой цивилизацией и открыть новые горизонты для человечества.",
				"Всё шло по плану, когда вдруг системы корабля начали показывать аномалии. Ты понимаешь, что твой путь отклонился, и вместо мирной обитаемой планеты ты оказался в районе космоса, кишащем враждебными силами. Враги начинают атаку на \"Авангард\", и тебе предстоит быстро занять боевой пост.",
				"Теперь ты управляешь \"Авангардом\", уклоняясь от вражеских атак и уничтожая вражеские корабли. В процессе боя тебе необходимо собирать энергетические ядра, разбросанные по полю боя, чтобы накапливать энергию для кротовой норы, которая позволит совершить переход на следующий уровень.",
				"Каждое энергетическое ядро приближает корабль к спасению, но враги становятся всё более агрессивными, и битва требует не только ловкости, но и стратегического мышления!"
		},
				planetScreen);
		audioManager = new AudioManager();
		setScreen(menuScreen);

		//setScreen(planetScreen);
		//Gdx.input.setInputProcessor(planetScreen);

		//setScreen(historyScreen);

		levelMapManager = new LevelMapManager();
	}

	public void stepWorld(World world) {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;
			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
