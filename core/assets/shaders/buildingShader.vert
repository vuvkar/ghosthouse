attribute vec4 a_position;
attribute vec4 a_color;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;
attribute vec2 a_binormal;

uniform mat4 u_projTrans;
uniform vec3 u_light;
uniform vec3 u_lightColor;


varying vec2 v_coords;
varying vec4 v_color;
varying vec3 v_normal;
varying vec3 v_toLight;
varying vec2 v_normal_map;

void main() {
  v_color = a_color;
  v_normal = a_normal;
  v_toLight = u_light - a_position.xyz;
  v_coords = a_texCoord0;
  v_normal_map = a_binormal;

  gl_Position = u_projTrans * a_position;
}
