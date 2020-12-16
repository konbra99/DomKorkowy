package graphics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import map.json.JsonSerializable;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Rectangle implements JsonSerializable {
    private VertexArrayObject VAO;
    private Texture texture;
    private Program program;
    public float org_posX, org_posY, posX, posY, width, height;
    public float centreX, centreY;
    public float angle;

    // konfiguracje
    // musza byc ustawione przed initGL
    public boolean X_WRAP, Y_WRAP;
    public boolean ROTATEABLE;

    public Rectangle() {
    }

    public Rectangle(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY / Config.RESOLUTION;
        this.org_posX = posX;
        this.org_posY = this.posY;
        this.centreX = posX + width / 2;
        this.centreY = (posY + height / 2) / Config.RESOLUTION;
        this.width = width;
        this.height = height / Config.RESOLUTION;
        this.angle = 0.0f;
    }

    public void initGL(String textureName, String vertShader, String fragShader) {
        float[] vertices = new float[]{
                posX, posY, 0.0f,
                posX, posY + height, 0.0f,
                posX + width, posY + height, 0.0f,
                posX + width, posY, 0.0f
        };

        int[] indices = new int[]{
                0, 1, 2,
                0, 3, 2
        };

        float[] texCoords = new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };

        texture = new Texture(textureName, texCoords, X_WRAP, Y_WRAP, width, height);
        VAO = new VertexArrayObject(vertices, indices, texCoords);
        program = new Program(vertShader, fragShader);
    }

    public void initGL(String vertShader, String fragShader) {
        float[] vertices = new float[]{
                posX, posY, 0.0f,
                posX, posY + height, 0.0f,
                posX + width, posY + height, 0.0f,
                posX + width, posY, 0.0f
        };

        int[] indices = new int[]{
                0, 1, 2,
                0, 3, 2
        };

        VAO = new VertexArrayObject(vertices, indices);
        program = new Program(vertShader, fragShader);
    }

    public void draw() {
        glUseProgram(program.programID);
        if (ROTATEABLE) {
            program.setFloat("angle", this.angle);
            program.setFloat3("centre", this.centreX, this.centreY, 0.0f);
        }
        program.setFloat("resolution", Config.RESOLUTION);
        texture.bind();
        glBindVertexArray(VAO.VAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
        texture.unbind();
        glUseProgram(0);
    }

    public void move(float x, float y) {
        posX += x;
        posY += y;

        glUseProgram(program.programID);
        program.setFloat2("offset", posX - org_posX, posY - org_posY);
        glUseProgram(0);
    }

    public void rotate(float angle) {
        this.angle = angle;

        glUseProgram(program.programID);
        program.setFloat("angle", this.angle);
        glUseProgram(0);
    }

    public void setOrientation(boolean isRight) {
        glUseProgram(program.programID);
        program.setBool("isRight", isRight);
        glUseProgram(0);
    }

    public void setAlpha(float alpha) {
        glUseProgram(program.programID);
        program.setFloat("myAlpha", alpha);
        glUseProgram(0);
    }

    public void setFade(float fade) {
        glUseProgram(program.programID);
        program.setFloat("xFade", fade);
        glUseProgram(0);
    }

    // sprawdza czy (x, y) jest wewnatrz prostokata
    public boolean hasPoint(float x, float y) {
        if (x >= posX && x <= posX + width) {
            return y >= posY && y <= posY + height;
        }

        return false;
    }

    // sprawdza czy prostokaty zachodza na siebie
    public boolean collidesWith(Rectangle other) {
        if (posX <= other.posX + other.width && posX + width >= other.posX) {
            return posY <= other.posY + other.height && posY + height >= other.posY;
        }

        return false;
    }

    // sprawdza czy po move(offX, offY) zajdzie kolizja
    public boolean willCollide(Rectangle other, float offX, float offY) {
        if (posX + offX <= other.posX + other.width && posX + offX + width >= other.posX) {
            return posY + offY <= other.posY + other.height && posY + offY + height >= other.posY;
        }

        return false;
    }

    public void setTexture(String imageName) {
        this.texture.setImage(imageName);
    }

    /**
     * Poczatek procesu serializacji.
     */
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("posX", posX);
        obj.addProperty("posY", posY);
        obj.addProperty("width", width);
        obj.addProperty("height", height);
        return obj;
    }

    /**
     * Koniec procesu deserializacji.
     */
    public void fromJson(JsonObject obj) {
        posX = obj.get("posX").getAsFloat();
        posY = obj.get("posY").getAsFloat();
        width = obj.get("width").getAsFloat();
        height = obj.get("height").getAsFloat();
        org_posX = posX;
        org_posY = posY;
        centreX = posX + width / 2;
        centreY = posY + height / 2;
    }
}
