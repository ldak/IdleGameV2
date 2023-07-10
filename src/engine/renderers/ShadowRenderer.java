package engine.renderers;

import engine.Window;
import engine.interfaces.Disposable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL45;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class ShadowRenderer implements Disposable {
    private static final int SHADOW_WIDTH = 2048;
    private static final int SHADOW_HEIGHT = 2048;

    public static final Vector3f sunPosition = new Vector3f(15, 15, 15);
    public static final Vector3f sunLookAt = new Vector3f(0, 0, 0);

    private int fbo, depthMap;
    private Matrix4f mat;

    public ShadowRenderer() {
        this.fbo = glGenFramebuffers();
        this.depthMap = glGenTextures();

        this.mat = new Matrix4f()
                .ortho(-10f, 10, -10, 10, 1, 50)
                .mul(new Matrix4f().lookAt(sunPosition, sunLookAt, new Vector3f(0, 0, 1)));

        GL45.glBindTexture(GL_TEXTURE_2D, depthMap);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT,
                SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMap, 0);
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void startPass() {
        glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        glClear(GL_DEPTH_BUFFER_BIT);
    }

    public void endPass(Window window) {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, window.getFramebufferSize().x, window.getFramebufferSize().y);
    }

    public Matrix4f getShadowProjViewMatrix() {
        return this.mat;
    }

    @Override
    public void dispose() {
        glDeleteFramebuffers(fbo);
        glDeleteTextures(depthMap);
    }
}
