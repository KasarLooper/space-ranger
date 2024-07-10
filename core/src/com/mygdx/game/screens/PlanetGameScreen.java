package com.mygdx.game.screens;

import static com.mygdx.game.GameResources.ALIEN_ANIM_RIGHT_IMG_PATTERN;
import static com.mygdx.game.GameResources.COSMONAUT_ANIM_RIGHT_IMG_PATTERN;
import static com.mygdx.game.GameResources.CRYSTAL_IMG_PATH;
import static com.mygdx.game.GameSettings.ALIEN_JUMP_FORCE;
import static com.mygdx.game.GameSettings.ALIEN_SPEED;
import static com.mygdx.game.GameSettings.BLOCK_SIZE;
import static com.mygdx.game.GameSettings.CAMERA_Y_FROM_CENTER;
import static com.mygdx.game.GameSettings.COSMONAUT_HEIGHT;
import static com.mygdx.game.GameSettings.COSMONAUT_JUMP_FORCE;
import static com.mygdx.game.GameSettings.COSMONAUT_SPEED;
import static com.mygdx.game.GameSettings.COSMONAUT_WIDTH;
import static com.mygdx.game.GameSettings.GROUND_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static com.mygdx.game.State.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GraphicsSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.LiveView;
import com.mygdx.game.components.MovingBackgroundLeftRightView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.components.TextView;
import com.mygdx.game.manager.ContactManager;
import com.mygdx.game.manager.LevelMapManager;
import com.mygdx.game.objects.AlienObject;
import com.mygdx.game.objects.CapsuleObject;
import com.mygdx.game.objects.Earth;
import com.mygdx.game.objects.EnemyObject;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.LightningBulletObject;
import com.mygdx.game.objects.PhysicsBlock;
import com.mygdx.game.objects.ResourceObject;
import com.mygdx.game.objects.SpacemanObject;
import com.mygdx.game.session.PlanetGameSession;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.ResourceBundle;

import jdk.internal.loader.Resource;
import java.util.Random;

public class PlanetGameScreen extends GameScreen {
    LevelMapManager loader;
    ContactManager contactManager;
    MovingBackgroundView backgroundView;

    SpacemanObject spaceman;
    Earth earth;
    ArrayList<PhysicsBlock> physics;
    ArrayList<AlienObject> aliens;
    ArrayList<ResourceObject> wrecks;
    ArrayList<ResourceObject> crystals;
    ArrayList<GameObject> mobSpawns;
    ArrayList<GameObject> resSpawns;
    CapsuleObject capsule;

    LiveView lives;
    ButtonView jumpButton;
    TextView purpose;
    ButtonView fireButton;
    LightningBulletObject lightning;
    boolean isLighting;

    boolean isJump;

