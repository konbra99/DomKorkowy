package logic;

import com.google.gson.JsonObject;
import graphics.Input;
import graphics.Rectangle;

public class Platform extends Entity{

    public Platform(float posX, float posY, float width, float height, String texture) {
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.rectangle.X_WRAP = true;
        this.rectangle.initGL(texture);
        this.textureName = texture;
        this.groups |= GROUP_PLATFORMS;
    }

    @Override
    public void move() {}

    @Override
    public void draw() {
        this.rectangle.draw();
    }

    @Override
    public void update() {
        if (this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
            if (this.textureName.equals("platforma.png")) {
                this.rectangle.setTexture("platforma2.jpg");
                this.textureName = "platforma2.jpg";
            } else {
                this.rectangle.setTexture("platforma.png");
                this.textureName = "platforma.png";
            }
        }
    }

    @Override
    public JsonObject toJson() {
        return super.toJson();
    }

    @Override
    public void fromJson(JsonObject obj) {
        super.fromJson(obj);
    }
}
