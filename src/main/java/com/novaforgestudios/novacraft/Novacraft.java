package com.novaforgestudios.novacraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

// Static import for OpenGL constants and functions
import static org.lwjgl.opengl.GL11.*;

public class Novacraft {

    // The width and height of the game window
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    // Handle for the game window
    private long window;

    public static void main(String[] args) {
        new Novacraft().run();
    }

    public void run() {
        init();
        loop();

        // Clean up resources
        cleanup();
    }

    private void init() {
        // Set up an error callback to catch GLFW errors
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // Hide the window until it's ready
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        // Create the game window
        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "NovaCraft", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window on the screen
        GLFW.glfwSetWindowPos(
            window,
            (GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width() - WINDOW_WIDTH) / 2,
            (GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).height() - WINDOW_HEIGHT) / 2
        );

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);

        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Show the window
        GLFW.glfwShowWindow(window);
    }

    private void loop() {
        // Initialize OpenGL
        GL.createCapabilities();

        // Set the clear color (background color)
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f); // Dark gray background

        // Run the rendering loop until the user closes the window
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Clear the screen
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Render your game here

            // Swap the color buffers
            GLFW.glfwSwapBuffers(window);

            // Poll for window events
            GLFW.glfwPollEvents();
        }
    }

    private void cleanup() {
        // Free the window callbacks and destroy the window
        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}