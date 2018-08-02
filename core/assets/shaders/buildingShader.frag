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

//    vec4 alpha = texture2D(texture, texcoord).aaaa;
//        float dot = dot(normalize(v_toLight), normalize(v_normal));
//        vec4 zibil = alpha*gl_Color+negalpha*vec4(1,1,1,0);
//        zibil.rgb *= max(dot, 0.2);
//    gl_FragColor = alpha*vec4(red,green,blue,calpha);
//
//    vec4 alpha = texture2D(texture, v_coords);
//    vec4 negalpha = alpha*vec4(-1,-1,-1,1)+vec4(1,1,1,0);

vec4 zibil = texture2D(texture, v_coords);
vec4 alpha = texture2D(texture, v_coords).aaaa;
float dot = dot(normalize(v_toLight), normalize(v_normal));
zibil.rgb *= dot;
zibil *= alpha;

//    if(zibil.a == 0.0)
//    {
//    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
//    }
//    else {
        gl_FragColor =  zibil;
   // }

}