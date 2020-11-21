#version 330 core

uniform bool isRight;
uniform sampler2D texSample;
uniform float myAlpha;
uniform float xFade;

out vec4 myColor;
in vec2 texCoord;

void main() {
    vec4 texColor = texture(texSample, texCoord);
    myColor = vec4(texColor.r, texColor.g, texColor.b, texColor.a * myAlpha);

    if (((gl_FragCoord.x / 1280.0) > xFade) == isRight)
        discard;
}
