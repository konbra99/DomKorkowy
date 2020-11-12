package graphics;

import logic.Player;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine implements Runnable {
    private Window window;

    public static Player KORKOWY;
    public static Rectangle PLATFORM;
    public static Rectangle BACKGROUND;

    @Override
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window.getWindowHandle());
        glfwDestroyWindow(window.getWindowHandle());
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void init() {
        window = new Window();
        GL.createCapabilities();
    }

    private void action() {
        BACKGROUND.draw();

        if (Input.MOUSE_X > -2.0f && Input.MOUSE_Y > -2.0f) {
            if (PLATFORM.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
                PLATFORM.move(-0.3f, 0.2f);
            }
        }
        PLATFORM.draw();
        KORKOWY.move();

        Input.resetMouse();
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        KORKOWY = new Player(-0.7f, -0.4f, 0.3f, 0.3f, "korkowa_postac.png");
        PLATFORM = new Rectangle(-0.2f, -0.2f, 1.0f, 0.1f);
        BACKGROUND = new Rectangle(-1.0f, -1.0f * 9.0f / 16.0f, 2.0f, 2.0f * 9.0f / 16.0f);

        PLATFORM.initGL("platforma.png");
        BACKGROUND.initGL("bg.jpg");

        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        // glowna petla programu
        while (!glfwWindowShouldClose(window.getWindowHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            action();

            glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers

            // sprawdza czy zaszly jakies eventy
            glfwPollEvents();
        }
    }
}
