package logic;

import com.google.gson.JsonObject;
import graphics.Rectangle;

public class Character extends Entity {
    public int hp;                      /* SERIALIZED */
    protected CharacterState state;
    protected boolean isRight = true;

    public Character() {
    }

    public Character(float posX, float posY, float width, float height, String textureName) {
        gravityFlag = true;
        this.rectangle = new Rectangle(posX, posY, width, height);
        this.textureName = textureName;
        this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void move() {}

    @Override
    public void draw() {
        rectangle.draw();
    }

    @Override
    public void update(){
        this.draw();
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty("hp", hp);
        return obj;
    };

    @Override
    public void fromJson(JsonObject obj) {
        hp = obj.get("hp").getAsInt();
        super.fromJson(obj);
    };
}
