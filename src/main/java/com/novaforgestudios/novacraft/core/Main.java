 package com.novaforgestudios.novacraft.core;

import com.novaforgestudios.novacraft.rendering.Cube;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Main {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private long window;
    private Cube cube;

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

        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enable depth testing for 3D rendering

        cube = new Cube();
    }

    public void run() {
        init();

        Matrix4f viewMatrix = new Matrix4f().translate(0, 0, -3); // Move camera back
        Matrix4f projectionMatrix = new Matrix4f().perspective(
            (float) Math.toRadians(45), // Field of view
            (float) WINDOW_WIDTH / WINDOW_HEIGHT, // Aspect ratio
            0.1f, // Near plane
            100.0f // Far plane
        );

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Render the cube
            cube.render(viewMatrix, projectionMatrix);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        cleanup();
    }

    private void cleanup() {
        cube.cleanup();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}