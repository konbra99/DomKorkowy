#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

uniform vec2 offset;
out vec2 texCoord;

void main() {
    gl_Position = vec4(aPos.x + offset.x, (aPos.y + offset.y) * (16.0 / 9.0), aPos.z, 1.0);
    texCoord = aTexCoord;
}
