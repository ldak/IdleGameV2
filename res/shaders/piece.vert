#version 400

layout(location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec3 color;
layout (location = 3) in mat4 mat;

out vec3 color_pass;
out vec3 surfaceNormal;
out vec3 lightVector;

uniform mat4 projView;
uniform vec3 lightPosition;

void main() {
    vec4 worldPos = mat * vec4(position, 1.0);
    color_pass = color;
    surfaceNormal = (mat * vec4(normal, 0.0)).xyz;
    lightVector = lightPosition - worldPos.xyz;
    gl_Position = projView * worldPos;
}
