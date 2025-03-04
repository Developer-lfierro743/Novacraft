package com.novaforgestudios.novacraft.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.Version;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import com.novaforgestudios.novacraft.entities.Player;
import com.novaforgestudios.novacraft.core.Camera;

public class DebugOverlay {

    private final StringBuilder debugInfo = new StringBuilder();

    public void update(Player player, Camera camera, int loadedChunks, int loadedEntities) {
        // Clear previous debug info
        debugInfo.setLength(0);

        // Player Information
        debugInfo.append("Player Position: ")
                .append(String.format("X: %.2f, Y: %.2f, Z: %.2f", player.getPosition().x, player.getPosition().y, player.getPosition().z))
                .append("\n");
        debugInfo.append("Facing Direction: ")
                .append(String.format("Yaw: %.2f, Pitch: %.2f", camera.getYaw(), camera.getPitch()))
                .append("\n");

        // World Information
        debugInfo.append("Region: world1.nwf\n");
        debugInfo.append("Loaded Chunks: ").append(loadedChunks).append("\n");
        debugInfo.append("Loaded Entities: ").append(loadedEntities).append("\n");

        // System Information
        debugInfo.append("GPU Renderer: ").append(GL11.glGetString(GL11.GL_RENDERER)).append("\n");
        debugInfo.append("GPU Vendor: ").append(GL11.glGetString(GL11.GL_VENDOR)).append("\n");
        debugInfo.append("OpenGL Version: ").append(GL11.glGetString(GL11.GL_VERSION)).append("\n");
        debugInfo.append("LWJGL Version: ").append(Version.getVersion()).append("\n");

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        debugInfo.append("CPU Name: ").append(osBean.getName()).append("\n");
        debugInfo.append("CPU Cores: ").append(osBean.getAvailableProcessors()).append("\n");

        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        debugInfo.append("Memory Usage: ").append(usedMemory / 1024 / 1024).append(" MB / ")
                .append(runtime.maxMemory() / 1024 / 1024).append(" MB\n");

        // Performance Metrics
        debugInfo.append("FPS: ").append(FPSCounter.getFPS()).append("\n");
        debugInfo.append("Delta Time: ").append(String.format("%.4f ms", FPSCounter.getDeltaTime() * 1000)).append("\n");
    }

    public void render() {
        // Output debug info to the console (temporary solution)
        System.out.println("Debug Info:\n" + debugInfo.toString());
    }
}