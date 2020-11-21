package logic;

import graphics.Config;
import graphics.Engine;
import graphics.Rectangle;

public class Hit extends Entity {
    boolean isRight;
    String textureName;
    Player player;
    private float time, start, end;
    int sign;
    int angle_index;

    public Hit(float posX, float posY, float width, float height, String texture) {
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.rectangle.ROTATEABLE = true;
        this.rectangle.initGL(texture, "hit.vert.glsl", "hit.frag");
        this.textureName = texture;
        this.angle_index = 0;
        this.time = 3.0f;
    }

    // trzeba ustawic po stworzeniu
    public void setPlayer(Player player) {
        this.player = player;
    }

    // ustawia pozycje obok gracza
    @Override
    public void move() {
        float off_x = this.player.rectangle.posX + this.player.rectangle.width / 2
                - this.rectangle.posX - this.rectangle.width / 2;

        off_x += isRight ? 0.15f : -0.15f;

        float off_y = this.player.rectangle.posY + this.player.rectangle.height / 2
                - this.rectangle.posY - this.rectangle.height / 2;
        this.rectangle.move(off_x, off_y);
    }

    @Override
    public void draw() {
        this.rectangle.setOrientation(isRight);
        this.rectangle.draw();
    }

    public void start() {
        this.rectangle.rotate(Config.HIT_ANGLES[angle_index]);
        this.time = 0.0f;
        this.isRight = this.player.isRight;
        move();

        start = isRight ? this.rectangle.posX: this.rectangle.posX + this.rectangle.width;
        end = isRight ? this.rectangle.posX + this.rectangle.width : this.rectangle.posX;
        sign = isRight ? 1 : -1;

        angle_index++;
        angle_index %= Config.HIT_ANGLES.length;
    }

    @Override
    public void update() {
        float fade;
        if (time < 1.0f) {
            this.rectangle.setAlpha(1.0f);
            fade = (start + sign * time * this.rectangle.width + 1.0f) / 2;
            this.rectangle.setFade(fade);
            time += 0.05f;

            if (time >= 1.0f) {
                // sprawdzamy kolizje
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
