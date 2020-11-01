#version 330 core

uniform sampler2D texSample;

out vec4 myColor;
in vec2 texCoord;

void main() {
    myColor = texture(texSample, texCoord);
}
