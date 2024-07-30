#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main()
{
    vec4 color = texture2D(u_texture, v_texCoords);
    if (color.a > 0.0) {
        gl_FragColor = vec4(1.0, 0.0, 0.0, color.a);
    } else {
        gl_FragColor = color;
    }
}
