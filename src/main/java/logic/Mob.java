package logic;

import com.google.gson.JsonObject;

public class Mob extends Character {

    public Mob() {
        groups |= GROUP_MOBS;
    }
    public Mob (float posX, float posY, float width, float height, String texture){
        super(posX, posY, width, height, texture);
        groups |= GROUP_MOBS;
    }

    @Override
    public void move() {
        //rectangle.move();
    }

    @Override
    public JsonObject toJson() {
        return super.toJson();
    };

    @Override
    public void fromJson(JsonObject obj) {
        super.fromJson(obj);
    };
}
