package graphics;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

// textura czcionki musi byc 256x256 z 64 znakami
public class FontLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    VertexArrayObject[] VAOs;
    Program program;

    public FontLoader() {
        float tex_x, tex_y;
        VAOs = new VertexArrayObject[64];
        float[] vertices = new float[]{
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, 0.0f, 0.0f
        };

        int[] indices = new int[]{
                0, 1, 2,
                0, 3, 2
        };

        for (int i = 0; i < 64; ++i) {
            tex_x = (i % 8) / 8.0f;
            tex_y = (i / 8) / 8.0f;
            float[] tex_coords = new float[]{
                    tex_x, tex_y + 0.125f,
                    tex_x, tex_y,
                    tex_x + 0.06f, tex_y,
                    tex_x + 0.06f, tex_y + 0.125f
            };
            VAOs[i] = new VertexArrayObject(vertices, indices, tex_coords);
        }

        program = new Program("font.vert.glsl", "font.frag");
    }

    // musi byc wywolane raz
    public void loadFont(String fontName) {
        Texture texture = new Texture("Fonts/" + fontName, null, false, false, 0, 0);
        textures.put(fontName, texture);
    }

    // width i height to wymiary znaku nie tekstu
    public void renderText(String text, String font, float x, float y, float width, float height,
                           float r, float g, float b, float a) {
        float offset = 0;
        text = text.toUpperCase();
        glUseProgram(program.programID);
        program.setFloat2("size", width, height);
        program.setFloat("offsetY", y);
        program.setFloat4("color", r, g, b, a);
        textures.get(font).bind();
        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            c -= 32;
            program.setFloat("offsetX", x + offset);
            glBindVertexArray(VAOs[c].VAO);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            glBindVertexArray(0);
            offset += width;
        }
        glUseProgram(0);
    }
}
