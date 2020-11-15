package logic;

import graphics.Input;
import graphics.Rectangle;

public class Platform extends Entity {
    private String textureName;

    public Platform(float posX, float posY, float width, float height, String texture) {
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.rectangle.X_WRAP = true;
        this.rectangle.initGL(texture);
        this.textureName = texture;
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
}
