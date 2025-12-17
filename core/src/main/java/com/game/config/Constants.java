package com.game.config;

import org.joml.Vector3f;

public class Constants {
    private Constants() {}

    // Window
    // 16 units wide and 9 units high. 1 unit ~ 1 Tile (32px * 32px || 16px * 16px || ...)
    public static final float WIDTH = 16f;
    public static final float HEIGHT = 9f;

    public static final float UNIT_SCALE = 1f / 16f;

    public static final float FRAME_DURATION = 1 / 8f;

    // Light
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(.3f,.3f,.3f);

    // Tiled Layers
    public static final String OBJECT_LAYER = "objects";
    public static final String GROUND_LAYER = "ground";
    public static final String BACKGROUND_LAYER = "background";
    public static final String FORE_LAYER = "foreground";

    // Tiled: Custom Properties
    public static final String ANIMATION = "animation";
    public static final String ATLAS_ASSET = "atlasAsset";
    public static final String ANIMATION_SPEED = "animationSpeed";
    public static final String SPEED = "speed";
    public static final String CONTROLLER = "controller";
    public static final String Z = "z";
    public static final String MAP_ASSET = "mapAsset";
}
