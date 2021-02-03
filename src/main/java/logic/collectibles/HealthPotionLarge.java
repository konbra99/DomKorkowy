package logic.collectibles;

import graphics.gui.GameplayContext;

public class HealthPotionLarge extends HealthPotion {

    public HealthPotionLarge() {
        super();
    }

    public HealthPotionLarge(float posX, float posY, float width, float height) {
        super(posX, posY, width, height, "HealthPotionLarge.png");
    }

    @Override
    public void usage() {
        GameplayContext.KORKOWY.heal(3);
        GameplayContext.map.getCurrentStage().removeEntity(this.id);
    }
}
