package graphics.gui;

import graphics.Config;
import graphics.Engine;
import graphics.Input;
import logic.Entity;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class Button extends Entity {
    String text, font;
    Action action;
    float text_x, text_y, charwidth, charheight;
    protected boolean is_active;
    protected boolean is_selected;
    protected String texture_enabled = "gui/button_long.png";
    protected String texture_disabled = "gui/button_long.png";
    protected String texture_highlighted = "gui/button_long.png";
    protected String texture_selected = "gui/button_long.png";


    public final static String [] LONG_BUTTON = {
            "gui/button_long.png",
            "gui/button_long_disabled.png",
            "gui/button_long_highlighted.png",
            "gui/button_long_selected.png" };

    public final static String [] MEDIUM_BUTTON = {
            "gui/button_medium.png",
            "gui/button_medium_disabled.png",
            "gui/button_medium_highlighted.png",
            "gui/button_medium_selected.png" };

    public final static String [] RIGHT_ARROW = {
            "gui/rarrow.png",
            "gui/rarrow_disabled.png",
            "gui/rarrow_highlighted.png",
            "gui/rarrow_selected.png" };

    public final static String [] LEFT_ARROW = {
            "gui/larrow.png",
            "gui/larrow_disabled.png",
            "gui/larrow_highlighted.png",
            "gui/larrow_selected.png" };

    public Button(float x, float y, float width, float height, Action action) {
        super(x, y, width, height, "gui/button.png");
        this.text = "";
        this.font = "";
        this.action = action;
        this.is_active = true;
        this.is_selected = false;
        this.rectangle.initGL("gui/textarea_active.png", "rectangle.vert.glsl", "rectangle.frag");
    }

    public Button(float x, float y, float width, float height, Action action, String [] textures) {
        super(x, y, width, height, "gui/button.png");
        this.text = "";
        this.font = "";
        this.action = action;
        this.is_active = true;
        this.is_selected = false;
        this.rectangle.initGL("gui/textarea_active.png", "rectangle.vert.glsl", "rectangle.frag");

        this.texture_enabled = textures[0];
        this.texture_disabled = textures[1];
        this.texture_highlighted = textures[2];
        this.texture_selected = textures[3];
    }

    @Override
    public void draw() {
        if (is_active) {
            if (is_selected)
                this.rectangle.setTexture(texture_selected);
            else if (this.rectangle.hasPoint(Input.MOUSE_POS_X, Input.MOUSE_POS_Y))
                this.rectangle.setTexture(texture_highlighted);
            else
                this.rectangle.setTexture(texture_enabled);
        } else {
            this.rectangle.setTexture(texture_disabled);
        }

        super.draw();
        if (this.text.length() > 0 && this.font.length() > 0) {
            Engine.fontLoader.renderText(text, font, text_x, text_y, charwidth, charheight,
                    0.0f, 0.0f, 0.0f, 1.0f);
        }
    }

    @Override
    public void update() {
        this.text_x = this.rectangle.posX + (this.rectangle.width / 2) - (charwidth * text.length() / 2);
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;
        if (is_active && this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
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

    public void setActive(boolean is_active) {
        if (this.is_active != is_active)
            if (is_active) {
                rectangle.setTexture("gui/textarea_active.png");
                this.is_active = is_active;
            } else {
                rectangle.setTexture("gui/textarea_inactive.png");
                this.is_active = is_active;
            }
    }
}
