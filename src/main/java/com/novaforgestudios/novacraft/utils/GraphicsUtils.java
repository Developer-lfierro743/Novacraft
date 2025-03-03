package com.novaforgestudios.novacraft.utils;

import org.lwjgl.opengl.GL11;

public final class GraphicsUtils {

    // Private constructor to prevent instantiation
    private GraphicsUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Detects and logs the current renderer, vendor, and OpenGL version.
     * Also checks if the system is using a software renderer.
     *
     * @return true if running on a software renderer, false otherwise.
     */
    public static boolean detectRenderer() {
        String renderer = GL11.glGetString(GL11.GL_RENDERER); // e.g., "NVIDIA GeForce RTX 3060/PCIe/SSE2"
        String vendor = GL11.glGetString(GL11.GL_VENDOR);     // e.g., "NVIDIA Corporation"
        String version = GL11.glGetString(GL11.GL_VERSION);   // e.g., "4.6.0 NVIDIA 535.183.01"

        System.out.println("Renderer: " + renderer);
        System.out.println("Vendor: " + vendor);
        System.out.println("OpenGL Version: " + version);

        if (isSoftwareRenderer(renderer)) {
            System.out.println("WARNING: Running on a software renderer (e.g., Mesa LLVMpipe). Performance may be limited.");
            return true;
        } else {
            System.out.println("Running on a hardware-accelerated GPU.");
            return false;
        }
    }

    /**
     * Checks if the given renderer string indicates a software renderer.
     *
     * @param renderer The renderer string from OpenGL.
     * @return true if the renderer is a software renderer, false otherwise.
     */
    public static boolean isSoftwareRenderer(String renderer) {
        if (renderer == null) {
            return false; // Default to false if renderer info is unavailable
        }
        String lowerCaseRenderer = renderer.toLowerCase();
        return lowerCaseRenderer.contains("llvmpipe") || lowerCaseRenderer.contains("software");
    }

    /**
     * Provides fallback behavior for systems running on a software renderer.
     * For example, reduces graphical settings or disables advanced features.
     */
    public static void applySoftwareRendererFallback() {
        System.out.println("Applying fallback settings for software renderer...");
        // Example: Disable advanced shaders, reduce resolution, etc.
        // You can implement specific fallback logic here.
    }
}