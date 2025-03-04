package com.novaforgestudios.novacraft.core;

import com.novaforgestudios.novacraft.entities.Player;
import com.novaforgestudios.novacraft.rendering.Cube;
import com.novaforgestudios.novacraft.utils.DebugOverlay;
import com.novaforgestudios.novacraft.utils.FPSCounter;
import com.novaforgestudios.novacraft.utils.GraphicsUtils;
import com.novaforgestudios.novacraft.utils.PlatformUtils;
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

    // Real-world walking speed in meters per second (matches Minecraft)
    private static final float MOVEMENT_SPEED = 4.317f;

    // Sprinting multiplier (30% faster)
    private static final float SPRINT_MULTIPLIER = 1.3f;

    // Sneaking multiplier (40% slower)
    private static final float SNEAK_MULTIPLIER = 0.6f;

    // InputManager instance
    private InputManager inputManager;

    // DebugOverlay instance
    private DebugOverlay debugOverlay;
    private boolean showDebugMenu = false; // Toggle debug menu with F3

    // Frame time tracking for frame rate independence
    private double lastFrameTime = System.nanoTime() / 1_000_000_000.0;

    // Variables to track last mouse position
    private double lastMouseX = WINDOW_WIDTH / 2.0;
    private double lastMouseY = WINDOW_HEIGHT / 2.0;
    private boolean firstMouse = true;

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

        // Initialize InputManager
        inputManager = new InputManager();

        // Initialize DebugOverlay
        debugOverlay = new DebugOverlay();

        // Set up key callbacks
        GLFW.glfwSetKeyCallback(window, (windowHandle, key, scancode, action, mods) -> {
            boolean isPressed = action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT;
            inputManager.setKeyState(key, isPressed);

            // Toggle debug menu with F3
            if (key == GLFW.GLFW_KEY_F3 && action == GLFW.GLFW_PRESS) {
                showDebugMenu = !showDebugMenu;
            }
        });

        // Handle Android-specific input
        if (PlatformUtils.isAndroid()) {
            GLFW.glfwSetCharCallback(window, (windowHandle, codepoint) -> {
                char character = (char) codepoint;
                inputManager.setKeyStateFromCharacter(character, true); // Key pressed
            });
        }

        // Set up mouse button callbacks
        GLFW.glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            boolean isPressed = action == GLFW.GLFW_PRESS;
            inputManager.setMouseButtonState(button, isPressed);
        });

        // Set up cursor position callbacks
        GLFW.glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
            if (firstMouse) {
                lastMouseX = xpos;
                lastMouseY = ypos;
                firstMouse = false;
            }

            float xOffset = (float) (xpos - lastMouseX);
            float yOffset = (float) (lastMouseY - ypos); // Reversed since y-coordinates go from bottom to top

            lastMouseX = xpos;
            lastMouseY = ypos;

            // Rotate the camera
            camera.rotate(xOffset, yOffset);
        });
    }

    public void run() {
        init();

        Matrix4f projectionMatrix = new Matrix4f().perspective(
            (float) Math.toRadians(45),
            (float) WINDOW_WIDTH / WINDOW_HEIGHT, 
            0.1f,
            100.0f
        );

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Calculate delta time
            double currentFrameTime = System.nanoTime() / 1_000_000_000.0;
            float deltaTime = (float) (currentFrameTime - lastFrameTime);
            lastFrameTime = currentFrameTime;

            // Update FPS counter
            FPSCounter.update();

            // Handle player movement and jumping
            handleInput(deltaTime);

            // Update the camera to follow the player
            camera.getPosition().set(player.getPosition()).add(0, 1, 3); // Offset for third-person view

            // Get the updated view matrix
            Matrix4f viewMatrix = camera.getViewMatrix();

            // Render the cube and player
            cube.render(viewMatrix, projectionMatrix);
            player.render(viewMatrix, projectionMatrix);

            // Render debug overlay if enabled
            if (showDebugMenu) {
                debugOverlay.update(player, camera, /* loadedChunks */ 0, /* loadedEntities */ 0);
                debugOverlay.render();
            }

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        cleanup();
    }

    /**
     * Handles user input for player movement and jumping.
     */
    private void handleInput(float deltaTime) {
        float speed = MOVEMENT_SPEED * deltaTime; // Scale movement by delta time

        // Adjust speed for sprinting or sneaking
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            speed *= SPRINT_MULTIPLIER; // Increase speed for sprinting
        } else if (inputManager.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            speed *= SNEAK_MULTIPLIER; // Decrease speed for sneaking
        }

        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_W)) {
            player.move(0, 0, -speed, cube); // Move forward
        }
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_S)) {
            player.move(0, 0, speed, cube); // Move backward
        }
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_A)) {
            player.move(-speed, 0, 0, cube); // Move left
        }
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_D)) {
            player.move(speed, 0, 0, cube); // Move right
        }
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            player.jump(); // Jump when spacebar is pressed
        }

        // Example: Check if the left mouse button is pressed
        if (inputManager.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("Left mouse button pressed at (" + inputManager.getMouseX() + ", " + inputManager.getMouseY() + ")");
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