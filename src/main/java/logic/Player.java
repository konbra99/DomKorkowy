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
//        state = JUMPING;
//        hp = 3;
//        immune = 0;
        hit.setPlayer(this);
        reset();
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
                    getDamage();
                }
            }
            for (Entity mob : Engine.getObstacles()) {
                if (this.rectangle.collidesWith(mob.rectangle)) {
                    getDamage();
                }
            }
        }

        for(Entity door: Engine.getDoors())
            if(door.isActive() && this.rectangle.collidesWith(door.rectangle)) {
                Engine.map.nextStage();
                reset();
            }



        this.rectangle.setOrientation(direction == RIGHT);
        this.rectangle.move(vel_x, vel_y);
        this.rectangle.draw();

        immune--;
        gravity_vel_dec();
        vel_x = 0.0f;

        if (rectangle.posX <= -1f -rectangle.width)
            reset();
        if (rectangle.posX >= 1f)
            reset();
    }

    @Override
    public void update() {
        if (Input.HIT) {
            hit.start();
            Input.HIT = false;
        }

        hit.update();
    }

    private void getDamage() {
        hp--;
        immune = 30;
        if (hp == 0) {
            reset();
            return;
        }
        Engine.HEALTHBAR.initGL(hp + "hp.png", "rectangle.vert.glsl", "rectangle.frag");
    }

    private void reset() {
        hp = 3;
        float start[] = Engine.getStart();
        rectangle.move(start[0] - rectangle.posX, start[1] - rectangle.posY);
        Engine.HEALTHBAR.initGL(hp + "hp.png", "rectangle.vert.glsl", "rectangle.frag");
        state = JUMPING;
        immune = 0;
        vel_x = 0.0f;
        vel_y = 0.0f;
    }
}