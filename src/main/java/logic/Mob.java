package logic;

import com.google.gson.JsonObject;

import java.util.Map;

public class Mob extends Character {

    protected float beginX;
    protected float endX;

    public Mob() {
        super();
        groups |= GROUP_MOBS;
    }

    public Mob (float posX, float posY, float width, float height, String texture,
                float beginX, float endX, float velocityX, int direction){
        super(posX, posY, width, height, texture, direction);
        this.vel_x = velocityX;
        this.beginX = beginX;
        this.endX = endX;
        groups |= GROUP_MOBS;
        this.hp = 3;
    }

    @Override
    public void init() {
        super.init();
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
    public Map<String, Float> getAttributes() {
        Map<String, Float> attr = super.getAttributes();
        attr.put("beginX", beginX);
        attr.put("endX", endX);
        attr.put("vel_x", vel_x);
        return attr;
    }

    @Override
    public void setAttributes(Map<String, Float> map) {
        super.setAttributes(map);
        beginX = map.get("beginX");
        endX = map.get("endX");
        vel_x = map.get("vel_x");
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty("beginX", beginX);
        obj.addProperty("endX", endX);
        obj.addProperty("vel_x", vel_x);
        return obj;
    }

    @Override
    public void fromJson(JsonObject obj) {
        this.beginX = obj.get("beginX").getAsFloat();
        this.endX = obj.get("endX").getAsFloat();
        this.vel_x = obj.get("vel_x").getAsFloat();
        super.fromJson(obj);
    }
}
