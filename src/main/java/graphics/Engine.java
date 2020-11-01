package graphics;

import graphics.utils.BufferUtils;
import graphics.utils.ImageUtils;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Engine implements Runnable {
    private Window window;
    private VertexArrayObject VAO;
    private Program program;
    private int location;
    float offset = 0.0f;

    private float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private int[] indices = {
            0, 1, 2,
            0, 3, 2
    };

    private float[] texCoords = {
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
    };

    @Override
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window.getWindowHandle());
        glfwDestroyWindow(window.getWindowHandle());

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        window = new Window();
        GL.createCapabilities();
    }

    private void action() {
        glUseProgram(program.programID);
        glBindVertexArray(VAO.VAO);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glUniform1f(location, offset);
        if (Window.RIGHT) {
            offset += 0.01f;
        } else if (Window.LEFT) {
            offset -= 0.01f;
        }
    }

    private void loop() {
        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        program = new Program("triangle.vert.glsl", "triangle.frag");
        VAO = new VertexArrayObject(vertices, indices, texCoords);

        location = glGetUniformLocation(program.programID, "offset");

        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        int[] pixels = ImageUtils.load("korkowa_postac.png");
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ImageUtils.width, ImageUtils.height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(pixels));

        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window.getWindowHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            action();

            glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}
