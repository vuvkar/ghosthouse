#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec3 v_normal;
varying vec3 v_toLight;
varying vec2 v_coords;

uniform sampler2D texture;

uniform vec3 u_light;

void main() {
    float dot = dot(normalize(v_toLight), normalize(v_normal));
    vec4 zibil =  texture2D(texture, v_coords);
    zibil.rgb *= max(dot, 0.0);
    gl_FragColor =  zibil;

}