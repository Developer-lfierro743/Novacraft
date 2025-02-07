package com.novaforgestudios.novacraft.core;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SharedConstants {

    // Game settings
    public static final String GAME_TITLE = "Novacraft Pre-Development Version";
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static final int TARGET_FPS = 60;

    // World settings
    public static final int CHUNK_SIZE = 16;
    public static final int WORLD_WIDTH = 1024;
    public static final int WORLD_HEIGHT = 1024;

    // Player settings
    public static final float PLAYER_SPEED = 5.0f;
    public static final float PLAYER_JUMP_HEIGHT = 10.0f;

    // Rendering settings
    public static final int RENDER_DISTANCE = 10;
    public static final int SHADOW_MAP_SIZE = 2048;

    // Other settings
    public static final boolean DEBUG_MODE = false;
    public static final boolean FULLSCREEN = false;
    public static final boolean VSYNC_ENABLED = true;
    public static final boolean ANTI_ALIASING_ENABLED = true;
    public static final boolean SHADOWS_ENABLED = true;
    public static final boolean WATER_REFLECTIONS_ENABLED = false;
    public static final boolean PHYSICS_ENABLED = true;
    public static final boolean SOUND_EFFECTS_ENABLED = true;
    public static final boolean MUSIC_ENABLED = true;
    public static final boolean AUTO_SAVE_ENABLED = true;

    // Developer options
    public static final boolean DEVELOPER_MODE = false;
    public static final boolean RELEASE_BUILD = false;
    public static final boolean DEBUG_LOGGING_ENABLED = false;
    public static final boolean PROFILER_ENABLED = false;
    public static final boolean MEMORY_MONITORING_ENABLED = false;

    // Logger
    public static final Logger LOGGER = Logger.getLogger(SharedConstants.class.getName());
    public static final Level LOG_LEVEL = Level.INFO;
    public static final boolean LOG_TO_FILE = false;
    public static final String LOG_FILE_PATH = "logs/novacraft.log";
}