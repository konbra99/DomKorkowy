#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

uniform vec2 size;
uniform float offsetX, offsetY;
out vec2 texCoord;

void main() {
    gl_Position = vec4(aPos.x * size.x + offsetX, aPos.y * size.y + offsetY, aPos.z, 1.0);
    texCoord = aTexCoord;
}
