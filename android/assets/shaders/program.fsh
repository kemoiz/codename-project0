#version 100

precision lowp float;


varying vec4 v_color;
varying vec2 v_texCoord0;
uniform sampler2D u_sampler2D;
uniform vec4 u_neon;

const vec3 GRAYSCALE = vec3(0.3, 0.59, 0.11);

// 0 = totally desaturated
// 1 = saturation unchanged
// higher = increase saturation
vec3 adjustSaturation(vec3 color, float saturation);



vec3 adjustSaturation(vec3 color, float saturation) {
   vec3 grey = vec3(dot(color, GRAYSCALE));  // precise
   //vec3 grey = vec3((color.r+color.g+color.b)*0.333);   // simple
   return mix(grey, color, saturation);   // correct
}

void main() {
 vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
  color.rgba = (color.rgba) + u_neon;
 
//  color.rgb = adjustSaturation(color.rgb, 3);

  gl_FragColor = color;
}

