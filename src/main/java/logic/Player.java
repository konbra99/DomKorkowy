package logic;

import graphics.Engine;
import graphics.Input;

import static logic.CharacterState.*;

public class Player extends Character {
    final static int initial_gravity_acc = 40;
    private int gravity_acc_up = 40;
    private int gravity_acc_down = 0;
    private boolean collision = false;

    public Player(float posX, float posY, float width, float height, String texture) {
        super(posX, posY, width, height, texture);
        state = FALLING;
    }

    @Override
    public void move() {
        float offsetX = 0.0f, offsetY = 0.0f;

        if (Input.RIGHT) {
            offsetX = 0.01f;
        } else if (Input.LEFT) {
            offsetX = -0.01f;
        }
        /*if (Input.UP) {
            offsetY = 0.01f;
        } else if (Input.DOWN) {
            offsetY = -0.01f;
        }*/

        if (state == STANDING) {
            gravity_acc_down = 0;
            if (Input.SPACE) {
                state = JUMPING;
                gravity_acc_up = 40;
            }

        }
        if (state == FALLING) {
            gravity_acc_down++;
            offsetY = -0.01f * 0.1f * gravity_acc_down;
        }

        if (state == JUMPING) {
            gravity_acc_up--;
            offsetY = 0.01f * 0.05f * gravity_acc_up;
            if (gravity_acc_up < 1) {
                gravity_acc_up = 0;
                state = FALLING;
            }
        }
        //        for (Platform p : Engine.PLATFORMS) {
        if (state != JUMPING) {
            collision = false;
            for (Entity p : Engine.map.getCurrentStage().platforms.values()) {
                if (this.rectangle.collidesWith(p.getRectangle()) && offsetY < 0.0f) {
                    collision = true;
                    state = STANDING;
                    offsetY = 0.0f;
                }
            }
            if (!collision) { state = FALLING;}
        }

        if (this.rectangle.collidesWith(Engine.KONIEC)) {
            System.out.println("koniec gry");
        }
        this.rectangle.move(offsetX, offsetY);
        this.rectangle.draw();
    }
}
