package logic.collectibles;

import graphics.Engine;
import graphics.gui.GameplayContext;

public class HealthPotionSmall extends HealthPotion {

    public HealthPotionSmall() {
        super();
    }

    public HealthPotionSmall(float posX, float posY, float width, float height) {
        super(posX, posY, width, height, "HealthPotionSmall.png");
    }

    @Override
    public void usage() {
        GameplayContext.KORKOWY.heal(1);
        GameplayContext.map.getCurrentStage().removeEntity(this.id);
    }
}
