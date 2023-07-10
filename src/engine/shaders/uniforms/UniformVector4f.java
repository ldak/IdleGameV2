package engine.shaders.uniforms;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL45;

import java.awt.*;
import java.nio.FloatBuffer;

public class UniformVector4f extends Uniform{
    public UniformVector4f(String name) {
        super(name);
    }

    public void loadVector4f(Vector4f vec) {
        GL45.glProgramUniform4fv(programID, location, new float[] {vec.x, vec.y, vec.z, vec.w});
    }

    public void loadVector4f(float x, float y, float z, float w) {
        GL45.glProgramUniform4fv(programID, location, new float[] {x, y, z, w});
    }

    public void loadColor(Color color) {
        GL45.glProgramUniform4fv(programID, location, new float[] {color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
    }

    public void loadVector4f(FloatBuffer vec) {
        GL45.glProgramUniform4fv(programID, location, vec);
    }
}
