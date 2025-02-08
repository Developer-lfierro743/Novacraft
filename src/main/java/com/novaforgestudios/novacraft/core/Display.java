package com.novaforgestudios.novacraft.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.opengl.GL;

import com.novaforgestudios.novacraft.Engine.Game;

public class Display {

    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWWindowCloseCallback closeCallback;
    private final Game game;

    public Display(int width, int height, String title, Game game) {
        this.game = game;

        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            throw new RuntimeException("Failed to create window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                    GLFW.glfwSetWindowShouldClose(window, true);
                }
            }
        };

        closeCallback = new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long window) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        };

        GLFW.glfwSetKeyCallback(window, keyCallback);
        GLFW.glfwSetWindowCloseCallback(window, closeCallback);
    }

    public long getWindow() {
        return window;
    }

    public void start() {
        game.run();
        cleanup();
    }

    public void cleanup() {
        GLFW.glfwTerminate();
    }
}