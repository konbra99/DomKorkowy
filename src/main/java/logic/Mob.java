package logic;

import com.google.gson.JsonObject;

public class Mob extends Character {

    protected float beginX;
    protected float endX;

    public Mob() {
        super();
    }

    public Mob (float posX, float posY, float width, float height, String texture,
                float beginX, float endX, float velocityX, int direction){
        super(posX, posY, width, height, texture);
        this.vel_x = velocityX;
        this.beginX = beginX;
        this.endX = endX;
        this.direction = direction;
        init();
    }

    @Override
    public void init() {
        groups |= GROUP_MOBS;
        this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
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
        rectangle.move(direction*vel_x, 0f);
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty("beginX", beginX);
        obj.addProperty("endX", endX);
        return obj;
    };

    @Override
    public void fromJson(JsonObject obj) {
        this.beginX = obj.get("beginX").getAsFloat();
        this.endX = obj.get("endX").getAsFloat();
        super.fromJson(obj);
    };
}
