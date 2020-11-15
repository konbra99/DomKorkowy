package graphics;

import logic.Platform;
import logic.Player;
import map.MapManager;
import map.Stage;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import java.util.LinkedList;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine implements Runnable {
    private Window window;

    public static MapManager map;
    public static Player KORKOWY;
    public static Rectangle BACKGROUND, KONIEC;
    public static LinkedList<Platform> PLATFORMS;

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
        initTempMap();
    }

    private void initTempMap() {
        Stage stage = new Stage();
        stage.addMapEntity(new Platform(-0.2f, -0.2f, 1.0f, 0.1f, "platforma.png"));
        stage.addMapEntity(new Platform(-1.0f, -1.0f / Main.RESOLUTION, 2.0f, 0.1f, "platforma.png"));
        stage.buildHashMap();
        stage.buildStage();
        map.addStage(stage);
    }

    private void action() {
        BACKGROUND.draw();

//        for (Platform p : PLATFORMS) {
//            p.update();
//            p.draw();
//        }

        map.update();
        map.draw();

        KONIEC.draw();

        KORKOWY.move();

        Input.resetMouse();
    }

    private void loop() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        KORKOWY = new Player(-0.7f, -0.4f, 0.13f, 0.21f, "korkowa_postac.png");

        KONIEC = new Rectangle(0.5f, -0.1f, 0.13f, 0.21f);

        PLATFORMS = new LinkedList<>();
        //PLATFORMS.add(new Platform(-0.2f, -0.2f, 1.0f, 0.1f, "platforma.png"));
        PLATFORMS.add(new Platform(-1.0f, -1.0f / Main.RESOLUTION, 2.0f, 0.1f, "platforma.png"));

        BACKGROUND = new Rectangle(-1.0f, -1.0f / Main.RESOLUTION, 2.0f, 2.0f / Main.RESOLUTION);
        BACKGROUND.X_WRAP = true;

        KONIEC.initGL("koniec.png");
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
