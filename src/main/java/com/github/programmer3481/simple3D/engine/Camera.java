package com.github.programmer3481.simple3D.engine;

import org.joml.Vector3f;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;
    private Matrix4f view;
    private float moveSpeedPerSec = 10.0f , mouseSensitivity = 0.2f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    public Camera(Vector3f position, Vector3f rotation, float fov, float aspect, float near, float far) {
        this.position = position;
        this.rotation = rotation;
        view = new Matrix4f();
        view.setPerspective(fov, aspect, near, far);
    }

    public Camera(Vector3f position, Vector3f rotation, float left, float right, float bottom, float top, float near, float far) {
        this.position = position;
        this.rotation = rotation;
        view = new Matrix4f();
        view.ortho(left, right, bottom, top, near, far);
    }

    public void update(float deltaTime) {
        float moveSpeed = moveSpeedPerSec * deltaTime;
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();

        float x = (float) Math.sin(Math.toRadians(rotation.y())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(rotation.y())) * moveSpeed;

        if (Input.isKeyDown(GLFW_KEY_A))
            position.add(new Vector3f(-z, 0.0f, x));
        if (Input.isKeyDown(GLFW_KEY_D))
            position.add(new Vector3f(z, 0, -x));
        if (Input.isKeyDown(GLFW_KEY_W))
            position.add(new Vector3f(-x , 0, -z));
        if (Input.isKeyDown(GLFW_KEY_S))
            position.add(new Vector3f(x , 0,  z));
        if (Input.isKeyDown(GLFW_KEY_E))
            position.add(new Vector3f(0, moveSpeed, 0));
        if (Input.isKeyDown(GLFW_KEY_Q))
            position.add(new Vector3f(0, -moveSpeed, 0));

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        rotation.add(new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }



    public Vector3f getRotation() {
        return rotation;
    }

    public Matrix4f getViewMatrix() {
        return view;
    }

    public Vector3f getPosition() {
        return position;
    }
}
