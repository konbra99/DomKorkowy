package logic;

import entity.entities_tree.Entity;
import graphics.Rectangle;

public class Character extends Entity {
    public int hp;
    private String texture;
    public Rectangle shape;

    public Character(float posX, float posY, float width, float height, String texture) {
        this.shape = new Rectangle(posX, posY, width, height);
        this.texture = texture;
        shape.initGL(this.texture);
    }

    @Override
    public void move(float x, float y){
        rectangle.move(x, y);
    };

    @Override
    public void update(){
        rectangle.draw();
    };
}
