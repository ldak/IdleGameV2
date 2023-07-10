package engine.inputs;

import engine.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MousePicker {
    private Vector3f ray;

    private Matrix4f invertedProjectionMatrix;
    private Matrix4f invertedViewMatrix;
    private Camera camera;

    public MousePicker(Camera camera) {
        this.camera = camera;
        this.invertedProjectionMatrix = camera.getInverseProjectionMatrix();
        this.invertedViewMatrix = camera.getInverseViewMatrix();
    }

    public Vector3f getRay() {
        return this.ray;
    }

    public void update(float mouseX, float mouseY) {
        this.invertedViewMatrix = camera.getInverseViewMatrix();
        ray = calculateRay(mouseX, mouseY);
    }

    private Vector3f calculateRay(float mouseX, float mouseY) {
        Vector4f clip = this.invertedProjectionMatrix.transform(new Vector4f(mouseX, mouseY, -1f, 1f));
        clip.z = -1f;
        clip.w = 0;
        Vector4f world = this.invertedViewMatrix.transform(clip);
        Vector3f mouse = new Vector3f(world.x, world.y, world.z);
        mouse.normalize();
        return mouse;
    }
}
