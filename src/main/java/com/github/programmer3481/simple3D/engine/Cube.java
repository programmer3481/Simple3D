package com.github.programmer3481.simple3D.engine;

import org.joml.Vector3f;

public class Cube {
    private Shader shader;
    private Texture texture;
    private Vector3f position, rotation, scale;

    public Cube(Shader shader, Texture texture, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.shader = shader;
        this.texture = texture;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Shader getShader() {
        return shader;
    }

    public Texture getTexture() {
        return texture;
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
