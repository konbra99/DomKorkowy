package graphics;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    public static boolean LEFT = false, RIGHT = false, UP = false, DOWN = false;
    public static boolean SPACE = false, L_CTRL = false, ONE_KEY = false, TWO_KEY = false;
    public static boolean ATTACK = false;
    public static boolean L_SHIFT = false;
    public static float MOUSE_X = -2.0f, MOUSE_Y = -2.0f;
    public static float MOUSE_POS_X = 0.0f, MOUSE_POS_Y = 0.0f;
    public static float MOUSE_SCROLL_X = 0.0f, MOUSE_SCROLL_Y = 0.0f;
    public static ArrayList<Character> singlePressed = new ArrayList<>();

    public static void handleKeyboard(int key, int action) {
        // przyciski specjalne
        boolean isPressed = action == GLFW_PRESS;
        switch (key) {
            case GLFW_KEY_LEFT, GLFW_KEY_A -> LEFT = isPressed;
            case GLFW_KEY_RIGHT, GLFW_KEY_D -> RIGHT = isPressed;
            case GLFW_KEY_UP, GLFW_KEY_W -> UP = isPressed;
            case GLFW_KEY_DOWN, GLFW_KEY_S -> DOWN = isPressed;
            case GLFW_KEY_SPACE -> SPACE = isPressed;
            case GLFW_KEY_F -> ATTACK = isPressed;
            case GLFW_KEY_1 -> ONE_KEY = isPressed;
            case GLFW_KEY_2 -> TWO_KEY = isPressed;
            case GLFW_KEY_LEFT_CONTROL -> L_CTRL = isPressed;
            case GLFW_KEY_LEFT_SHIFT -> L_SHIFT = isPressed;
        }

        if (key == GLFW_KEY_BACKSPACE && action == GLFW_PRESS) {
            singlePressed.add('|');
        }
    }

    public static void handleText(int codePoint) {
        if (31 < codePoint && codePoint < 123 && codePoint != 96) {
            singlePressed.add((char) codePoint);
        }
    }

    public static void handleMouse(int button, int action, long window) {
        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
            DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, b1, b2);
            MOUSE_X = (float) ((2 * b1.get(0) / Config.WIDTH) - 1.0);
            MOUSE_Y = (float) ((((2 * (Config.HEIGHT - b2.get(0))) / Config.HEIGHT) - 1.0) * (9.0 / 16.0));
        }
    }

    public static void handleMouseScroll(double dx, double dy) {
        MOUSE_SCROLL_X = (float)dx;
        MOUSE_SCROLL_Y = (float)dy;
    }

    public static void handleMousePosition(double dx, double dy) {
        MOUSE_POS_X = (float) ((2 * dx / Config.WIDTH) - 1.0);
        MOUSE_POS_Y = (float) ((((2 * (Config.HEIGHT - dy)) / Config.HEIGHT) - 1.0) * (9.0 / 16.0));
    }

    public static void resetInputs() {
        MOUSE_X = -2.0f;
        MOUSE_Y = -2.0f;
        MOUSE_SCROLL_X = 0.0f;
        MOUSE_SCROLL_Y = 0.0f;
        singlePressed.clear();
    }
}
