#version 330 core

in vec2 passTextureCoord;
in vec3 passColor;

out vec4 outColor;

uniform sampler2D tex1;

void main() {
    outColor = texture(tex1, passTextureCoord) * vec4(passColor, 1.0);
}
