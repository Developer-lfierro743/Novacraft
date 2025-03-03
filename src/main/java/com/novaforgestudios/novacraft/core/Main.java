package com.novaforgestudios.novacraft.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11; // Import GL11 for OpenGL constants

public class Main {

    // Window dimensions
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    // GLFW window handle
    private long window;

    /**
     * Initialize the game.
     */
    public void init() {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // Hide window until fully initialized
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        // Create the window
        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Novacraft", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window on the screen
        var videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (videoMode != null) {
            GLFW.glfwSetWindowPos(
                window,
                (videoMode.width() - WINDOW_WIDTH) / 2,
                (videoMode.height() - WINDOW_HEIGHT) / 2
            );
        } else {
            System.err.println("Failed to retrieve video mode for primary monitor.");
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);

        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Show the window
        GLFW.glfwShowWindow(window);

        // Initialize OpenGL
        GL.createCapabilities();
        System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION)); // Fixed here
    }

    /**
     * Run the game loop.
     */
    public void run() {
        // Initialize the game
        init();

        // Main game loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Render here
            render();

            // Swap the color buffers
            GLFW.glfwSwapBuffers(window);

            // Poll for window events
            GLFW.glfwPollEvents();
        }

        // Clean up resources
        cleanup();
    }

    /**
     * Render the game.
     */
    private void render() {
        // Clear the screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Clean up resources.
     */
    private void cleanup() {
        // Destroy the window
        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    /**
     * Entry point for the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Main().run();
    }
}