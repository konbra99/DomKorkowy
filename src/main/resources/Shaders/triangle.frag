#version 330 core

uniform sampler2D texSample;

out vec4 gl_FragColor;
in vec2 texCoord;

void main() {
    gl_FragColor = texture(texSample, texCoord);
}
