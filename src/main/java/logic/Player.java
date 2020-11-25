package logic;

import graphics.Engine;
import graphics.Input;

import static logic.CharacterState.*;

public class Player extends Character {
    private Weapon weapons[];
    private int activeWeapon;
    private int immune;

    public Player(float posX, float posY, float width, float height, String texture) {
        super(posX, posY, width, height, texture);
        // Trzy sloty na bronie, dwie przykładowe dodane od początku
        this.weapons = new MeleeWeapon[3];
        this.weapons[0] = new MeleeWeapon(this, 0, posX - width, posY - height / 2, 0.12f,
                0.12f, 0.065f, "sword1.png", 5, 4);
        this.weapons[1] = new MeleeWeapon(this, 1, posX - width, posY - height / 2, 0.075f,
                0.15f, 0.07f, "mace.png", 1, 2);
        this.activeWeapon = 0;
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
            vel_y = 0.03f;
            if (Input.L_SHIFT) {
                vel_y *= 1.33f;
            }
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
        weapons[activeWeapon].update();
        if (Input.ATTACK) {
            weapons[activeWeapon].start();
            Input.ATTACK = false;
        }
        if(Input.ONE_KEY) {
            this.setActiveWeapon(0);
            Input.ONE_KEY = false;
        } else if(Input.TWO_KEY) {
            this.setActiveWeapon(1);
            Input.TWO_KEY = false;
        }

        weapons[activeWeapon].update();
    }
    public void setActiveWeapon(int index) {
        this.activeWeapon = index;
    }


    private void getDamage() {
        hp--;
        immune = 30;
        if (hp == 0) {
            reset();
            return;
        }
        Engine.HEALTHBAR.initGL(hp + "hp.png", "rectangle.vert.glsl", "rectangle.frag");
        vel_y *= 0.9f;
        vel_x *= 0.9f;
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