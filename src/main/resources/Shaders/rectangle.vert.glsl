#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

uniform bool isRight = true;
uniform float resolution;
uniform vec2 offset;
out vec2 texCoord;

void main() {
    gl_Position = vec4(aPos.x + offset.x, (aPos.y + offset.y) * resolution, aPos.z, 1.0);
    if (isRight)
        texCoord = aTexCoord;
    else
        texCoord = vec2(1.0 - aTexCoord.x, aTexCoord.y);
}
