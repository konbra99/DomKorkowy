package logic;

import com.google.gson.JsonObject;
import graphics.Rectangle;
import graphics.gui.GameplayContext;
import map.Stage;

public class Checkpoint extends Entity {
    public int stageX, stageY;

    public Checkpoint() {
        super();
        this.groups |= GROUP_CHECKPOINTS;
    }

    public Checkpoint(float x, float y, float width, float height, Stage stage,
                      boolean activeFlag) {
        this.rectangle = new Rectangle(x, y, width, height);
        this.activeFlag = activeFlag;
        this.stageX = stage.getxIndex();
        this.stageY = stage.getyIndex();
        this.textureName = "checkpoints/checkpoint_off.png";
        this.groups |= GROUP_CHECKPOINTS;
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty("stageX", stageX);
        obj.addProperty("stageY", stageY);
        obj.addProperty("activeFlag", activeFlag);
        return obj;
    }

    @Override
    public void fromJson(JsonObject obj) {
        this.stageX = obj.get("stageX").getAsInt();
        this.stageY = obj.get("stageY").getAsInt();
        this.activeFlag = obj.get("activeFlag").getAsBoolean();
        super.fromJson(obj);
    }

    @Override
    public void update() {
        super.update();
        GameplayContext.KORKOWY.collideCheckpoint(this);
    }

    public void check() {
        this.rectangle.setTexture("checkpoints/checkpoint_on.png");
    }

    public void uncheck() {
        this.rectangle.setTexture("checkpoints/checkpoint_off.png");
    }
}
