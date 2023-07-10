#version 400

layout(location = 0) in vec2 position;
layout(location = 1) in vec4 color;

//out vec4 color_pass;
//out vec4 lightPos;
//out vec3 position_pass;

uniform mat4 projView;
//uniform mat4 lightProjView;
uniform float boardWidth;

void main() {
//    color_pass = color;
    vec4 pos = vec4(position.x + int(gl_InstanceID % int(boardWidth)), position.y + (gl_InstanceID / int(boardWidth)), 0.0, 1.0);
//    lightPos = lightProjView * pos;
//    position_pass = vec3(position, 0);
    gl_Position = projView * pos;
}
