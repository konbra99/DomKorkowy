#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

uniform float angle;
uniform vec3 centre;
uniform float resolution;
uniform vec2 offset;
out vec2 texCoord;

vec3 rotate(vec3 point) {
    vec3 rotated;

    float rad = radians(angle);
    float co = cos(rad);
    float si = sin(rad);

    // shift
    point.x -= centre.x;
    point.y -= centre.y;

    // rotate
    rotated.x = point.x * co - point.y * si;
    rotated.y = point.x * si + point.y * co;

    // shift back
    rotated.x += centre.x;
    rotated.y += centre.y;

    return rotated;
}

void main() {
    vec3 position = rotate(aPos);
    gl_Position = vec4(position.x + offset.x, (position.y + offset.y) * resolution, position.z, 1.0);
    texCoord = aTexCoord;
}
