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

    // Object Setting
  
    // Ship Setting
    public static final int SHOOTING_COOL_DOWN = 100; // in ms
    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static final int SPEED_SHIP = 7;

    // Bullet Setting
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 50;
    public static final int Bullet_Speed = 20; // in ms
}
