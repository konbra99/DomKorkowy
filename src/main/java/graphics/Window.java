package graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long windowHandle;

    public static boolean LEFT = false, RIGHT = false, UP = false, DOWN = false;

    public Window() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        windowHandle = glfwCreateWindow(Main.WIDTH, Main.HEIGHT, "Hello World!", NULL, NULL);
        if (windowHandle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
                DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
                DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
                glfwGetCursorPos(window, b1, b2);
                System.out.println("x : " + b1.get(0) + ", y = " + b2.get(0));
            }
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop

            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS) {
                LEFT = true;
            }
            if (key == GLFW_KEY_LEFT && action == GLFW_RELEASE) {
                LEFT = false;
            }

            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS) {
                RIGHT = true;
            }
            if (key == GLFW_KEY_RIGHT && action == GLFW_RELEASE) {
                RIGHT = false;
            }

            if (key == GLFW_KEY_UP && action == GLFW_PRESS) {
                UP = true;
            }
            if (key == GLFW_KEY_UP && action == GLFW_RELEASE) {
                UP = false;
            }

            if (key == GLFW_KEY_DOWN && action == GLFW_PRESS) {
                DOWN = true;
            }
            if (key == GLFW_KEY_DOWN && action == GLFW_RELEASE) {
                DOWN = false;
            }
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowHandle, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(windowHandle);
    }

    public long getWindowHandle() {
        return windowHandle;
    }
}
