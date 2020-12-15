package logic.collectibles;

import graphics.Engine;

public class HealthPotionSmall extends HealthPotion {

    public HealthPotionSmall() {
        super();
    }

    public HealthPotionSmall(float posX, float posY, float width, float height) {
        super(posX, posY, width, height, "HealthPotionSmall.png");
    }

    @Override
    public void init() {
        super.init();
        rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void update() {
        if (Engine.gameplay.KORKOWY.getRectangle().collidesWith(this.getRectangle())) {
            usage();
        }
    }

    @Override
    public void usage() {
        Engine.gameplay.KORKOWY.heal(1);
        Engine.gameplay.map.getCurrentStage().removeEntity(this.id);
    }
}
