package graphics.gui;

import graphics.Config;
import graphics.Engine;
import graphics.Input;
import graphics.Rectangle;
import logic.Entity;

public class TextArea extends Entity {
    String text;
    private float charwidth, charheight;
    private int capacity;
    private float r, g, b, a;
    private String font;

    public TextArea(float x, float y, float width, float height, float charwidth, float charheight, String font,
                    float r, float g, float b, float a) {
        this.rectangle = new Rectangle(x, y, width, height);
        this.charwidth = charwidth;
        this.charheight = charheight;
        this.capacity = (int) Math.floor(width * 0.95f / charwidth);
        this.font = font;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.rectangle.initGL("gui/cyan.png", "rectangle.vert.glsl", "rectangle.frag");
        this.text = "";
    }

    @Override
    public void update() {
        if (this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
            Engine.active = this;
        } else if (Engine.active == this && Input.MOUSE_X != -2.0f) {
            Engine.active = null;
            return;
        }

        if (Engine.active == this) {
            if (Input.singlePressed.size() > 0) {
                int len = text.length() - 1;
                if (Input.singlePressed.get(0) == '|') {
                    if (len >= 0) {
                        text = text.substring(0, len);
                    }
                    return;
                }

                text += Input.singlePressed.get(0);
            }
        }
    }

    @Override
    public void draw() {
        this.rectangle.draw();
        String tempText = this.text;
        int len = this.text.length();
        if (len >= this.capacity) {
            tempText = this.text.substring(len - this.capacity);
        }
        Engine.fontLoader.renderText(tempText, this.font, this.rectangle.posX + this.rectangle.width / 20.0f,
                (this.rectangle.posY - this.rectangle.height / 20.0f) * Config.RESOLUTION,
                this.charwidth, this.charheight, this.r, this.g, this.b, 1.0f);
    }

    // czysci i zwraca tekst
    public String Clear() {
        String temp = this.text;
        this.text = "";
        return temp;
    }

    public void setCharwidth(float charwidth) {
        this.charwidth = charwidth;
        this.capacity = (int) Math.floor(this.rectangle.width * 0.95f / charwidth);
    }

    public void setCharheight(float charheight) {
        this.charheight = charheight;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setA(float a) {
        this.a = a;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getText() {
        return this.text;
    }
}
