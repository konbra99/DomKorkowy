package graphics.gui;

import graphics.Rectangle;
import logic.Entity;

public abstract class Context extends Entity {
    public Context() {}

    public Context(String background) {
        super(-1.0f, -1.0f, 2.0f, 2.0f, background);
    }

    @Override
    public void init() {
        this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void draw() {
        this.rectangle.draw();
    }

    public void refreshContext() {

    }
}
