package engine;

import engine.util.Matrix;
import game.Board;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private static final float SPEED = 15;
    private float distanceFromCenter;
    private final float standardDistanceFromCenter;

    public Vector3f position;
    public Vector3f rotation;

    private Matrix4f viewMatrix;
    private Matrix4f inverseViewMatrix;
    private Matrix4f projectionMatrix;
    private Matrix4f inverseProjectionMatrix;
    private Matrix4f projViewMatrix;

    private FrustumIntersection frustrum;

    private int windowX;
    private int windowY;

    private Vector3f center;


    public Camera(Window window, Board board) {
        this.rotation = new Vector3f(-40, 0, -80);
        this.position = new Vector3f((float) board.getWidth() / 2,0 , 5);
        this.center = new Vector3f((float) board.getWidth() / 2,(float) board.getHeight() / 2,0);
        this.standardDistanceFromCenter = board.getWidth() * 1.5f;

        this.frustrum = new FrustumIntersection();
        this.windowX = window.getWindowSize().x;
        this.windowY = window.getWindowSize().y;

        this.projectionMatrix = Matrix.makeProjectionMatrix(window);
        this.inverseProjectionMatrix = new Matrix4f(projectionMatrix).invert();
        this.inverseViewMatrix = new Matrix4f();
        this.projViewMatrix = new Matrix4f();
    }

    public void update(Window window, float delta) {
        checkForWindowResize(window);
        useWindowInput(window,delta);

        calculateZoom();
        calculateCameraPosition();
        createMatrix();

    }

    private void createMatrix() {
        viewMatrix = Matrix.makeViewMatrix(this);
        projViewMatrix.set(projectionMatrix).mul(viewMatrix);
        frustrum.set(projViewMatrix, false);
        viewMatrix.invert(inverseViewMatrix);
        projectionMatrix.invert(inverseProjectionMatrix);
    }

    private void useWindowInput(Window window, float delta) {
        if(window.isKeyDown(GLFW.GLFW_KEY_W)) {
            this.position.y += SPEED * delta;
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_S)) {
            this.position.y -= SPEED * delta;
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_A)) {
            this.position.x -= SPEED * delta;
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_D)) {
            this.position.x += SPEED * delta;
        }

        //rotate
        if (window.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            this.rotation.z -= 100f * delta;
        } else if (window.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
            this.rotation.z += 100f * delta;
        }
        if (window.isKeyDown(GLFW.GLFW_KEY_UP)) {
            if (this.rotation.x >= 0)
                return;

            this.rotation.x += 100f * delta;
        } else if (window.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
            if (this.rotation.x <= -80 )
                return;
            this.rotation.x -= 100f * delta;
        }
    }

    private void checkForWindowResize(Window window) {
        if (window.getWindowSize().x!=windowX||window.getWindowSize().y!=windowY){
            this.windowX = window.getWindowSize().x;
            this.windowY = window.getWindowSize().y;
            this.projectionMatrix = Matrix.makeProjectionMatrix(window);
            this.inverseProjectionMatrix = new Matrix4f(projectionMatrix).invert();
        }
    }

    private void calculateCameraPosition() {
        float offsetX = (float) (this.distanceFromCenter  * Math.sin(Math.toRadians(this.rotation.x)) * Math.sin(Math.toRadians(this.rotation.z)));
        float offsetY  = (float) (this.distanceFromCenter * Math.sin(Math.toRadians(this.rotation.x)) * Math.cos(Math.toRadians(this.rotation.z)));
        position.x = center.x + offsetX;
        position.y = center.y + offsetY;
        position.z = center.z + (float) (this.distanceFromCenter * Math.cos(Math.toRadians(this.rotation.x)));
    }


    public Matrix4f getViewMatrix() {
        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getInverseProjectionMatrix() {
        return this.inverseProjectionMatrix;
    }

    public Matrix4f getInverseViewMatrix() {
        return this.inverseViewMatrix;
    }

    public Matrix4f getProjectionViewMatrix() {
        return this.projViewMatrix;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public FrustumIntersection getFrustrum() {
        return this.frustrum;
    }

    public void rotateView(Vector2f vec, float delta) {
        this.rotation.z += vec.x * 1f * delta;
        if (this.rotation.x >= 0 && vec.y > 0)
            return;

        if (this.rotation.x <= -80 && vec.y < 0)
            return;

        this.rotation.x += vec.y * 1f * delta;

    }

    public void calculateZoom() {
        float zoomLevel = (float) (Window.getMouseWheel() * 1f);
        distanceFromCenter = this.standardDistanceFromCenter - zoomLevel;
    }
}
