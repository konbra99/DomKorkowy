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
    public void usage() {
        Engine.gameplay.KORKOWY.heal(1);
        Engine.gameplay.map.getCurrentStage().removeEntity(this.id);
    }
}
