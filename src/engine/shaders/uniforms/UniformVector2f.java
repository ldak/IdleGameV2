package engine.shaders.uniforms;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL45;

import java.nio.FloatBuffer;

public class UniformVector2f extends Uniform{
    public UniformVector2f(String name) {
        super(name);
    }

    public void loadVector2f(Vector2f vec) {
        GL45.glProgramUniform2fv(programID, location, new float[] {vec.x, vec.y});
    }

    public void loadVector2f(float x, float y) {
        GL45.glProgramUniform2fv(programID, location, new float[] {x, y});
    }

    public void loadVector2f(FloatBuffer vec) {
        GL45.glProgramUniform2fv(programID, location, vec);
    }
}
