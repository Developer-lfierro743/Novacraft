package com.novaforgestudios.novacraft.core;

import org.lwjgl.glfw.GLFW;
import com.novaforgestudios.novacraft.utils.PlatformUtils;

import java.util.HashMap;
import java.util.Map;

public class InputManager {
    // Arrays to track key and mouse button states
    private final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private final boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    // Variables to track mouse position
    private double mouseX, mouseY;

    // Key mappings for Android
    private final Map<Character, Integer> androidKeyMap = new HashMap<>();

    public InputManager() {
        // Initialize Android key mappings
        androidKeyMap.put('w', GLFW.GLFW_KEY_W);
        androidKeyMap.put('a', GLFW.GLFW_KEY_A);
        androidKeyMap.put('s', GLFW.GLFW_KEY_S);
        androidKeyMap.put('d', GLFW.GLFW_KEY_D);
        androidKeyMap.put(' ', GLFW.GLFW_KEY_SPACE); // Spacebar
        androidKeyMap.put('\n', GLFW.GLFW_KEY_ENTER); // Enter key
        androidKeyMap.put('\b', GLFW.GLFW_KEY_BACKSPACE); // Backspace
    }

    /**
     * Sets the state of a key (pressed or released).
     *
     * @param key   The GLFW key code (e.g., GLFW.GLFW_KEY_W).
     * @param state True if the key is pressed, false if released.
     */
    public void setKeyState(int key, boolean state) {
        if (key >= 0 && key < keys.length) {
            keys[key] = state;
            System.out.println("Key state updated: " + key + " -> " + state); // Debugging output
        }
    }

    /**
     * Sets the state of a key based on a character input (for Android).
     *
     * @param character The character input from the virtual keyboard.
     * @param state     True if the key is pressed, false if released.
     */
    public void setKeyStateFromCharacter(char character, boolean state) {
        if (PlatformUtils.isAndroid()) {
            Integer glfwKey = androidKeyMap.get(Character.toLowerCase(character));
            if (glfwKey != null) {
                setKeyState(glfwKey, state);
                System.out.println("Mapped character '" + character + "' to GLFW key: " + glfwKey); // Debugging output
            } else {
                System.out.println("Unmapped character: " + character); // Debugging output
            }
        }
    }

    /**
     * Sets the state of a mouse button (pressed or released).
     *
     * @param button The GLFW mouse button code (e.g., GLFW.GLFW_MOUSE_BUTTON_LEFT).
     * @param state  True if the button is pressed, false if released.
     */
    public void setMouseButtonState(int button, boolean state) {
        if (button >= 0 && button < mouseButtons.length) {
            mouseButtons[button] = state;
            System.out.println("Mouse button state updated: " + button + " -> " + state); // Debugging output
        }
    }

    /**
     * Updates the mouse position.
     *
     * @param x The x-coordinate of the mouse cursor.
     * @param y The y-coordinate of the mouse cursor.
     */
    public void setMousePosition(double x, double y) {
        mouseX = x;
        mouseY = y;
        System.out.println("Mouse position updated: (" + x + ", " + y + ")"); // Debugging output
    }

    /**
     * Checks if a specific key is currently pressed.
     *
     * @param key The GLFW key code (e.g., GLFW.GLFW_KEY_W).
     * @return True if the key is pressed, false otherwise.
     */
    public boolean isKeyPressed(int key) {
        return key >= 0 && key < keys.length && keys[key];
    }

    /**
     * Checks if a specific mouse button is currently pressed.
     *
     * @param button The GLFW mouse button code (e.g., GLFW.GLFW_MOUSE_BUTTON_LEFT).
     * @return True if the button is pressed, false otherwise.
     */
    public boolean isMouseButtonPressed(int button) {
        return button >= 0 && button < mouseButtons.length && mouseButtons[button];
    }

    /**
     * Gets the current x-coordinate of the mouse cursor.
     *
     * @return The x-coordinate of the mouse cursor.
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     * Gets the current y-coordinate of the mouse cursor.
     *
     * @return The y-coordinate of the mouse cursor.
     */
    public double getMouseY() {
        return mouseY;
    }
}