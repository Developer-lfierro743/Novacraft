package com.novaforgestudios.novacraft.utils;

public class FPSCounter {
    private static long lastTime = System.nanoTime();
    private static int frames = 0;
    private static float fps = 0;
    private static float deltaTime = 0;

    public static void update() {
        long currentTime = System.nanoTime();
        frames++;
        if (currentTime - lastTime >= 1_000_000_000) { // Every second
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }
        deltaTime = (currentTime - lastTime) / 1_000_000_000f;
    }

    public static float getFPS() {
        return fps;
    }

    public static float getDeltaTime() {
        return deltaTime;
    }
}
