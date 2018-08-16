#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec3 v_normal;
varying vec3 v_toLight;
varying vec2 v_coords;
varying vec2 v_normal_map;

uniform sampler2D texture;
uniform sampler2D u_normalMap;

uniform vec3 u_light;
uniform vec3 u_lightColor;

void main() {

    vec3 faceNormal = v_normal;
    vec4 texNormal = texture2D(u_normalMap, v_normal_map);
    vec3 normal = normalize(texNormal.rgb)*2-1;
    texNormal.y=1-texNormal.y;

    vec3 finalNormal = (faceNormal + normal);

    //finalNormal=faceNormal;
    float dot = dot(normalize(v_toLight), normalize(finalNormal));
    vec4 zibil =  texture2D(texture, v_coords);
    zibil.rgb *= max(dot, 0.8);
    if(zibil.a < 0.1)
       discard;
    gl_FragColor = zibil * vec4(u_lightColor, 1.0);


}