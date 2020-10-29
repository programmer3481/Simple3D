package com.github.programmer3481.simple3D.engine;

import org.joml.Vector3f;

public class Cube {
    private Shader shader;
    private Vector3f position, rotation, scale;

    public Cube(Shader shader, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.shader = shader;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Shader getShader() {
        return shader;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }
}
