package logic.collectibles;

import logic.Entity;

public class Collectible extends Entity {
    public Collectible(float posX, float posY, float width, float height, String textureName) {
        super(posX, posY, width, height, textureName);
    }

    public void usage() {
        //do something
        //delete item
    }
}
