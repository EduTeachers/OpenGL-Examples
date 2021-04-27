#version 330 core

out vec4 FragColor;

in vec3 outColor;
in vec2 outTexture;

uniform sampler2D ourTexture;

void main()
{
    FragColor = texture(ourTexture, outTexture);
    //FragColor = vec4(outColor, 1.0f);
}
