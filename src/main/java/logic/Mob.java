package logic;

import com.google.gson.JsonObject;

public class Mob extends Character {

    // VARIABLES
    protected float velocity;
    protected float beginX;
    protected float endX;

    public Mob() {
        groups |= GROUP_MOBS;
    }
    public Mob (float posX, float posY, float width, float height, String texture){
        super(posX, posY, width, height, texture);
        groups |= GROUP_MOBS;
        velocity = 0.01f;
        beginX = -0.2f;
        endX = 0.7f;
        direction = RIGHT;
    }

    @Override
    public void move() {
        if (rectangle.posX > endX) {
            direction = LEFT;
        }
        else if (rectangle.posX < beginX) {
            direction = RIGHT;
        }

        rectangle.setOrientation(direction != RIGHT);
        rectangle.move(direction*velocity, 0f);
    }

    public JsonObject toJson() {
        return super.toJson();
    };

    @Override
    public void fromJson(JsonObject obj) {
        super.fromJson(obj);
    };
}
