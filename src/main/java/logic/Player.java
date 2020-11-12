package logic;

import graphics.Engine;
import graphics.Input;

public class Player extends Character {

    public Player(float posX, float posY, float width, float height, String texture) {
        super(posX, posY, width, height, texture);
    }

    @Override
    public void move() {
        float offsetX = 0.0f, offsetY = 0.0f;

        if (Input.RIGHT) {
            offsetX = 0.01f;
        } else if (Input.LEFT) {
            offsetX = -0.01f;
        }

        if (Input.UP) {
            offsetY = 0.01f;
        } else if (Input.DOWN) {
            offsetY = -0.01f;
        }

        if (this.shape.collidesWith(Engine.PLATFORM) && offsetY < 0.0f) {
            offsetY = 0.0f;
        }

        this.shape.move(offsetX, offsetY);
        this.shape.draw();
    }
}
