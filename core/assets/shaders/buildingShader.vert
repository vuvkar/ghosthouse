attribute vec4 a_position;
attribute vec4 a_color;
attribute vec3 a_normal;

uniform mat4 u_projTrans;
uniform vec3 u_light;

varying vec4 v_color;
varying vec3 v_normal;
varying vec3 v_toLight;

void main() {
  v_color = a_color;
  v_normal = a_normal;
  v_toLight = u_light - a_position.xyz;

  gl_Position = u_projTrans * a_position;
}
