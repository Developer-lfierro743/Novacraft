package com.novaforgestudios.novacraft.Engine;

import org.lwjgl.glfw.GLFW;

import com.novaforgestudios.novacraft.core.Display;

public class Game {

    private Display display;

    public Game() {
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void run() {
        // Game loop logic here
        while (!GLFW.glfwWindowShouldClose(display.getWindow())) {
            // Game loop logic here
            GLFW.glfwSwapBuffers(display.getWindow());
            GLFW.glfwPollEvents();
        }
    }
}