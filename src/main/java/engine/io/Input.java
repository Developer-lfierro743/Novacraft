package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {
    private static final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static final boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;

    private final GLFWKeyCallback keyboard;
    private final GLFWCursorPosCallback mouseMove;
    private final GLFWMouseButtonCallback mouseButtons;

    public Input() {
        keyboard = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            keys[key] = (action != GLFW.GLFW_RELEASE);
        });

        mouseMove = GLFWCursorPosCallback.create((window, xpos, ypos) -> {
            mouseX = xpos;
            mouseY = ypos;
        });

        mouseButtons = GLFWMouseButtonCallback.create((window, button, action, mods) -> {
            buttons[button] = (action != GLFW.GLFW_RELEASE);
        });
    }

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isButtonDown(int button) {
        return buttons[button];
    }

    public void destroy() {
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtons;
    }
}