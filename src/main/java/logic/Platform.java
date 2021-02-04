package logic;

public class Platform extends Entity {

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
}
