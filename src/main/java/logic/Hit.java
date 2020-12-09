package logic;

import graphics.Config;
import graphics.Engine;
import graphics.Main;
import graphics.Rectangle;

public class Hit extends Entity {
    private int shift_time = 30;
    private int fade_time = 30;
    private int shift_end = 0, fade_end = 0;

    int direction;
    String textureName;
    MeleeWeapon weapon;
    private float start, end;
    int angle_index;

    public Hit(MeleeWeapon weapon, float posX, float posY, float width, float height, String texture) {
        this.weapon = weapon;
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.textureName = texture;
        this.rectangle.ROTATEABLE = true;
        this.rectangle.initGL(texture, "hit.vert.glsl", "hit.frag");
        this.angle_index = 0;
    }

    // ustawia pozycje obok broni
    @Override
    public void move() {
        float off_x = this.weapon.rectangle.posX + this.weapon.rectangle.width / 2
                - this.rectangle.posX - this.rectangle.width / 2;

        off_x += direction * 0.075f;

        float off_y = this.weapon.rectangle.posY + this.weapon.rectangle.height / 2
                - this.rectangle.posY - this.rectangle.height / 2;
        this.rectangle.move(off_x, off_y);
    }

    @Override
    public void draw() {
        if (Engine.FRAMES > fade_end)
            return;

        this.rectangle.setOrientation(direction == RIGHT);
        this.rectangle.draw();
    }

    public void start() {
        this.rectangle.rotate(Config.HIT_ANGLES[angle_index]);
        this.shift_end = Engine.FRAMES + shift_time;
        this.fade_end = shift_end + shift_time;
        this.direction = this.weapon.direction;
        move();

        start = direction == RIGHT ? this.rectangle.posX : this.rectangle.posX + this.rectangle.width;
        end = direction == RIGHT ? this.rectangle.posX + this.rectangle.width : this.rectangle.posX;

        angle_index++;
        angle_index %= Config.HIT_ANGLES.length;
    }

    @Override
    public void update() {
        float fade;
        if (Engine.FRAMES <= shift_end) {
            this.rectangle.setAlpha(1.0f);
            fade = (start
                    + direction * ((shift_time - shift_end + Engine.FRAMES) / (float) shift_time)
                    * this.rectangle.width + 1.0f) / 2;
            this.rectangle.setFade(fade);

            if (Engine.FRAMES == shift_end) {
                // sprawdzamy kolizje
                for (Entity mob : Engine.map.getCurrentStage().mobs.values()) {
                    if (this.rectangle.collidesWith(mob.rectangle)) {
                        System.out.println("trafiony");
                    }
                }
            }
        } else if (Engine.FRAMES <= fade_end) {
            this.rectangle.setAlpha((fade_end - Engine.FRAMES) / (float) fade_time);
            this.rectangle.setFade((end + 1.0f) / 2);

            if (Engine.FRAMES == fade_end) {
                this.rectangle.rotate(0);
            }
        }
    }
}