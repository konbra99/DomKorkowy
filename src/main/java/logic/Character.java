package logic;

import com.google.gson.JsonObject;

public class Character extends Entity {
    protected CharacterState state;     /* NOT SERIALIZED */

    public Character() {
        super();
        gravityFlag = true;
    }

    @Override
    public void init() {
        super.init();
        gravityFlag = true;
        this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
    }

    public Character(float posX, float posY, float width, float height, String textureName, int direction) {
        super(posX, posY, width, height, textureName);
        this.direction = direction;
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty("hp", hp);
        obj.addProperty("direction", direction);
        return obj;
    }

    @Override
    public void fromJson(JsonObject obj) {
        hp = obj.get("hp").getAsInt();
        direction = obj.get("direction").getAsInt();
        super.fromJson(obj);
    }
}
