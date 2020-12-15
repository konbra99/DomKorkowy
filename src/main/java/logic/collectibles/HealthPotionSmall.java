package logic.collectibles;

import graphics.Engine;
import graphics.gui.GameplayContext;

public class HealthPotionSmall extends HealthPotion{
    public HealthPotionSmall(float posX, float posY, float width, float height) {
        super(posX, posY, width, height, "HealthPotionSmall.png");
        rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
    }

    public void update() {
        if (Engine.gameplay.KORKOWY.getRectangle().collidesWith(this.getRectangle())) {
            Engine.gameplay.KORKOWY.heal(1);
        }
    }
}
