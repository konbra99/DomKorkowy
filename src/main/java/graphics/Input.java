package graphics;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class Input {
    public static boolean LEFT = false, RIGHT = false, UP = false, DOWN = false;
    public static boolean SPACE = false;
    public static float MOUSE_X = -2.0f, MOUSE_Y = -2.0f;

    public static void handleKeyboard(int key, int action) {
        boolean isPressed = action == GLFW_PRESS;
        switch (key) {
            case GLFW_KEY_LEFT:
            case GLFW_KEY_A:
                LEFT = isPressed;
                break;
            case GLFW_KEY_RIGHT:
            case GLFW_KEY_D:
                RIGHT = isPressed;
                break;
            case GLFW_KEY_UP:
            case GLFW_KEY_W:
                UP = isPressed;
                break;
            case GLFW_KEY_DOWN:
            case GLFW_KEY_S:
                DOWN = isPressed;
                break;
            case GLFW_KEY_SPACE:
                SPACE = isPressed;
                break;
        }
    }

    public static void handleMouse(int button, int action, long window) {
        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
            DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, b1, b2);
            MOUSE_X = (float) ((2 * b1.get(0) / Main.WIDTH) - 1.0);
            MOUSE_Y = (float) ((((2 * (Main.HEIGHT - b2.get(0))) / Main.HEIGHT) - 1.0) * (9.0 / 16.0));
        }
    }

    public static void resetMouse() {
        MOUSE_X = -2.0f;
        MOUSE_Y = -2.0f;
    }
}
