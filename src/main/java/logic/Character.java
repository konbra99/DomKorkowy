package logic;

import graphics.Rectangle;

public class Character {
    public int hp;
    private String texture;
    public Rectangle shape;

    public Character(float posX, float posY, float width, float height, String texture) {
        this.shape = new Rectangle(posX, posY, width, height);
        this.texture = texture;
        shape.initGL(this.texture);
    }
}
