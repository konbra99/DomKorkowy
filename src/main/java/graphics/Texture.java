package graphics;

import graphics.utils.BufferUtils;
import graphics.utils.ImageUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private final int texture;
    private String textureName;

    public Texture(String textureName, float[] texCoords, boolean X_WRAP, boolean Y_WRAP, float width, float height) {
        this.textureName = textureName;
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        IntBuffer buffer = BufferUtils.getBuffer(textureName);
        int[] dims = ImageUtils.sizeMap.get(textureName);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, dims[0], dims[1], 0, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);

        // skaluje wierzcholki po x
        if (X_WRAP) {
            float scale_x = (Config.WIDTH * width / 2.0f) / dims[0];
            texCoords[4] = scale_x;
            texCoords[6] = scale_x;
        }

        // skaluje po y
        if (Y_WRAP) {
            float scale_y = (Config.HEIGHT * height * Config.RESOLUTION / 2.0f) / dims[1];
            texCoords[1] = scale_y;
            texCoords[7] = scale_y;
        }
    }

    public String getTextureName() {
        return textureName;
    }

    public void setImage(String imageName) {
        this.textureName = imageName;
        bind();
        IntBuffer buffer = BufferUtils.getBuffer(imageName);
        int[] dims = ImageUtils.sizeMap.get(imageName);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, dims[0], dims[1], 0, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);
        unbind();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
