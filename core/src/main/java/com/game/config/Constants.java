package com.game.config;

import org.joml.Vector3f;

public class Constants {
    private Constants() {}

    // Window
    // 16 units wide and 9 units high. 1 unit ~ 1 Tile (32px * 32px || 16px * 16px || ...)
    public static final float WIDTH = 16f;
    public static final float HEIGHT = 9f;

    public static final float UNIT_SCALE = 1f / 16f;

    // Light
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(.3f,.3f,.3f);
}
