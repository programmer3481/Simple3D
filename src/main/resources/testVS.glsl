#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 textureCoord;
layout(location = 2) in vec3 color;

out vec2 passTextureCoord;
out vec3 passColor;

uniform mat4 matrix;

void main() {
    gl_Position = matrix * vec4(position, 1.0);
    passTextureCoord = textureCoord;
    passColor = color;
}
