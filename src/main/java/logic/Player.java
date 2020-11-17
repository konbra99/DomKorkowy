package logic;

import graphics.Engine;
import graphics.Input;

import static logic.CharacterState.*;

public class Player extends Character {
    private float vel_y = 0.0f;

    public Player(float posX, float posY, float width, float height, String texture) {
        super(posX, posY, width, height, texture);
        state = JUMPING;
    }

    @Override
    public void move() {
        float offsetX = 0.0f, offsetY = 0.0f;

        if (Input.RIGHT) {
            offsetX = 0.01f;
        } else if (Input.LEFT) {
            offsetX = -0.01f;
        }

        if (Input.SPACE && state == STANDING) {
            vel_y = 0.04f;
            state = JUMPING;
        }

        offsetY = vel_y;

        for (Entity p : Engine.map.getCurrentStage().platforms.values()) {
            if (this.rectangle.willCollide(p.getRectangle(), offsetX, offsetY) && offsetY < 0.0f) {
                // zignoruj jesli nie jest wyzej
                if (!(this.rectangle.posY + 0.01f > p.rectangle.posY + p.rectangle.height))
                    continue;

                offsetY -= this.rectangle.posY + offsetY - (p.rectangle.posY + p.rectangle.height);
                vel_y = 0.0f;
                state = STANDING;
                break;
            }
        }

        if (this.rectangle.collidesWith(Engine.KONIEC)) {
            System.out.println("koniec gry");
        }

        this.rectangle.move(offsetX, offsetY);
        this.rectangle.draw();

        vel_y -= 0.002f;
    }
}
