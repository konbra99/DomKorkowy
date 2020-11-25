package logic;

import graphics.Config;

public class MeleeWeapon extends Weapon {
    private Hit hit;
    private int duration;
    private int attackFrame;

    public MeleeWeapon(Player player, int id, float posX, float posY, float width, float height, float shift_x,
                       String textureName, int damage, int attackSpeed) {
        super(player, id, posX, posY, width, height, shift_x, textureName, damage, attackSpeed);
        hit = new Hit(this, posX + width / 2, posY + height / 2, 0.3f, 0.025f, "hit2.png");
        this.rectangle.ROTATEABLE = true;
        this.rectangle.initGL(this.textureName, "meleeweapon.vert.glsl", "meleeweapon.frag");
        this.rectangle.centreX = rectangle.org_posX;
        this.rectangle.centreY = rectangle.org_posY;
    }


    // ustawia pozycję obok gracza
    @Override
    public void move() {
        float off_x = this.player.rectangle.posX + this.player.rectangle.width / 2
                - this.rectangle.posX - this.rectangle.width / 2;
        off_x += direction * this.shift_x;

        float off_y = this.player.rectangle.posY + this.player.rectangle.height / 2
                - this.rectangle.posY - this.rectangle.height / 2;
        off_y += 0.05f;
        this.rectangle.move(off_x, off_y);
    }

    @Override
    public void start() {
        this.duration = Config.ATTACK_SPEED_FACTOR / attackSpeed;
        this.attackFrame = 0;
        hit.start();
    }

    @Override
    public void update() {
        if(this.player.direction == RIGHT) {
            this.direction = RIGHT;
        } else {
            this.direction = LEFT;
        }
        this.rectangle.setOrientation(direction == RIGHT);

        if(this.attackFrame <= this.duration) {
            this.rectangle.rotate(WeaponUtils.angle((this.id % 3), -this.direction, Config.ATTACK_MAX_ANGLES[id],
                    this.duration, this.attackFrame));
            this.attackFrame++;
        } else {
            this.rectangle.rotate(0);
        }

        hit.update();
        move();
        draw();

    }
}

