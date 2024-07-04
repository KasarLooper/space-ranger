package com.mygdx.game;

public class GameSettings {
    //Screen settings
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    //Simulation settings
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    //Spawn settings
    public static final int SPAWN_COOL_DOWN = 1500;
    public static final int CHANCE_CORE_SPAWN = 25; // в процентах

    // Object Setting
  
    // Ship Setting
    public static final int SHOOTING_COOL_DOWN = 1000; // in ms
    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static final int SPEED_SHIP = 3;

    // Enemy Settings
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 100;
    public static final int SPEED_ENEMY = 2;
    public static final int ENEMY_SHOOT_COOL_DOWN = 2000;


    // Core Settings
    public static final int CORE_WIDTH = 130;
    public static final int CORE_HEIGHT = 110;

    // Bullet Setting
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 50;
    public static final int Bullet_Speed = 30; // in ms

    //Enemy behaviour settings
    public static final int ENEMY_CHECK_DISTANCE = 800;
    public static final int ENEMY_CHECK_ANGLE = 15;
    public static final int ENEMY_SHOOT_DISTANCE = 400;
    public static final int ENEMY_SHOOT_ANGLE = 15;
    public static final float USUAL_ROTATION_SPEED = 0.4f;
    public static final float TO_PLAYER_ROTATION_SPEED = 0.8f;
}
