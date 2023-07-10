#version 400

//in vec4 color_pass;
//in vec4 lightPos;
//in vec3 position_pass;

out vec4 fragColor;

//uniform sampler2D shadowMap;

//float calcShadow(vec4 pos) {
//    float bias = 0.005;
//    vec3 projCoords = pos.xyz / pos.w;
//    projCoords = projCoords * 0.5 + 0.5;
//    float closestDepth = texture(shadowMap, projCoords.xy).r;
//    float currentDepth = projCoords.z;
//
//    float shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;
//    return shadow;
//}

void main() {
//    float shadow =  calcShadow(lightPos);
    float shadow = 0;
    fragColor = vec4(0, 0, 0, 0);
//    if(position_pass.x >= 0.92 || position_pass.x <= 0.08 || position_pass.y >= 0.92 || position_pass.y <= 0.08) {
//        fragColor = vec4(color_pass.xyz / 255 * (1.1 - shadow), 1.0);
//    } else {
//        if(color_pass.w == 255) {
//            fragColor = vec4(vec3(0, 0, 1) * (1.1 - shadow), 1.0);
//        } else {
//            fragColor = vec4(color_pass.xyz / 255 * (1.1 - shadow), 1.0);
//        }
//    }
}