package com.kasarlooper.spaceranger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class GameResources {
    public static final String SHIP_IMG_PATH = "ship_%d.png";
    public static final String JOYSTICK_BACK_IMG_PATH = "joystick-zone.png";
    public static final String JOYSTICK_CIRCLE_IMG_PATH = "joystick-circle.png";
    public static final String BULLET_IMG_PATH = "bullet.png";
    public static final String BACKGROUND_IMG_PATH = "background.jpg";
    public static final String BACKGROUND_MENU_IMG_PATH = "background_menu.jpg";

    public static final String BACKGROUND_2_IMG_PATH = "back_2.png";
    public static final String BLACKOUT_IMG_PATH = "blackout_full.png";

    public static final String FONT_PATH = "fonts/ofont.ru_Montserrat.ttf";
    public static final String BUTTON_IMG_PATH = "button.png";
    public static final String STRIP_IMG_PATH = "strip.png";

    public static final String CORE_IMG_PATH = "core.png";
    public static final String ENEMY_SHIP_IMG_PATH = "Enemy_ship.png";
    public static final String BOOM_IMG_PATH = "Boom.png";
    public static final String ENEMY_BULLET_IMG_PATH = "enemy_bullet.png";

    public static final String ANIM_SHIP_PORTAL_IMG_PATH_PATTERN = "anim_ship_portal/anim_ship_portal_%d.png";
    public static final String FIRE_BUTTON_IMG_PATH = "Remove-bg.ai_1720009081104.png";
    public static final String PAUSE_ICON_IMG_PATH = "pause_icon.png";

    //music
    public static final String MUSIC_SPACE_PATH = "music/17237337355896yrd3mbm.mp3";
    public static final String SOUND_BULLET_PATH = "music/burst_fire.mp3";
    public static final String SOUND_BOOM_PATH = "music/Boom.mp3";
    public static final String SOUND_WALKING_PATH = "music/sound_walking.mp3";
    public static final String SOUND_SHOT_PATH = "music/sound_shot.mp3";
    public static final String SOUND_ENERGY_PATH = "music/f7b30b479a2a752.mp3";
    public static final String MUSIC_PLANET_PATH = "music/Planet_of_Nightmares.mp3";
    public static final String SOUND_DAMAGE_COSMONAUT = "music/damage_cosmonaut.mp3";
    public static final String SOUND_DAMAGE_ENEMY = "music/damage_enemy.mp3";


    //Второй уровень
    public static final String LIGHTNING_RIGHT_IMG_PATH = "lightning_right.png";
    public static final String LIGHTNING_LEFT_IMG_PATH = "lightning_left.png";
    public static final String EARTH_BACKGROUND_IMG_PATH = "earth_background.png";
    public static final String CAPSULE_IMG_PATH = "capsule.png";
    public static final String ASTEROID_IMG_PATH = "asteroid.png";

    //ресурсы
    public static final String CRYSTAL_IMG_PATH = "crystal.png";
    public static final String WRECKAGE_IMG_PATH = "wreckage.png";

    //платформы
    public static final String LEVEL_MAP_IMG_PATH = "level_platforms.png";

    //текстуры блоков для платформ
    public static final String TEXTURE_BOX_GREEN = "texture_box_1.png";
    public static final String TEXTURE_BOX_BLACK = "texture_box_2.png";

    public static final String COSMONAUT_ANIM_LEFT_IMG_PATTERN = "cosmonaut_anim_left/cosmonaut_anim_left_%d.png";

    public static final String JUMP_BUTTON_IMG_PATH = "upbutton.png";

    //Alien
    public static final String ALIEN_ANIM_LEFT_IMG_PATTERN = "Alien_anim_left/alien_anim_left_%d.png";

    //Заставка для конца игры
    public static final String BACKGROUND_END_IMG_PATH = "background_end.jpg";
    public static final String FIRE_BUTTON_PLANET_IMG_PATH = "fireButtonPlanet.png";

    public static final ShaderProgram RED_SHADER = new ShaderProgram(
            Gdx.files.internal("shaders/vertex_shader.glsl").readString(),
            Gdx.files.internal("shaders/fragment_shader.glsl").readString());
}
