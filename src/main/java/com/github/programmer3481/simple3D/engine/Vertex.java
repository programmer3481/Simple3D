package com.github.programmer3481.simple3D.engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {
    private Vector3f position;
    private Vector3f color;
    private Vector2f texCoord;
    private Vector3f normal;

    public Vertex(Vector3f position, Vector3f color, Vector2f texCoord, Vector3f normal) {
        this.position = position;
        this.color = color;
        this.texCoord = texCoord;
        this.normal = normal;
    }

    public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
        this(position, new Vector3f(1.0f, 1.0f, 1.0f), texCoord, normal);
    }

    public Vertex(Vector3f position, Vector2f texCoord) {
        this(position, new Vector3f(1.0f, 1.0f, 1.0f), texCoord, new Vector3f(0.0f, 0.0f, 1.0f));
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector2f getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
}
