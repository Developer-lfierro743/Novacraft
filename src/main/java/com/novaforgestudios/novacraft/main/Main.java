package com.novaforgestudios.novacraft.main;

import org.lwjgl.glfw.GLFW;
import engine.io.Input;
import engine.io.Window;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public final int WIDTH = 1280, HEIGHT = 760;

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        System.out.println("Initializing Game!");

        // Ensure GLFW is initialized
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        window = new Window(WIDTH, HEIGHT, "NOVACRAFT INDUSTRIAL REVOLUTION Edition");
        window.create();

        // Check if window creation was successful
        if (window.getWindowHandle() == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }
    }

    public void run() {
        init();
        while (window != null && !window.shouldClose()) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                return;
            }
        }
        if (window != null) {
            window.destroy();
        }
        // Terminate GLFW
        GLFW.glfwTerminate();
    }

    private void update() {
        if (window != null) {
            window.update();
            if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
                System.out.println("X: " + Input.getMouseX() + " Y: " + Input.getMouseY());
            }
        }
    }

    private void render() {
        if (window != null) {
            window.swapBuffers();
        }
    }

    public static void main(String[] args) {
        new Main().start();
    }
}