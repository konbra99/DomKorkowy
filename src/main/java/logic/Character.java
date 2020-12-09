package logic;

import com.google.gson.JsonObject;
import graphics.Rectangle;

public class Character extends Entity {
    public int hp;                      /* SERIALIZED */
    protected CharacterState state;
    protected int direction = RIGHT;

    public Character() {
        super();
    }

    @Override
    public void init() {
        gravityFlag = true;
        this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
    }

    public Character(float posX, float posY, float width, float height, String textureName) {
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.textureName = textureName;
        init();
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty("hp", hp);
        return obj;
    }

    @Override
    public void fromJson(JsonObject obj) {
        hp = obj.get("hp").getAsInt();
        super.fromJson(obj);
    }
}
