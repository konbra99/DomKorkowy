package logic.collectibles;

import graphics.gui.GameplayContext;
import logic.Entity;

public class Collectible extends Entity {

    public Collectible() {
        super();
    }

    public Collectible(float posX, float posY, float width, float height, String textureName) {
        super(posX, posY, width, height, textureName);
    }

    @Override
    public void init() {
        super.init();
        rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void update() {
        if (GameplayContext.KORKOWY.getRectangle().collidesWith(this.getRectangle())) {
            usage();
        }
    }

    public void usage() {
    }
}
