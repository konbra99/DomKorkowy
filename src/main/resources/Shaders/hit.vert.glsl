#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

uniform bool isRight;
uniform float angle;
uniform float resolution;
uniform vec2 offset;
uniform vec3 centre;

out vec2 texCoord;

vec3 rotate(vec3 point, float angle, vec3 centre) {
    vec3 rotated = point;

    float rad = radians(angle);
    float co = cos(rad);
    float si = sin(rad);

    // shift
    rotated.x -= centre.x;
    rotated.y -= centre.y;

    // rotate
    rotated.x = rotated.x * co - rotated.y * si;
    rotated.y = rotated.x * si + rotated.y * co;

    // shift back
    rotated.x += centre.x;
    rotated.y += centre.y;

    return rotated;
}

void main() {
    vec3 position = rotate(aPos, angle, centre);
    gl_Position = vec4(position.x + offset.x, (position.y + offset.y) * resolution, position.z, 1.0);
    if (isRight)
        texCoord = aTexCoord;
    else
        texCoord = vec2(1.0 - aTexCoord.x, aTexCoord.y);
}
