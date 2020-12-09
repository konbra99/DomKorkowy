#version 330 core

uniform sampler2D texSample;
uniform vec4 color;

out vec4 myColor;
in vec2 texCoord;

void main() {
    vec4 texColor = texture(texSample, texCoord);
    if (texColor.r < 0.1)
        discard;

    myColor = vec4(texColor.r, texColor.r, texColor.r, 1.0) * color;
}
