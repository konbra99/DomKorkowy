package graphics;

import com.google.gson.JsonObject;
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
    private Window window;

    public static MapManager map;
    public static Player KORKOWY;
    public static Rectangle HEALTHBAR;
    public static int FRAMES;

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

        // init temp map
        try {
            JsonObject obj = JsonUtils.fromFile("test_file.json");
            map.fromJson(obj);
            map.nextStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        FRAMES = 0;

        HEALTHBAR = new Rectangle(-1.0f, 0.9f, 0.18f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
        KORKOWY = new Player(-0.7f, -0.8f, 0.08f, 0.18f, "korkowa_postac.png");
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFont("msgothic.bmp");

        double current = glfwGetTime();
        double previous;
        int avg = 0;

        // glowna petla programu
        while (!glfwWindowShouldClose(window.getWindowHandle())) {
            previous = current;
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // input
            glfwPollEvents();

            // update
            map.move();
            map.update();
            KORKOWY.move();
            KORKOWY.update();

            // draw
            map.draw();
            KORKOWY.draw();
            HEALTHBAR.draw();
            fontLoader.renderText("fps: " + avg, "msgothic.bmp",
                    0.65f, 0.9f, 0.05f, 0.08f,
                    0.0f, 1.0f, 0.0f, 1.0f);

            Input.resetMouse();

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
