package com.novaforgestudios.novacraft.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Main {

    private long window;

    public void run() {
        System.out.println("Starting Novacraft...");
        init();
        loop();
        cleanup();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        // Create the window
        window = GLFW.glfwCreateWindow(1280, 720, "Novacraft - Industrial Revolution", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window
        centerWindow();
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // Enable v-sync
        GLFW.glfwShowWindow(window);
        GL.createCapabilities(); // Initialize OpenGL

        // Set up input callbacks
        setupInputCallbacks();

        System.out.println("Window successfully created!");
    }

    private void centerWindow() {
        // Get the monitor and the video mode to center the window
        long monitor = GLFW.glfwGetPrimaryMonitor();
        GLFW.GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
        if (vidMode != null) {
            int x = (vidMode.width() - 1280) / 2;
            int y = (vidMode.height() - 720) / 2;
            GLFW.glfwSetWindowPos(window, x, y);
        }
    }

    private void setupInputCallbacks() {
        // Set up a key callback to handle key presses
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true); // Close the window on ESC
            }
        });
    }

    private void loop() {
        // Game loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Clear the screen
            GL11.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // Render your game here (e.g., draw objects, update game state)

            
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    private void cleanup() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}