package com.github.programmer3481.simple3D.engine;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private Mesh cubeMesh;

    public Renderer() {
        cubeMesh = new Mesh(new Vertex[] {
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f), new Vector2f(1.0f, 2.0f/4)), //v0
                new Vertex(new Vector3f(1.0f, -1.0f, -1.0f), new Vector2f(2.0f/3, 2.0f/4)), //v1
                new Vertex(new Vector3f(1.0f, 1.0f, -1.0f), new Vector2f(2.0f/3, 3.0f/4)), //v2
                new Vertex(new Vector3f(-1.0f, 1.0f, -1.0f), new Vector2f(1.0f, 3.0f/4)), //v3
                new Vertex(new Vector3f(-1.0f, -1.0f, 1.0f), new Vector2f(0.0f, 2.0f/4)), //v4
                new Vertex(new Vector3f(1.0f, -1.0f, 1.0f), new Vector2f(1.0f/3, 2.0f/4)), //v5
                new Vertex(new Vector3f(1.0f, 1.0f, 1.0f), new Vector2f(1.0f/3, 3.0f/4)), //v6
                new Vertex(new Vector3f(-1.0f, 1.0f, 1.0f), new Vector2f(0.0f, 3.0f/4)), //7

                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f), new Vector2f(2.0f/3, 1.0f/4)), //v8
                new Vertex(new Vector3f(-1.0f, 1.0f, -1.0f), new Vector2f(2.0f/3, 1.0f)), //v9
                new Vertex(new Vector3f(-1.0f, 1.0f, 1.0f), new Vector2f(1.0f/3, 1.0f)), //10
                new Vertex(new Vector3f(-1.0f, -1.0f, 1.0f), new Vector2f(1.0f/3, 1.0f/4)), //v11

                new Vertex(new Vector3f(-1.0f, 1.0f, -1.0f), new Vector2f(2.0f/3, 0.0f)), //v12
                new Vertex(new Vector3f(-1.0f, 1.0f, 1.0f), new Vector2f(1.0f/3, 0.0f)) //13
        }, new int[] {
                5, 2, 6,
                5, 1, 2,

                4, 6, 7,
                4, 5, 6,

                1, 3, 2,
                1, 0, 3,

                6, 9, 10,
                6, 2, 9,

                1, 11, 8,
                1, 5, 11,

                8, 13, 12,
                8, 11, 13
        });
    }

    public void render(Object3D object, Camera camera) {
        glBindVertexArray(object.getMesh().getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, object.getTexture().getTextureID());
        glUseProgram(object.getShader().getProgramID());
        object.getShader().setUniform("model", (new Matrix4f())
                        .scale(object.getScale())
                        .rotate((new Quaternionf()).rotateXYZ(
                                (float) Math.toRadians(object.getRotation().x),
                                (float) Math.toRadians(object.getRotation().y),
                                (float) Math.toRadians(object.getRotation().z)))
                        .translate(object.getPosition()));
        object.getShader().setUniform("view", (new Matrix4f())
                .rotate((new Quaternionf()).rotateXYZ(
                        (float) -Math.toRadians(camera.getRotation().x),
                        (float) -Math.toRadians(camera.getRotation().y),
                        (float) -Math.toRadians(camera.getRotation().z)))
                .translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z));
        object.getShader().setUniform("projection", camera.getViewMatrix());
        glDrawElements(GL_TRIANGLES, object.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
        glUseProgram(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void renderCubes(Cube[] cubes, Camera camera) {
        glBindVertexArray(cubeMesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, cubeMesh.getIBO());
        for (Cube cube : cubes) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, cube.getTexture().getTextureID());
            glUseProgram(cube.getShader().getProgramID());
            cube.getShader().setUniform("model", (new Matrix4f())
                    .translate(cube.getPosition())
                    .rotate((new Quaternionf()).rotateXYZ(
                            (float) Math.toRadians(cube.getRotation().x),
                            (float) Math.toRadians(cube.getRotation().y),
                            (float) Math.toRadians(cube.getRotation().z)))
                    .scale(cube.getScale()));
            cube.getShader().setUniform("view", (new Matrix4f())
                    .rotate((new Quaternionf()).rotateXYZ(
                            (float) -Math.toRadians(camera.getRotation().x),
                            (float) -Math.toRadians(camera.getRotation().y),
                            (float) -Math.toRadians(camera.getRotation().z)))
                    .translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z));
            cube.getShader().setUniform("projection", camera.getViewMatrix());
            glDrawElements(GL_TRIANGLES, cubeMesh.getIndices().length, GL_UNSIGNED_INT, 0);
        }
        glUseProgram(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void destroy() {
        cubeMesh.destroy();
    }
}
