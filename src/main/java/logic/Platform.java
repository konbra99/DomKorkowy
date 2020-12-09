package logic;

import graphics.Input;

public class Platform extends Entity{

    public Platform() {
        super();
        groups |= GROUP_PLATFORMS;
    }

    public Platform(float posX, float posY, float width, float height, String textureName) {
        super(posX, posY, width, height, textureName);
        groups |= GROUP_PLATFORMS;
    }

    @Override
    public void init() {
        this.rectangle.X_WRAP = true;
        this.rectangle.initGL(textureName, "rectangle.vert.glsl", "rectangle.frag");
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
