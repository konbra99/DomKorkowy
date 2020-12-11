package graphics;

import com.google.gson.JsonObject;
import graphics.gui.Button;
import graphics.gui.TextArea;
import logic.*;
import map.MapManager;
import map.json.JsonUtils;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
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

    public static MapManager map;
    public static Player KORKOWY;
    public static Rectangle HEALTHBAR;
    public static Rectangle background;
    public static int FRAMES;
    public static GAME_STATE STATE;
    public static FontLoader fontLoader;
    public static TextArea active;
    public Menu menu;
    public static MapBrowser browser;

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
        map = new MapManager();
        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        background = new Rectangle(-1.0f, -1.0f, 2.0f, 2.0f);
        background.initGL("background/menubg.jpg", "rectangle.vert.glsl", "rectangle.frag");
        HEALTHBAR = new Rectangle(-1.0f, 0.9f, 0.18f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
        fontLoader = new FontLoader();
        fontLoader.loadFont("msgothic.bmp");

        STATE = GAME_STATE.MENU;
        menu = new Menu();
        browser = new MapBrowser();

        // init temp map
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        FRAMES = 0;

        KORKOWY = new Player(-0.7f, -0.8f, 0.08f, 0.18f, "korkowa_postac.png");
        active = new TextArea(0.5f, 0.5f, 0.4f, 0.1f, 0.04f, 0.1f,
                "msgothic.bmp", 0.7f, 0.2f, 0.6f, 1.0f);
        Button button = new Button(0.5f, 0.3f, 0.4f, 0.1f,
                () -> System.out.println(active.Clear()));
        button.setText("czysc", "msgothic.bmp", 0.04f, 0.1f);

        double current = glfwGetTime();
        double previous;
        int avg = 0;

        // glowna petla programu
        while (!glfwWindowShouldClose(window.getWindowHandle()) && STATE != GAME_STATE.EXIT) {
            previous = current;
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // input
            glfwPollEvents();

            background.draw();
            if (STATE == GAME_STATE.MENU) {
                menu.update();
                menu.draw();
            } else if (STATE == GAME_STATE.BROWSER) {
                browser.update();
                browser.draw();
            } else if (STATE == GAME_STATE.GAMEPLAY) {
                // update
                map.move();
                map.update();
                KORKOWY.move();
                KORKOWY.update();
                Input.resetInputs();

                // draw
                map.draw();
                KORKOWY.draw();
                HEALTHBAR.draw();
            }

            fontLoader.renderText("fps: " + avg, "msgothic.bmp",
                    0.65f, 0.9f, 0.05f, 0.08f,
                    0.0f, 1.0f, 0.0f, 1.0f);

            glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers
            current = glfwGetTime();
            avg = (int) Math.floor(1.0 / (current - previous));
            ++FRAMES;
        }
    }

    public static Collection<Entity> getPlatforms() {
        return map.getCurrentStage().platforms.values();
    }

    public static Collection<Entity> getMobs() {
        return map.getCurrentStage().mobs.values();
    }

    public static Collection<Entity> getObstacles() {
        return map.getCurrentStage().obstacles.values();
    }

    public static Collection<Entity> getDoors() {
        return map.getCurrentStage().doors.values();
    }

    public static float[] getStart() {
        return map.getCurrentStage().start;
    }
}
