package com.kasarlooper.spaceranger.levels.planet;

import static com.kasarlooper.spaceranger.GameSettings.BLOCK_SIZE;
import static com.kasarlooper.spaceranger.GameSettings.CAMERA_Y_FROM_CENTER;
import static com.kasarlooper.spaceranger.GameSettings.CHANCE_CRYSTAL_DROP;
import static com.kasarlooper.spaceranger.GameSettings.CHANCE_CRYSTAL_SPAWN;
import static com.kasarlooper.spaceranger.GameSettings.CHANCE_WRECK_DROP;
import static com.kasarlooper.spaceranger.GameSettings.CRYSTAL_COUNT;
import static com.kasarlooper.spaceranger.GameSettings.GRAVITY;
import static com.kasarlooper.spaceranger.GameSettings.GROUND_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;
import static com.kasarlooper.spaceranger.GameSettings.WRECK_COUNT;
import static com.kasarlooper.spaceranger.GraphicsSettings.PLANET_AIM2_PATTERN;
import static com.kasarlooper.spaceranger.State.ENDED;
import static com.kasarlooper.spaceranger.State.PAUSED;
import static com.kasarlooper.spaceranger.State.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.kasarlooper.spaceranger.BlockMap;
import com.kasarlooper.spaceranger.GameResources;
import com.kasarlooper.spaceranger.GraphicsSettings;
import com.kasarlooper.spaceranger.MyGdxGame;
import com.kasarlooper.spaceranger.components.ButtonView;
import com.kasarlooper.spaceranger.components.ImageView;
import com.kasarlooper.spaceranger.components.LiveView;
import com.kasarlooper.spaceranger.components.MovingBackgroundLeftRightView;
import com.kasarlooper.spaceranger.components.MovingBackgroundView;
import com.kasarlooper.spaceranger.components.TextView;
import com.kasarlooper.spaceranger.levels.GameObject;
import com.kasarlooper.spaceranger.levels.physics.WorldWrap;
import com.kasarlooper.spaceranger.levels.planet.objects.AlienObject;
import com.kasarlooper.spaceranger.levels.planet.objects.CapsuleObject;
import com.kasarlooper.spaceranger.levels.planet.objects.Earth;
import com.kasarlooper.spaceranger.levels.planet.objects.LightningBulletObject;
import com.kasarlooper.spaceranger.levels.planet.objects.PhysicsBlock;
import com.kasarlooper.spaceranger.levels.planet.objects.ResourceObject;
import com.kasarlooper.spaceranger.levels.planet.objects.SpacemanObject;
import com.kasarlooper.spaceranger.manager.AudioManager;
import com.kasarlooper.spaceranger.manager.LevelMapManager;
import com.kasarlooper.spaceranger.screens.GameScreen;
import com.kasarlooper.spaceranger.session.PlanetGameSession;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlanetGameScreen extends GameScreen {
    LevelMapManager loader;
    BlockMap blockMap;
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
    ImageView strip;
    TextView purpose;
    ButtonView fireButton;
    LightningBulletObject lightning;
    boolean isLighting;
    boolean isJump;
    boolean isEnoughResources;
    boolean isWinGame;

    public PlanetGameScreen(MyGdxGame game) {
        super(game);
        world = new WorldWrap(GRAVITY, game);

        session = new PlanetGameSession();
        loader = new LevelMapManager();
        loader.loadMap(world);
        physics = loader.getPhysics();
        blockMap = new BlockMap(physics, 200, 16);
        mobSpawns = loader.getMobSpawns();
        resSpawns = loader.getResSpawns();
        backgroundView = new MovingBackgroundLeftRightView(GameResources.BACKGROUND_2_IMG_PATH);

        spaceman = new SpacemanObject(loader.getPlayerX(), loader.getPlayerY(), world, blockMap);
        earth = new Earth(world);
        aliens = new ArrayList<>();
        wrecks = new ArrayList<>();
        crystals = new ArrayList<>();
        capsule = new CapsuleObject(loader.getCapsuleX(), loader.getCapsuleY(), loader.getCapsuleWidth(), loader.getCapsuleHeight());
        jumpButton = new ButtonView(1130, 10, 150, 150, GameResources.JUMP_BUTTON_IMG_PATH);
        lives = new LiveView(0, 675);
        purpose = new TextView(myGdxGame.averageWhiteFont, 300, 675,
                String.format(GraphicsSettings.PLANET_AIM1_PATTERN, spaceman.cristalCount, spaceman.wreckCount));



        fireButton = new ButtonView(970, 10, 150, 150, GameResources.FIRE_BUTTON_PLANET_IMG_PATH);
        strip = new ImageView(70, 800, 220,650, GameResources.STRIP_IMG_PATH);
        isLighting = false;
        isJump = false;
        isEnoughResources = false;
        isWinGame = false;

        dx = 0;
        dy = 0;

    }

    protected float getCenterX() {
        return spaceman.getCenterX();
    }

    @Override
    protected float getCenterY() {
        return spaceman.getCenterY();
    }

    float dx, dy;

    @Override
    public void render(float delta) {
        super.render(delta);
        if (session.state == PAUSED) {
            isJump = false;
            isLighting = false;
            joystick.toDefault();
        }

        myGdxGame.camera.position.x = spaceman.getCenterX() + dx;
        myGdxGame.camera.position.y = spaceman.getCenterY() + GROUND_HEIGHT - CAMERA_Y_FROM_CENTER + dy;
        if (spaceman.isAlive()) {
            if (session.state == com.kasarlooper.spaceranger.State.PLAYING) {
                backgroundView.move(spaceman.getCenterX(), spaceman.getCenterY());
                if (isJump) spaceman.jump();
                for (AlienObject alien : aliens)
                    alien.move(spaceman.getCenterX(), spaceman.getCenterY(), physics);
                world.update(delta);
                spaceman.update();
                for (AlienObject alien : aliens) alien.update();

                lives.setLeftLives(spaceman.liveLeft);
                if (lightning != null && lightning.destroyIfNeed()) lightning = null;
                if (lightning == null && isLighting && LightningBulletObject.isShootTime()) {
                    lightning = new LightningBulletObject(spaceman, world);
                    AudioManager.soundShot.play(0.2f);
                }

                if (((PlanetGameSession)session).shouldSpawnCore()) {
                    Random random = new Random();
                    if (random.nextInt(100) > CHANCE_CRYSTAL_SPAWN) {
                        spawnAlien();
                    } else {
                        spawnCrystal();
                    }
                }
                updateAlien();
                updateCore();

                if (spaceman.cristalCount >= CRYSTAL_COUNT && spaceman.wreckCount >= WRECK_COUNT) {
                    purpose.setText(PLANET_AIM2_PATTERN);
                    purpose.TextPosition();
                    isEnoughResources = true;
                }

                if (capsule.isCollision(spaceman.getCenterX(), spaceman.getCenterY()) && isEnoughResources) {
                    session.state = ENDED;
                    ((PlanetGameSession) session).setVictory();
                    myGdxGame.passPlanetLevel();
                }
            }
        } else {
            session.state = ENDED;
        }
    }

    @Override
    public void show() {
        super.show();
        AudioManager.planetMusic.play();
    }

    @Override
    public void hide() {
        super.hide();
        AudioManager.planetMusic.stop();
    }

    @Override
    public void drawDynamic() {
        backgroundView.draw(myGdxGame.batch);
        capsule.draw(myGdxGame.batch);
        earth.draw(myGdxGame.batch, spaceman.getCenterX());
        for (PhysicsBlock block : physics) {
            if (myGdxGame.camera.frustum.sphereInFrustum(block.getCenterX(), block.getCenterY(), 0, Math.max(block.height, block.width))) {
                block.draw(myGdxGame.batch);
            }
        }
        //spaceman.draw(myGdxGame.batch);
        super.drawDynamic();
        if (lightning != null) lightning.draw(myGdxGame.batch);
        for (ResourceObject wreck : wrecks) wreck.draw(myGdxGame.batch);
        for (ResourceObject crystal : crystals) crystal.draw(myGdxGame.batch);
        for (AlienObject alien : aliens) alien.draw(myGdxGame.batch);
    }

    @Override
    public void drawStatic() {
        spaceman.staticDraw(myGdxGame.batch);

        jumpButton.draw(myGdxGame.batch);
        lives.draw(myGdxGame.batch);
        strip.draw(myGdxGame.batch);
        purpose.draw(myGdxGame.batch);
        fireButton.draw(myGdxGame.batch);
        super.drawStatic();
    }


    Random rd = new Random();

    public void spawnAlien() {
        ArrayList<GameObject> near = new ArrayList<>();
        for (GameObject cords : mobSpawns) {
            float dx = Math.abs(cords.getCenterX() - spaceman.getCenterX());
            if (dx > SCREEN_WIDTH / 2f && dx < SCREEN_WIDTH * 2.5f)
                near.add(cords);
        }
        removeCollapsed(near);
        if (!near.isEmpty()) {
            int i = rd.nextInt(near.size());
            int x = near.get(i).getCenterX();
            int y = near.get(i).getCenterY();
            aliens.add(new AlienObject(x, y, world, blockMap));
        }
    }

    public void spawnCrystal() {
        ArrayList<GameObject> near = new ArrayList<>();
        for (GameObject cords : resSpawns) {
            float dx = Math.abs(cords.getCenterX() - spaceman.getCenterX());
            if (dx > SCREEN_WIDTH / 2f && dx < SCREEN_WIDTH * 2.5f)
                near.add(cords);
        }
        removeCollapsed(near);
        if (!near.isEmpty()) {
            int i = rd.nextInt(near.size());
            int x = near.get(i).getCenterX();
            int y = near.get(i).getCenterY();
            crystals.add(new ResourceObject(x, y, true, world));
        }
    }

    private void removeCollapsed(ArrayList<GameObject> near) {
        Iterator<GameObject> iterator = near.iterator();
        while (iterator.hasNext()) {
            GameObject object1 = iterator.next();
            boolean shouldRemove = false;

            for (GameObject object2 : wrecks) {
                if (Math.abs(object1.getCenterX() - object2.getCenterX()) <= BLOCK_SIZE &&
                        Math.abs(object1.getCenterY() - object2.getCenterY()) <= BLOCK_SIZE) {
                    shouldRemove = true;
                    break;
                }
            }

            if (!shouldRemove) {
                for (GameObject object2 : aliens) {
                    if (Math.abs(object1.getCenterX() - object2.getCenterX()) <= BLOCK_SIZE &&
                            Math.abs(object1.getCenterY() - object2.getCenterY()) <= BLOCK_SIZE) {
                        shouldRemove = true;
                        break;
                    }
                }
            }

            if (!shouldRemove) {
                for (GameObject object2 : crystals) {
                    if (Math.abs(object1.getCenterX() - object2.getCenterX()) <= BLOCK_SIZE &&
                            Math.abs(object1.getCenterY() - object2.getCenterY()) <= BLOCK_SIZE) {
                        shouldRemove = true;
                        break;
                    }
                }
            }

            if (shouldRemove) {
                iterator.remove();
            }
        }

    }

    @Override
    public void restartGame() {
        super.restartGame();
        world.destroyBody(spaceman.body);
        for (AlienObject alien : aliens)
            world.destroyBody(alien.body);
        for (ResourceObject res : wrecks)
            world.destroyBody(res.body);
        for (ResourceObject res : crystals)
            world.destroyBody(res.body);
        spaceman = new SpacemanObject(loader.getPlayerX(), loader.getPlayerY(), world, blockMap);
        aliens = new ArrayList<>();
        wrecks = new ArrayList<>();
        crystals = new ArrayList<>();
        lives.setLeftLives(3);
        purpose.setText(String.format(GraphicsSettings.PLANET_AIM1_PATTERN, spaceman.wreckCount, spaceman.cristalCount));
        purpose.TextPosition();
        spaceman.cristalCount = 0;
        spaceman.wreckCount = 0;
        isEnoughResources = false;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (backgroundView != null) backgroundView.dispose();
        if (spaceman != null) spaceman.dispose();
        if (earth != null) earth.dispose();
        if (physics != null) {
            for (PhysicsBlock block : physics) {
                block.dispose();
            }
        }
        if (aliens != null) {
            for (AlienObject alien : aliens) {
                alien.dispose();
            }
        }
        /*
        if (wrecks != null) {
            for (ResourceObject wreck : wrecks) {
                wreck.dispose();
            }
        }
        if (crystals != null) {
            for (ResourceObject crystal : crystals) {
                crystal.dispose();
            }
        }
         */
        if (capsule != null) capsule.dispose();
        if (lives != null) lives.dispose();
        if (jumpButton != null) jumpButton.dispose();
        if (strip != null) strip.dispose();
        if (purpose != null) purpose.dispose();
        if (fireButton != null) fireButton.dispose();
        //if (lightning != null) lightning.dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        if (session.state != PLAYING) return true;
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) (Gdx.graphics.getHeight() - screenY) * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX > SCREEN_WIDTH / 2) {
            if (jumpButton.isHit(screenX, screenY)) isJump = true;
            if (fireButton.isHit(screenX, screenY)) isLighting = true;
        } else {
            if (joystick.isTouched()) joystick.onDrag(screenX, screenY);
            else joystick.onTouch(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (session.state != PLAYING) return true;
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) (Gdx.graphics.getHeight() - screenY) * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (jumpButton.isHit(screenX, screenY)) isJump = false;
        else if (fireButton.isHit(screenX, screenY)) isLighting = false;
        else {
            joystick.toDefault();
            spaceman.stop();
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (session.state != PLAYING) return true;
        screenX = Math.round((float) screenX * (float) SCREEN_WIDTH / (float) Gdx.graphics.getWidth());
        screenY = Math.round((float) (Gdx.graphics.getHeight() - screenY) * (float) SCREEN_HEIGHT / (float) Gdx.graphics.getHeight());
        if (screenX > SCREEN_WIDTH / 2) {
            if (!jumpButton.isHit(screenX, screenY)) isJump = false;
            if (!fireButton.isHit(screenX, screenY)) isLighting = false;
        }
        if (!jumpButton.isHit(screenX, screenY) && !fireButton.isHit(screenX, screenY)) {
            if (screenX <= SCREEN_WIDTH / 2) {
                joystick.onDrag(screenX, screenY);
                if (joystick.getDegrees() % 360 > 0 && joystick.getDegrees() % 360 <= 180)
                    spaceman.stepLeft();
                else spaceman.stepRight();
            } else {
                joystick.toDefault();
                spaceman.stop();
            }
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        final float padding = 300;

        if (keycode == Input.Keys.RIGHT)
            dx += padding;
        if (keycode == Input.Keys.LEFT)
            dx -= padding;
        if (keycode == Input.Keys.UP)
            dy += padding;
        if (keycode == Input.Keys.DOWN)
            dy -= padding;
        if (keycode == Input.Keys.ESCAPE) {
            dx = 0;
            dy = 0;
        }

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
                if (rd.nextInt(100) < CHANCE_CRYSTAL_DROP) {
                    ResourceObject crystal = new ResourceObject((int) alien.getCenterX(), (int) alien.getCenterY(), true, world);
                    crystals.add(crystal);
                } else if (rd.nextInt(100) < CHANCE_WRECK_DROP) {
                    ResourceObject wreck = new ResourceObject((int) alien.getCenterX(), (int) alien.getCenterY(), false, world);
                    wrecks.add(wreck);
                }
                world.destroyBody(alien.body);
                iterator.remove();
            }
        }
    }

    public void updateCore() {
        Iterator<ResourceObject> iterator1 = wrecks.iterator();
        while (iterator1.hasNext()) {
            ResourceObject wreck = iterator1.next();
            if (wreck.destroy()) {
                spaceman.wreckCount += 1;
                purpose.setText(String.format(GraphicsSettings.PLANET_AIM1_PATTERN, spaceman.wreckCount, spaceman.cristalCount));
                world.destroyBody(wreck.body);
                iterator1.remove();
            }
        }

        Iterator<ResourceObject> iterator2 = crystals.iterator();
        while (iterator2.hasNext()) {
            ResourceObject crystal = iterator2.next();
            if (crystal.destroy()) {
                spaceman.cristalCount += 1;
                purpose.setText(String.format(GraphicsSettings.PLANET_AIM1_PATTERN, spaceman.wreckCount, spaceman.cristalCount));
                world.destroyBody(crystal.body);
                iterator2.remove();
            }
        }
    }

    @Override
    public void win() {
        spaceman.wreckCount = 4;
        spaceman.cristalCount = 4;
    }

    @Override
    public void lose() {
        spaceman.liveLeft = 0;
    }
}
