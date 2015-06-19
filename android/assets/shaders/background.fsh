#version 100

 precision lowp float;


varying vec4 v_color;
varying vec2 v_texCoord0;


uniform float time;

uniform sampler2D u_sampler2D;
uniform vec4 u_neon;
float t, cx, cy;
vec4 color;

void main() {
   color = texture2D(u_sampler2D, v_texCoord0) * v_color;

  t = time * 0.05;


 cx = (gl_FragCoord.x/ 10.0) + 0.5*sin(t/5.0);
  cy = 100.0 - (gl_FragCoord.y/ 10.0) + 0.5*cos(t/3.0);
  

  color.r = sin(0.24*((gl_FragCoord.x/100.0)*sin(t/3.0)+(gl_FragCoord.y/100.0)*cos(t/5.0))+t) - sin(sqrt(0.3*(cx*cx+t*cy)+1.0)+t);

 color.b =  color.r * 0.5 + sin(sqrt(0.3*(cx*cx+cy*cy)+1.0)+t);

 color.g = color.b - sin(sqrt(0.14*(cx*cx+cy*cy)+1.0)+t);

//color.a = sin(gl_FragCoord.x/10.0 + t) ;
color.a = 1.0;
 

  gl_FragColor = color;
}

