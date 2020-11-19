package com.github.programmer3481.simple3D.engine;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {
    String path;
    private int width, height;
    private int textureID;
    ByteBuffer image;

    public Texture(String path) {
        this.path = path;
        textureID = glGenTextures();
        loadImage(path);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void destroy() {
        glDeleteTextures(textureID);
    }

    public float getWidth() {
        return (float)width;
    }

    public float getHeight() {
        return (float)height;
    }

    public int getTextureID() {
        return textureID;
    }

    private void loadImage(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            STBImage.stbi_set_flip_vertically_on_load(true);
            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) {
                System.err.println("Couldn't load" + path);
            }
            width = w.get();
            height = h.get();
        }
    }
}
