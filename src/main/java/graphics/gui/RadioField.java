package graphics.gui;

import graphics.Config;
import graphics.Engine;
import graphics.Input;
import graphics.Rectangle;
import map.MapManager;


public class RadioField extends DataField {

    // zmienne
    private Rectangle on_off;
    int current_state;
    Action action_off;
    Action action_on;

    // stale
    public final static int ON = 0;
    public final static int OFF = 1;
    public final static String [] ON_OFF_TEXTURES = {"gui/on.png", "gui/off.png"};

    public RadioField(String [] textures, int init_state, Action on, Action off) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
        current_state = init_state;
        action_on = on;
        action_off = off;
        on_off = new Rectangle(0.7f, +0.06f, 0.25f, 0.17f);
        on_off.initGL(ON_OFF_TEXTURES[current_state], "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void update() {
        this.text_x = this.rectangle.posX + 0.1f;
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;
        if (is_active && this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
            if (current_state == ON) {
                // zmiana na OFF
                current_state = OFF;
                on_off.setTexture(ON_OFF_TEXTURES[OFF]);
                action_off.action();
            }
            else {
                // zmiana na ON
                current_state = ON;
                on_off.setTexture(ON_OFF_TEXTURES[ON]);
                action_on.action();
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
        on_off.draw();
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);
        this.on_off.move(dx, dy);
        this.text_x = this.rectangle.posX + 0.1f;
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;
    }

    public void setActionOn(Action action) {
        this.action_on = action;
    }

    public void setActionOff(Action action) {
        this.action_off = action;
    }
}
