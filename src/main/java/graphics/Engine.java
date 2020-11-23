package graphics;

import logic.*;
import map.MapManager;
import map.Stage;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine implements Runnable {
    private Window window;

    public static MapManager map;
    public static Player KORKOWY;
    public static Rectangle BACKGROUND;
    public static LinkedList<Platform> PLATFORMS;
    public static Rectangle HEALTHBAR;

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
        initTempMap();
    }

    private void initTempMap() {
        Stage stage = new Stage();
        stage.addMapEntity(new Platform(-0.2f, -0.2f, 1.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-1.0f, -1.0f / Config.RESOLUTION, 2.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Mob(0.5f, -0.1f, 0.13f, 0.21f, "koniec.png"));
        stage.addMapEntity(new Obstacle(0.1f, -0.1f, 0.13f, 0.21f, "kaktus.png"));
        stage.buildHashMap();
        stage.buildStage();
        map.addStage(stage);
    }

    private void action() {
        map.move();
        map.update();
        map.draw();

        KORKOWY.move();
        KORKOWY.update();

        HEALTHBAR.draw();

        Input.resetMouse();
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        KORKOWY = new Player(-0.7f, -0.4f, 0.13f, 0.21f, "korkowa_postac.png");

        PLATFORMS = new LinkedList<>();
        PLATFORMS.add(new Platform(-1.0f, -1.0f / Config.RESOLUTION, 2.0f, 0.1f, "platforma.png"));

        HEALTHBAR = new Rectangle(-1.0f, 0.48f, 0.24f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
        // glowna petla programu
        while (!glfwWindowShouldClose(window.getWindowHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            action();


            glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers

            // sprawdza czy zaszly jakies eventy
            glfwPollEvents();
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
}
