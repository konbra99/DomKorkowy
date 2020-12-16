package graphics.gui;

import graphics.Config;
import graphics.Engine;
import graphics.Input;
import map.MapManager;


public class OptionField extends DataField {
    public MapManager map;
    MapBrowser browser;
    Button left;
    Button right;
    String [] option_strings;
    Object [] option_values;
    int option_length;
    int option_current;

    public OptionField(MapBrowser browser, String [] textures, String[] option_strings, Object[] option_values) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
        this.browser = browser;
        this.action = () -> {
            this.is_invalid = false;
            if (this.browser != null)
                this.browser.deselectAll();
        };
        this.option_strings = option_strings;
        this.option_values = option_values;
        this.option_length = option_strings.length;
        this.option_current = 0;

        left = new Button(0.35f, +0.05f, 0.09f, 0.18f, this::prevValue, Button.LEFT_ARROW);
        right = new Button(0.86f, +0.05f, 0.09f, 0.18f, this::nextValue, Button.RIGHT_ARROW);
    }

    @Override
    public void update() {
        this.text_x = this.rectangle.posX + 0.1f;
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;
        left.update();
        right.update();
        if (is_active && this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
            this.action.action();
        }
    }

    @Override
    public void draw() {
        super.draw();
        left.draw();
        right.draw();
        Engine.fontLoader.renderText(option_strings[option_current], font, text_x+0.4f, text_y, charwidth, charheight,
                0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);
        this.left.move(dx, dy);
        this.right.move(dx, dy);
        this.text_x = this.rectangle.posX + 0.1f;
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;
    }

    public boolean getValue(MapManager map) {
        return false;
    }

    public Object getValue() {
        return option_values[option_current];
    }

    public void nextValue() {
        option_current = (option_current+1) % option_length;
        System.out.println("current_option: " + option_current);
    }

    public void prevValue() {
        option_current = (option_length + option_current-1) % option_length;
        System.out.println("current_option: " + option_current);
    }
}
