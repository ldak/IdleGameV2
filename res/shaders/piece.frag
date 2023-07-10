#version 400

in vec3 color_pass;
in vec3 surfaceNormal;
in vec3 lightVector;

out vec4 fragColor;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(lightVector);

    float lightDot = dot(unitNormal, unitLightVector);
    float brightness = max(lightDot, 0.0);
    vec3 diff = brightness * vec3(1, 1, 1);

    fragColor = vec4(diff, 1) * vec4(color_pass / 255, 1.0);
}