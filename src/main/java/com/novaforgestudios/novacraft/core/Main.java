package com.novaforgestudios.novacraft.core;

import com.novaforgestudios.novacraft.entities.Player;
import com.novaforgestudios.novacraft.rendering.Cube;
import com.novaforgestudios.novacraft.utils.GraphicsUtils;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private long window;
    private Cube cube;
    private Player player;
    private Camera camera;

    // Movement speed
    private static final float MOVEMENT_SPEED = 0.05f;

    // Key states
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

    public void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Novacraft", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        var videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (videoMode != null) {
            GLFW.glfwSetWindowPos(
                window,
                (videoMode.width() - WINDOW_WIDTH) / 2,
                (videoMode.height() - WINDOW_HEIGHT) / 2
            );
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);

        GL.createCapabilities(); // Initialize OpenGL capabilities

        // Detect renderer and handle fallback if necessary
        if (GraphicsUtils.detectRenderer()) {
            GraphicsUtils.applySoftwareRendererFallback();
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enable depth testing for 3D rendering

        // Initialize player, camera, and cube
        player = new Player();
        camera = new Camera();
        cube = new Cube();

        // Set up key callbacks
        GLFW.glfwSetKeyCallback(window, (windowHandle, key, scancode, action, mods) -> {
            if (key >= 0 && key < keys.length) {
                keys[key] = action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT;
            }
        });
    }

    public void run() {
        init();

        Matrix4f projectionMatrix = new Matrix4f().perspective(
            (float) Math.toRadians(45), // Field of view
            (float) WINDOW_WIDTH / WINDOW_HEIGHT, // Aspect ratio
            0.1f, // Near plane
            100.0f // Far plane
        );

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Handle player movement and jumping
            handleInput();

            // Update the camera to follow the player
            camera.getPosition().set(player.getPosition()).add(0, 1, 3); // Offset for third-person view

            // Get the updated view matrix
            Matrix4f viewMatrix = camera.getViewMatrix();

            // Render the cube and player
            cube.render(viewMatrix, projectionMatrix);
            player.render(viewMatrix, projectionMatrix);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        cleanup();
    }

    /**
     * Handles user input for player movement and jumping.
     */
    private void handleInput() {
        if (keys[GLFW.GLFW_KEY_W]) {
            player.move(0, 0, -MOVEMENT_SPEED); // Move forward
        }
        if (keys[GLFW.GLFW_KEY_S]) {
            player.move(0, 0, MOVEMENT_SPEED); // Move backward
        }
        if (keys[GLFW.GLFW_KEY_A]) {
            player.move(-MOVEMENT_SPEED, 0, 0); // Move left
        }
        if (keys[GLFW.GLFW_KEY_D]) {
            player.move(MOVEMENT_SPEED, 0, 0); // Move right
        }
        if (keys[GLFW.GLFW_KEY_SPACE]) {
            player.jump(); // Jump when spacebar is pressed
        }
    }

    private void cleanup() {
        cube.cleanup();
        player.cleanup();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}