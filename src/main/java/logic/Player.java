package logic;

import graphics.Engine;
import graphics.Input;

import static logic.CharacterState.*;

public class Player extends Character {
    private Hit hit;
    private int immune;

    public Player(float posX, float posY, float width, float height, String texture) {
        super(posX, posY, width, height, texture);
        this.hit = new Hit(posX + width / 2, posY + height / 2, 0.5f, 0.025f, "hit2.png");
        state = JUMPING;
        hp = 3;
        immune = 0;
        hit.setPlayer(this);
    }

    @Override
    public void move() {
        float speed = 1;

        if (Input.L_CTRL) {
            speed = 1.5f;
        }
        if (Input.RIGHT) {
            vel_x = 0.01f * speed;
            direction = RIGHT;
        } else if (Input.LEFT) {
            vel_x = -0.01f * speed;
            direction = LEFT;
        }

        if (Input.SPACE && state == STANDING) {
            vel_y = 0.04f;
            state = JUMPING;
        }

        this.gravity();

        if (state != STANDING && vel_y == 0) {
            state = STANDING;
        }
        if (immune < 1) {
            for (Entity mob : Engine.getMobs()) {
                if (this.rectangle.collidesWith(mob.rectangle)) {
                    System.out.println("Kolizja z mobem");
                    hp--;
                    immune = 30;
                    Engine.HEALTHBAR.initGL(hp + "hp.png", "rectangle.vert.glsl", "rectangle.frag");
                    if (hp == 0) {
                        hp = 3;
                    }
                }
            }

            for (Entity p: Engine.getObstacles()) {
                if (this.rectangle.collidesWith(p.getRectangle()))
                    if (p.isCollideable()) {
                        System.out.println("Kolizja z przeszkoda");
                        hp--;
                        immune = 30;
                        Engine.HEALTHBAR.initGL(hp + "hp.png", "rectangle.vert.glsl", "rectangle.frag");
                        if (hp == 0) {
                            hp = 3;
                        }
                    }
            }
        }


        this.rectangle.setOrientation(direction == RIGHT);
        this.rectangle.move(vel_x, vel_y);
        this.rectangle.draw();

        immune--;
        gravity_vel_dec();
        vel_x = 0.0f;
    }

    @Override
    public void update() {
        if (Input.HIT) {
            hit.start();
            Input.HIT = false;
        }

        hit.update();
    }
}