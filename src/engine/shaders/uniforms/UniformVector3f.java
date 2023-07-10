package engine.shaders.uniforms;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL45;

import java.awt.*;
import java.nio.FloatBuffer;

public class UniformVector3f extends Uniform{
    public UniformVector3f(String name) {
        super(name);
    }

    public void loadVector3f(Vector3f vec) {
        GL45.glProgramUniform3fv(programID, location, new float[] {vec.x, vec.y, vec.z});
    }

    public void loadVector3f(float x, float y, float z) {
        GL45.glProgramUniform3fv(programID, location, new float[] {x, y, z});
    }

    public void loadColor(Color color) {
        GL45.glProgramUniform3fv(programID, location, new float[] {color.getRed(), color.getGreen(), color.getBlue()});
    }

    public void loadVector3f(FloatBuffer vec) {
        GL45.glProgramUniform3fv(programID, location, vec);
    }
}
