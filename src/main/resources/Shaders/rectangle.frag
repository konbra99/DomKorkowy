#version 330 core

uniform sampler2D texSample;
uniform float myAlpha = 1.0;

out vec4 myColor;
in vec2 texCoord;

void main() {
    vec4 texColor = texture(texSample, texCoord);
    if (texColor.a < 0.1)
        discard;

    myColor = vec4(texColor.rgb, myAlpha*texColor.a);
}
