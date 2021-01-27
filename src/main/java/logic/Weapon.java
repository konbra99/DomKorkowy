package logic;

import graphics.Rectangle;

public abstract class Weapon extends Entity {
    protected int id;
    protected int damage;
    protected int attackSpeed;
    protected int direction;
    protected float shift_x;
    protected Player player;

    public Weapon(Player player, int id, float posX, float posY, float width, float height, float shift_x,
                  String textureName, int damage, int attackSpeed) {
        this.player = player;
        this.id = id;
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.textureName = textureName;
        this.shift_x = shift_x;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public void start() {
    }

    public void hitUpdate() {}
}
