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

//        for (Platform p : Engine.PLATFORMS) {
        for (Entity p : Engine.map.getCurrentStage().platforms.values()) {
            if (this.rectangle.collidesWith(p.getRectangle()) && offsetY < 0.0f) {
                offsetY = 0.0f;
            }
        }

        if (this.rectangle.collidesWith(Engine.KONIEC)) {
            System.out.println("koniec gry");
        }

        this.rectangle.move(offsetX, offsetY);
        this.rectangle.draw();
    }
}
