package graphics;

import graphics.gui.*;
import graphics.gui.TextArea;
import logic.*;
import map.MapManager;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine implements Runnable {
    public enum GAME_STATE {
        MENU,
        BROWSER,
        GAMEPLAY,
        MULTIPLAYER,
        EDITOR,
        EXIT
    }

    private Window window;

    public static int FRAMES;
    public static GAME_STATE STATE;
    public static FontLoader fontLoader;
    public static TextArea active;
    public static MenuContext menu;
    public static BrowserContext browser;
    public static GameplayContext gameplay;
    public static EditorContext editor;
    public static Context activeContext;

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
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); tryb debuggerski

        fontLoader = new FontLoader();
        fontLoader.loadFont("msgothic.bmp");

        menu = new MenuContext("background/menubg.jpg");
        browser = new BrowserContext("background/menubg.jpg");
        gameplay = new GameplayContext();
        editor = new EditorContext();
        activeContext = menu;
        STATE = GAME_STATE.MENU;
    }

    private void renderFPS(double previous, double current) {
        int avg = (int) Math.floor(1.0 / (current - previous));
        fontLoader.renderText("fps: " + avg, "msgothic.bmp",
                0.65f, 0.9f, 0.05f, 0.08f,
                0.0f, 1.0f, 0.0f, 1.0f);
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        FRAMES = 0;

        //KORKOWY = new Player(-0.7f, -0.8f, 0.08f, 0.18f, "korkowa_postac.png");
        double current = glfwGetTime();
        double previous;
        int avg = 0;

        // glowna petla programu
        while (!glfwWindowShouldClose(window.getWindowHandle()) && STATE != GAME_STATE.EXIT) {
            previous = current;
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // input
            glfwPollEvents();

            activeContext.update();
            Input.resetInputs();
            activeContext.draw();

            fontLoader.renderText("fps: " + avg, "msgothic.bmp",
                    0.65f, 0.9f, 0.05f, 0.08f,
                    0.0f, 1.0f, 0.0f, 1.0f);

            glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers
            current = glfwGetTime();
            avg = (int) Math.floor(1.0 / (current - previous));
            ++FRAMES;
        }
    }
}
