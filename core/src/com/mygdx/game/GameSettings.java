package com.mygdx.game;

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
    public static final int SPAWN_COOL_DOWN = 2000 / 10;
    public static final int CHANCE_CORE_SPAWN = 100; // в процентах // 25

    // Object Setting
  
    // Ship Setting
    public static final int SHOOTING_COOL_DOWN = 1000; // in ms
    public static final int SHIP_WIDTH = 100;
    public static final int SHIP_HEIGHT = 100;
    public static final int SPEED_SHIP = 3;
    public static final int COUNT_FRAMES_ONE_IMG = 5;

    // Enemy Settings
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 100;
    public static final int SPEED_ENEMY = 3;
    public static final int ENEMY_SHOOT_COOL_DOWN = 2000;


    // Core Settings
    public static final int CORE_WIDTH = 130;
    public static final int CORE_HEIGHT = 110;

    // Bullet Setting
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 50;
    public static final int Bullet_Speed = 30; // in ms

    //Enemy behaviour settings
    public static final int ENEMY_CHECK_DISTANCE = 1200;
    public static final int ENEMY_CHECK_ANGLE = 90;
    public static final int ENEMY_SHOOT_DISTANCE = 400;
    public static final int ENEMY_SHOOT_ANGLE = 4;
    public static final float ENEMY_USUAL_ROTATION_SPEED = 1f;
    public static final float ENEMY_TO_PLAYER_ROTATION_SPEED = 10f;
    public static final int ENEMY_CHANCE_CHANGE_AIM = 10; // 5

    // PLANET

    public static final double GRAVITY_PLANET_Y = -30;
    public static final double GRAVITY_PLANET_X = 0;
    public static final int GROUND_HEIGHT = 200;

    // Block settings
    public static final int BLOCK_SIZE = 100;

    // Cosmonaut settings
    public static final int COSMONAUT_WIDTH = 50;
    public static final int COSMONAUT_HEIGHT = 75;
    public static final int COSMONAUT_SPEED = 10;
    public static final int COSMONAUT_JUMP_FORCE = 14;
}
