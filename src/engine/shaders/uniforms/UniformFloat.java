package engine.shaders.uniforms;

import org.lwjgl.opengl.GL45;

import java.nio.FloatBuffer;

public class UniformFloat extends Uniform{
    public UniformFloat(String name) {
        super(name);
    }

    public void loadFloat(float v) {
        GL45.glProgramUniform1fv(programID, location, new float[]{v});
    }

    public void loadFloat(FloatBuffer v) {
        GL45.glProgramUniform1fv(programID, location, v);
    }
}
