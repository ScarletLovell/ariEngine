#version 120

attribute vec3 position;
attribute vec3 color;

varying vec3 vertexColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    vertexColor = color;
    mat4 mvp = projection * view * model;
    gl_Position = mvp * vec4(position, 1.0);
}