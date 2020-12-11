package graphics.gui;

import graphics.Config;
import graphics.Engine;
import graphics.Input;
import logic.Entity;

public class Button extends Entity {
    String text, font;
    Action action;
    float text_x, text_y, charwidth, charheight;

    public Button(float x, float y, float width, float height, Action action) {
        super(x, y, width, height, "gui/button.png");
        this.text = "";
        this.font = "";
        this.action = action;
        this.rectangle.initGL("gui/textarea.png", "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void draw() {
        super.draw();
        if (this.text.length() > 0 && this.font.length() > 0) {
            Engine.fontLoader.renderText(text, font, text_x, text_y, charwidth, charheight,
                    0.0f, 0.0f, 0.0f, 1.0f);
        }
    }

    @Override
    public void update() {
        if (this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
            this.action.action();
        }
    }

    public void setText(String text, String font, float width, float height) {
        this.text = text;
        this.font = font;
        this.charwidth = width;
        this.charheight = height;
        this.text_x = this.rectangle.posX + (this.rectangle.width / 2) - (width * text.length() / 2);
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (height / 2))* Config.RESOLUTION;
    }
}
