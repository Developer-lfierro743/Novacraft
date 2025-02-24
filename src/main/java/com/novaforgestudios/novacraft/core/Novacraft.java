package com.novaforgestudios.novacraft.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Novacraft {

    private long window;

    public void run() {
        System.out.println("Novacraft is starting!");

        init();
        loop();

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

    if (!GLFW.glfwInit()) {
        throw new IllegalStateException("Unable to initialize GLFW");
    }

    GLFW.glfwDefaultWindowHints();
    GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

    long monitor = GLFW.glfwGetPrimaryMonitor();
    GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);

    window = GLFW.glfwCreateWindow(vidMode.width(), vidMode.height(), "Novacraft Prototype", monitor, MemoryUtil.NULL);
    if (window == MemoryUtil.NULL) {
        throw new RuntimeException("Failed to create the GLFW window.");
    }

    GLFW.glfwMakeContextCurrent(window);
    GLFW.glfwSwapInterval(1);
    GLFW.glfwShowWindow(window);

    GL.createCapabilities();
    GL11.glClearColor(1.0f,0.0f,0.0f,0.0f);;
}

    private void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Novacraft().run();
    }
}
