package logic;

import graphics.Config;
import graphics.Engine;
import graphics.Rectangle;

public class Hit extends Entity {
    int direction;
    String textureName;
    MeleeWeapon weapon;
    private float time, start, end;
    int angle_index;

    public Hit(MeleeWeapon weapon, float posX, float posY, float width, float height, String texture) {
        this.weapon = weapon;
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.textureName = texture;
        this.rectangle.ROTATEABLE = true;
        this.rectangle.initGL(texture, "hit.vert.glsl", "hit.frag");
        this.angle_index = 0;
        this.time = 3.0f;
    }

    // ustawia pozycje obok broni
    @Override
    public void move() {
        float off_x = this.weapon.rectangle.posX + this.weapon.rectangle.width / 2
                - this.rectangle.posX - this.rectangle.width / 2;

        off_x += direction * 0.1f;

        float off_y = this.weapon.rectangle.posY + this.weapon.rectangle.height / 2
                - this.rectangle.posY - this.rectangle.height / 2;
        this.rectangle.move(off_x, off_y);
    }

    @Override
    public void draw() {
        this.rectangle.setOrientation(direction == RIGHT);
        this.rectangle.draw();
    }

    public void start() {
        this.rectangle.rotate(Config.HIT_ANGLES[angle_index]);
        this.time = 0.0f;
        this.direction = this.weapon.direction;
        move();

        start = direction == RIGHT ? this.rectangle.posX: this.rectangle.posX + this.rectangle.width;
        end = direction == RIGHT ? this.rectangle.posX + this.rectangle.width : this.rectangle.posX;

        angle_index++;
        angle_index %= Config.HIT_ANGLES.length;
    }

    @Override
    public void update() {
        float fade;
        if (time < 1.0f) {
            this.rectangle.setAlpha(1.0f);
            fade = (start + direction * time * this.rectangle.width + 1.0f) / 2;
            this.rectangle.setFade(fade);
            time += 0.05f;

            if (time >= 1.0f) {
                // sprawdzamy kolizj
                for (Entity mob : Engine.map.getCurrentStage().mobs.values()) {
                    if (this.rectangle.collidesWith(mob.rectangle)) {
                        System.out.println("trafiony");
                    }
                }
            }
        } else if (time <= 2.0f) {
            this.rectangle.setAlpha(2.0f - time);
            this.rectangle.setFade((end + 1.0f) / 2);

            time += 0.04f;
            if (time > 2.0f) {
                this.rectangle.rotate(0);
            }
        } else {
            return;
        }

        draw();
    }
}