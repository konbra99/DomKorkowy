package graphics;

import graphics.utils.BufferUtils;
import graphics.utils.ImageUtils;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private final int texture;

    public Texture(String textureName) {
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        int[] pixels = ImageUtils.load(textureName);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ImageUtils.width, ImageUtils.height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(pixels));
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