    public PlanetGameScreen(MyGdxGame game) {
        super(game);
        session = new PlanetGameSession();
        loader = new LevelMapManager();
        contactManager = new ContactManager(myGdxGame.planet);
        loader.loadMap(myGdxGame.planet);
        physics = loader.getPhysics();
        mobSpawns = loader.getMobSpawns();
        resSpawns = loader.getResSpawns();
        backgroundView = new MovingBackgroundLeftRightView(GameResources.BACKGROUND_2_IMG_PATH);
        spaceman = new SpacemanObject(
                loader.getPlayerX(), loader.getPlayerY(),
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                COSMONAUT_ANIM_RIGHT_IMG_PATTERN, 4,
                COSMONAUT_SPEED, COSMONAUT_JUMP_FORCE,
                myGdxGame.planet);
        earth = new Earth(GROUND_HEIGHT, myGdxGame.planet);
        aliens = new ArrayList<>();
        wrecks = new ArrayList<>();
        crystals = new ArrayList<>();
        capsule = new CapsuleObject(loader.getCapsuleX(), loader.getCapsuleY(), loader.getCapsuleWidth(), loader.getCapsuleHeight());
        jumpButton = new ButtonView(1150, 25, 100, 100, GameResources.JUMP_BUTTON_IMG_PATH);
        lives = new LiveView(0, 675);
        purpose = new TextView(myGdxGame.averageWhiteFont, 300, 675,
                String.format(GraphicsSettings.PLANET_AIM1_PATTERN, 0, 0));
        fireButton = new ButtonView(1000, 25, 100, 100, GameResources.FIRE_BUTTON_PLANET_IMG_PATH);
        isLighting = false;
        isJump = false;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        myGdxGame.camera.position.x = spaceman.getX();
        myGdxGame.camera.position.y = spaceman.getY() + GROUND_HEIGHT - CAMERA_Y_FROM_CENTER;
        if (session.state == com.mygdx.game.State.PLAYING) {
            backgroundView.move(spaceman.getX(), spaceman.getY());
            if (isJump)
                spaceman.jump();
            spaceman.updateFrames();
            myGdxGame.stepWorld(myGdxGame.planet);
            spaceman.updateJump();
        }
        lives.setLeftLives(spaceman.liveLeft);

        if (isLighting) {
            if (lightning.destroy()) {
                isLighting = false;
                myGdxGame.planet.destroyBody(lightning.body);
            }
        }

        if (((PlanetGameSession)session).shouldSpawnCore()) {
            Random random = new Random();
            if (random.nextInt(100) > GameSettings.CHANCE_CORE_SPAWN) {
                spawnAlien();
            } else {
                spawnCrystal();
            }
        }
        updateAlien();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void drawDynamic() {
        backgroundView.draw(myGdxGame.batch);
        capsule.draw(myGdxGame.batch);
        earth.draw(myGdxGame.batch, spaceman.getX());
        for (PhysicsBlock block : physics) block.draw(myGdxGame.batch);
        spaceman.draw(myGdxGame.batch);
        super.drawDynamic();
        if (isLighting) lightning.draw(myGdxGame.batch);
        for (ResourceObject wreck : wrecks) wreck.draw(myGdxGame.batch);
        for (ResourceObject crystal : crystals) crystal.draw(myGdxGame.batch);
        for (AlienObject alien : aliens) alien.draw(myGdxGame.batch);
    }

    @Override
    public void drawStatic() {
        super.drawStatic();
        jumpButton.draw(myGdxGame.batch);
        lives.draw(myGdxGame.batch);
        purpose.draw(myGdxGame.batch);
        fireButton.draw(myGdxGame.batch);
    }

    Random rd = new Random();

    public void spawnAlien() {
        int i = rd.nextInt(mobSpawns.size());
        while (true) {
            int x = mobSpawns.get(i).x;
            int y = mobSpawns.get(i).y;
            boolean isContinue = false;
            for (AlienObject res : aliens)
                if (Math.abs(res.getX() - x) < 10 && Math.abs(res.getY() - y) < 10) {
                    i++;
                    if (i >= mobSpawns.size())
                        i -= mobSpawns.size();
                    isContinue = true;
                    break;
                }
            if (isContinue) continue;
            mobSpawns.add(new AlienObject(x, y, BLOCK_SIZE, BLOCK_SIZE, ALIEN_ANIM_RIGHT_IMG_PATTERN,
                    5, ALIEN_SPEED, ALIEN_JUMP_FORCE, myGdxGame.planet));
            break;
        }
    }

    public void spawnWreck() {
    }

    public void spawnCrystal() {
        if (crystals.size() >= resSpawns.size()) return;
        int i = rd.nextInt(resSpawns.size());
        while (true) {
            int x = resSpawns.get(i).x;
            int y = resSpawns.get(i).y;
            boolean isContinue = false;
            for (ResourceObject res : crystals)
                if (Math.abs(res.getX() - x) < 10 && Math.abs(res.getY() - y) < 10) {
                    i++;
                    if (i >= resSpawns.size())
                        i -= resSpawns.size();
                    isContinue = true;
                    break;
                }
            if (isContinue) continue;
            crystals.add(new ResourceObject(x, y, BLOCK_SIZE, BLOCK_SIZE, CRYSTAL_IMG_PATH, myGdxGame.planet));
            break;
        }
    }

    @Override
    public void restartGame() {
        super.restartGame();
        myGdxGame.planet.destroyBody(spaceman.body);
        spaceman = new SpacemanObject(
                loader.getPlayerX(), loader.getPlayerY(),
                COSMONAUT_WIDTH, COSMONAUT_HEIGHT,
                COSMONAUT_ANIM_RIGHT_IMG_PATTERN, 4,
                COSMONAUT_SPEED, COSMONAUT_JUMP_FORCE,
                myGdxGame.planet);
        purpose.setText(String.format(GraphicsSettings.PLANET_AIM1_PATTERN, 0, 0));
    }

    @Override
    public void dispose() {
        super.dispose();
        spaceman.dispose();
        capsule.dispose();
        jumpButton.dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && session.state == PLAYING) {
            if (joystick.getDegrees() % 360 > 0 && joystick.getDegrees() % 360 <= 180)
                spaceman.stepLeft();
            else spaceman.stepRight();
        }
        if (jumpButton.isHit(screenX, Gdx.graphics.getHeight() - screenY))
            isJump = true;

        if (fireButton.isHit(screenX, Gdx.graphics.getHeight() - screenY) && ((PlanetGameSession)session).shouldSpawnLighting()) {
            isLighting = true;
            lightning = new LightningBulletObject(spaceman, myGdxGame.planet);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && session.state == PLAYING) spaceman.stop();
        if (jumpButton.isHit(screenX, Gdx.graphics.getHeight() - screenY))
            isJump = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) screenY * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX <= SCREEN_WIDTH / 2 && session.state == PLAYING) {
            if (joystick.getDegrees() % 360 > 0 && joystick.getDegrees() % 360 <= 180)
                spaceman.stepLeft();
            else spaceman.stepRight();
        }
        if (screenX > SCREEN_WIDTH / 2 && session.state == PLAYING) {
            if (!pauseButton.isHit(screenX, Gdx.graphics.getHeight() - screenY) && !jumpButton.isHit(screenX, Gdx.graphics.getHeight() - screenY)) {
                isJump = false;
            }
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            joystick.left();
            spaceman.stepLeft();
        }
        else if (keycode == Input.Keys.D) {
            joystick.right();
            spaceman.stepRight();
        }
        if (keycode == Input.Keys.SPACE)
            isJump = true;
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
            joystick.toDefault();
            spaceman.stop();
        }
        if (keycode == Input.Keys.SPACE)
            isJump = false;
        return super.keyUp(keycode);
    }

    public void updateAlien() {
        Iterator<AlienObject> iterator = aliens.iterator();
        while (iterator.hasNext()) {
            AlienObject alien = iterator.next();
            if (!alien.isAlive()) {
                Random random = new Random();
                if (random.nextInt(100) > 50) {
                    ResourceObject crystal = new ResourceObject(
                            alien.x, alien.y,
                            100,100,
                            GameResources.CRYSTAL_IMG_PATH,
                            myGdxGame.planet
                    );
                    crystals.add(crystal);
                } else {
                    ResourceObject wreck = new ResourceObject(
                            alien.x, alien.y,
                            100, 100,
                            GameResources.WRECKAGE_IMG_PATH,
                            myGdxGame.planet
                    );
                    wrecks.add(wreck);
                }
                myGdxGame.planet.destroyBody(alien.body);
                iterator.remove();
            }
        }
    }
}
