package engine;

import engine.interfaces.Disposable;
import engine.interfaces.MouseCallback;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;

public class Window implements Disposable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FOV = 50;

    private final long handle;
    private final Vector2i framebufferSize;
    private final Vector2i windowSize;
    private final Vector2f cursorPosition;
    private final Vector2f NDCPosition;
    private MouseCallback mouseCallback;
    private Callback debugCallback = null;

    private final DoubleBuffer x = MemoryUtil.memAllocDouble(1);
    private final DoubleBuffer y = MemoryUtil.memAllocDouble(1);

    private static double mouseWheel;


    public Window(String name) {
        this(name, false);
    }

    public Window(String name, boolean debug) {
        System.out.println("GLFW Version: " + glfwGetVersionString());

        //glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

        glfwInit();

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        //glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, debug ? GLFW_TRUE : GLFW_FALSE);

        handle = glfwCreateWindow(WIDTH, HEIGHT, name, 0, 0);

        glfwMakeContextCurrent(handle);

        // TODO uncomment for vsync
        glfwSwapInterval(1);

        GL.createCapabilities();

        //this.debugCallback = GLUtil.setupDebugMessageCallback(System.err);

        GL11.glClearColor(0, 0, 0.2f, 1);

        try (MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer x = stack.callocInt(1);
            IntBuffer y = stack.callocInt(1);
            glfwGetWindowSize(handle, x, y);
            this.windowSize = new Vector2i(x.get(0), y.get(0));
            glfwGetFramebufferSize(handle, x, y);
            this.framebufferSize = new Vector2i(x.get(0), y.get(0));
            DoubleBuffer mouseX = stack.callocDouble(1);
            DoubleBuffer mouseY = stack.callocDouble(1);
            glfwGetCursorPos(handle, mouseX, mouseY);
            this.cursorPosition = new Vector2f((float)mouseX.get(0), (float)mouseY.get(0));
            this.NDCPosition = new Vector2f((2.0f * cursorPosition.x) / windowSize.x - 1.0f, 1.0f - (2.0f * cursorPosition.y) / windowSize.y);
        }

        System.out.println(GL11.glGetString(GL11.GL_RENDERER));

        glfwSetFramebufferSizeCallback(handle, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                framebufferSize.set(width, height);
                glViewport(0, 0, width, height);
            }
        });

        glfwSetMouseButtonCallback(handle, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if(mouseCallback != null) {
                    mouseCallback.mouseButtonCallback((int) cursorPosition.x, (int) cursorPosition.y, button, action == GLFW_PRESS);
                }
            }
        });

        glfwSetCursorPosCallback(handle, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                cursorPosition.set((float) xpos, (float) ypos);
                NDCPosition.set((float)((2.0f * xpos) / windowSize.x - 1.0f), (float)(1.0f - (2.0f * ypos) / windowSize.y));
            }
        });

        glfwSetWindowSizeCallback(handle, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                windowSize.set(width, height);
            }
        });

        glfwSetScrollCallback(handle, new GLFWScrollCallback() {
            @Override
            public void invoke(long arg0, double arg1, double arg2) {
                mouseWheel += arg2;
            }
        } );
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public void poll() {
        glfwPollEvents();
    }

    public void display() {
        glfwSwapBuffers(handle);
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(handle, title);
    }

    public void setCursorMode(boolean enabled) {
        glfwSetInputMode(handle, GLFW_CURSOR, enabled ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
    }

    public void setShouldClose() {
        glfwSetWindowShouldClose(handle, true);
    }

    public boolean isKeyDown(int key) {
        return glfwGetKey(handle, key) == GLFW_PRESS;
    }

    public boolean isMouseButtonDown(int button) {
        return glfwGetMouseButton(handle, button) == GLFW_PRESS;
    }

    public Vector2i getWindowSize() {
        return this.windowSize;
    }

    public void registerMouseCallback(MouseCallback mouseCallback) {
        this.mouseCallback = mouseCallback;
    }

    public Vector2i getFramebufferSize() {
        return this.framebufferSize;
    }

    public Vector2f getCursorPosition() {
        return this.cursorPosition;
    }

    public Vector2f getNDCPosition() {
        return this.NDCPosition;
    }

    public static double getMouseWheel() {
        return mouseWheel;
    }

    @Override
    public void dispose() {
        if (debugCallback != null) {
            debugCallback.free();
        }
        MemoryUtil.memFree(x);
        MemoryUtil.memFree(y);
        glfwDestroyWindow(handle);
        glfwTerminate();
    }
}
