#version 330 core

uniform sampler2D texSample;

out vec4 gl_FragColor;
in vec2 texCoord;

void main() {
    vec4 texColor = texture(texSample, texCoord);
    if (texColor.a < 0.1)
        discard;
    gl_FragColor = texColor;
}
