package com.kasarlooper.spaceranger;

public class GameSettings {

    //ALL

    //Screen settings
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    //Simulation settings
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    //SPACE

    //Spawn settings
    public static final int SPACE_SPAWN_COOL_DOWN = 2000;
    public static final int PLANET_SPAWN_COOL_DOWN = 10_000;
    public static final int CHANCE_CORE_SPAWN = 25;
    public static final int CHANCE_ASTEROID_SPAWN = 25;

    // Object Setting
  
    // Ship Setting
    public static final int SHOOTING_COOL_DOWN = 1000; // in ms
    public static final int SHIP_WIDTH = 100;
    public static final int SHIP_HEIGHT = 100;
    public static final int SPEED_SHIP = 15;
    public static final int COUNT_FRAMES_ONE_IMG = 5;

    // Enemy Settings
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 100;
    public static final int SPEED_ENEMY = 8;
    public static final int ENEMY_SHOOT_COOL_DOWN = 2000;


    // Core Settings
    public static final int CORE_WIDTH = 50;
    public static final int CORE_HEIGHT = 50;

    // Asteroid settings
    public static final int ASTEROID_WIDTH_MIN = 100;
    public static final int ASTEROID_WIDTH_MAX = 200;
    public static final int ASTEROID_SPEED = 4;

    // Bullet Setting
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 50;
    public static final int BULLET_SPEED = 30; // in ms

    //Enemy behaviour settings
    public static final int ENEMY_CHECK_DISTANCE = 1200;
    public static final int ENEMY_CHECK_ANGLE = 90;
    public static final int ENEMY_SHOOT_DISTANCE = 400;
    public static final int ENEMY_SHOOT_ANGLE = 4;
    public static final float ENEMY_USUAL_ROTATION_SPEED = 1f;
    public static final float ENEMY_TO_PLAYER_ROTATION_SPEED = 10f;
    public static final int ENEMY_CHANCE_CHANGE_AIM = 10; // 5

    // PLANET

    public static final int WRECK_COUNT = 3;
    public static final int CRYSTAL_COUNT = 2;

    public static final double GRAVITY_PLANET_Y = -30;
    public static final double GRAVITY_PLANET_X = 0;
    public static final int GROUND_HEIGHT = 10;
    public static final int CAMERA_Y_FROM_CENTER = 0;

    // Chances
    public static final int CHANCE_CRYSTAL_SPAWN = 25;
    public static final int CHANCE_CRYSTAL_DROP = 10;
    public static final int CHANCE_WRECK_DROP = 60;

    // Earth settings
    public static final int EARTH_WIDTH = 640;
    public static final int EARTH_HEIGHT = 130;

    // Block settings
    public static final int BLOCK_SIZE = 85;
    public static final int MAP_HEIGHT = 16;

    // Cosmonaut settings
    public static final int COSMONAUT_WIDTH = 65; // 85
    public static final int COSMONAUT_HEIGHT = 160; // 170
    public static final int COSMONAUT_SPEED = 10;
    public static final int COSMONAUT_JUMP_FORCE = 75;
    public static final int FRAME_DURATION = 20;
    public static final int LIGHTNING_DAMAGE_MILLIS = 300;

    // Lightning settings
    public static final int LIGHTING_WIDTH = 240;
    public static final int LIGHTING_HEIGHT = 170;
    public static final int LIGHTNING_COOL_DOWN = 1000;


    // Alien settings
    public static final int ALIEN_WIDTH = 65;
    public static final int ALIEN_HEIGHT = 160;
    public static final int ALIEN_SPEED = 6;
    public static final int ALIEN_JUMP_FORCE = 75;
    public static final int ALIEN_DAMAGE_IMPULSE = 100000;
}
