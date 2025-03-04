package com.novaforgestudios.novacraft.core;

import org.lwjgl.glfw.GLFW;

public class InputManager {
    // Arrays to track key and mouse button states
    private final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private final boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    // Variables to track mouse position
    private double mouseX, mouseY;

    /**
     * Sets the state of a key (pressed or released).
     *
     * @param key   The GLFW key code (e.g., GLFW.GLFW_KEY_W).
     * @param state True if the key is pressed, false if released.
     */
    public void setKeyState(int key, boolean state) {
        if (key >= 0 && key < keys.length) {
            keys[key] = state;
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