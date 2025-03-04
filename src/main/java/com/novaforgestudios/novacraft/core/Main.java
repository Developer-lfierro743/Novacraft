package com.novaforgestudios.novacraft.core;

import com.novaforgestudios.novacraft.entities.Player;
import com.novaforgestudios.novacraft.rendering.Cube;
import com.novaforgestudios.novacraft.utils.FreeTypeFontRenderer;
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

    // FreeTypeFontRenderer instance
    private FreeTypeFontRenderer fontRenderer;

    // Debug Menu Variables
    private boolean showDebugMenu = false; // Toggle debug menu with F3

    // Frame time tracking for frame rate independence
    private double lastFrameTime = System.nanoTime();

    // Variables to track last mouse position
    private double lastMouseX = WINDOW_WIDTH / 2.0;
    private double lastMouseY = WINDOW_HEIGHT / 2.0;
    private boolean firstMouse = true;

    // Mouse sensitivity
    private static final float MOUSE_SENSITIVITY = 0.1f;

    // Debug mode flag
    private boolean debugMode = true;

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

        // Initialize FreeType Font Renderer
        try {
            fontRenderer = new FreeTypeFontRenderer("/assets/fonts/OpenSans-Regular.ttf", 24);
            log("Successfully loaded font: /assets/fonts/OpenSans-Regular.ttf");
        } catch (RuntimeException e) {
            System.err.println("Failed to load font. Using default rendering.");
            fontRenderer = null; // Fallback to no font rendering
        }

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

            float xOffset = (float) (xpos - lastMouseX) * MOUSE_SENSITIVITY;
            float yOffset = (float) (lastMouseY - ypos) * MOUSE_SENSITIVITY; // Reversed since y-coordinates go from bottom to top

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
            float deltaTime = calculateDeltaTime();

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

            // Render debug menu if enabled
            if (showDebugMenu) {
                renderDebugMenu();
            }

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        cleanup();
    }

    /**
     * Calculates delta time for frame rate independence.
     */
    private float calculateDeltaTime() {
        double currentFrameTime = System.nanoTime();
        float deltaTime = (float) ((currentFrameTime - lastFrameTime) / 1_000_000_000.0);
        lastFrameTime = currentFrameTime;
        return deltaTime;
    }

    /**
     * Handles user input for player movement and jumping.
     */
    private void handleInput(float deltaTime) {
        handleMovement(deltaTime);
        handleJumping();

        // Handle mouse input
        if (inputManager.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            log("Left mouse button pressed at (" + inputManager.getMouseX() + ", " + inputManager.getMouseY() + ")");
        }
    }

    /**
     * Handles player movement.
     */
    private void handleMovement(float deltaTime) {
        float speed = MOVEMENT_SPEED * deltaTime;

        // Adjust speed for sprinting or sneaking
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            speed *= SPRINT_MULTIPLIER;
        } else if (inputManager.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            speed *= SNEAK_MULTIPLIER;
        }

        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_W)) {
            player.move(0, 0, -speed, cube);
        }
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_S)) {
            player.move(0, 0, speed, cube);
        }
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_A)) {
            player.move(-speed, 0, 0, cube);
        }
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_D)) {
            player.move(speed, 0, 0, cube);
        }
    }

    /**
     * Handles player jumping.
     */
    private void handleJumping() {
        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            player.jump();
        }
    }

    /**
     * Renders the debug menu using FreeTypeFontRenderer.
     */
    private void renderDebugMenu() {
        if (fontRenderer == null) {
            return; // Skip rendering if fontRenderer is not initialized
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST); // Disable depth testing for 2D rendering
        GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set text color to white

        String debugInfo =
            "Player Position: " + player.getPosition() + "\n" +
            "Camera Yaw: " + camera.getYaw() + "\n" +
            "Camera Pitch: " + camera.getPitch() + "\n" +
            "FPS: " + FPSCounter.getFPS() + "\n" +
            "Delta Time: " + FPSCounter.getDeltaTime();

        float x = 10, y = 10, scale = 1.0f;
        for (String line : debugInfo.split("\n")) {
            fontRenderer.renderText(line, x, y, scale);
            y += 20; // Move to the next line
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST); // Re-enable depth testing
    }

    /**
     * Cleans up resources.
     */
    private void cleanup() {
        cube.cleanup();
        player.cleanup();
        if (fontRenderer != null) {
            fontRenderer.cleanup(); // Dispose FreeType resources
        }
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    /**
     * Logs messages if debug mode is enabled.
     */
    private void log(String message) {
        if (debugMode) {
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}