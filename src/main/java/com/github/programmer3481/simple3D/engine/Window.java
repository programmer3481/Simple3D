package com.github.programmer3481.simple3D.engine;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Window {
    public Input input;
    private int width, height;
    private String title;
    private long window = 0;
    private GLFWVidMode videoMode;
    private Vector3f backgroundColor;
    private int frames;
    private long time;

    public Window(int width, int height, String title, Vector3f backgroundColor) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.backgroundColor = backgroundColor;

        if (!glfwInit()) {
            System.err.println("ERROR: GLFW couldn't initialize");
            return;
        }
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        input = new Input();
        window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            System.err.println("ERROR: Window couldn't be created");
            return;
        }

        videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        createCapabilities();
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);

        glfwSetKeyCallback(window, input.getKeyboardCallback());
        glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        glfwSetScrollCallback(window, input.getMouseScrollCallback());

        glfwShowWindow(window);

        time = System.currentTimeMillis();
        frames = 0;
    }

    public void update() {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, widthBuffer, heightBuffer);
        width = widthBuffer.get(0);
        height = heightBuffer.get(0);
        glViewport(0, 0, width, height);

        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();

        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public void mouseState(boolean lock) {
        glfwSetInputMode(window, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
    }

    public void setBackgroundColor(float r, float g, float b) {
        backgroundColor.set(r, g, b);
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }
}
