package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
    private int width, height;
    private String title;
    private long windowHandle;
    public int frames;
    public static long time;
    private Input input;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.input = new Input();
    }

    public void create() {
        // Remove GLFW initialization here since it's done in the Main class
        // if (!GLFW.glfwInit()) {
        //     System.err.println("ERROR: GLFW could not be initialized!");
        //     return;
        // }

        windowHandle = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (windowHandle == 0) {
            System.err.println("ERROR: Window could not be created!");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(windowHandle, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwShowWindow(windowHandle);
        GLFW.glfwSwapInterval(1);

        GLFW.glfwSetKeyCallback(windowHandle, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(windowHandle, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(windowHandle, input.getMouseButtonsCallback());

        time = System.currentTimeMillis();
    }

    public void update() {
        GLFW.glfwPollEvents();
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(windowHandle, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void destroy() {
        input.destroy();
        GLFW.glfwDestroyWindow(windowHandle);
        // GLFW.glfwTerminate(); // Avoid terminating GLFW here if it's terminated in the Main class
    }

    public long getWindowHandle() {
        return windowHandle;
    }
}