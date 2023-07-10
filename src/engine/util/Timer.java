package engine.util;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
    private float lastTime;

    /**
     * Create a new timer
     */
    public Timer() {
        reset();
    }

    /**
     * Reset timer back to zero
     */
    public void reset() {
        lastTime = (float)glfwGetTime();
    }

    /** Return elapsed seconds and reset timer
     * @return seconds passed since creation or last reset
     */
    public float elapsed() {
        float temp = lastTime;
        lastTime = (float) glfwGetTime();
        return (float) glfwGetTime() - temp;
    }

    public float elapsedWithoutReset() {
        return (float) glfwGetTime() - lastTime;
    }
}
