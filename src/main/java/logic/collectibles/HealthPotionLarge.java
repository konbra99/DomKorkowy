package logic.collectibles;

import graphics.Engine;

public class HealthPotionLarge extends HealthPotion{

    public HealthPotionLarge() {
        super();
    }

    public HealthPotionLarge(float posX, float posY, float width, float height) {
        super(posX, posY, width, height, "HealthPotionLarge.png");
    }

    @Override
    public void usage() {
        Engine.gameplay.KORKOWY.heal(3);
        Engine.gameplay.map.getCurrentStage().removeEntity(this.id);
    }
}
