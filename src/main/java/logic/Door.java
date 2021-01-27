package logic;

import com.google.gson.JsonObject;
import graphics.Rectangle;

import java.util.Map;

public class Door extends Entity {
	public int linkedStage;

	public Door() {
		super();
		this.groups |= GROUP_DOORS;
	}

	public Door(float posX, float posY, float width, float height, String textureName, int linkedStage, boolean activeFlag) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.activeFlag = activeFlag;
		this.linkedStage = linkedStage;
		this.groups |= GROUP_DOORS;
	}

	@Override
	public void init() {
		super.init();
		this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
	}

	@Override
	public JsonObject toJson() {
		JsonObject obj = super.toJson();
		obj.addProperty("linkedStage", linkedStage);
		obj.addProperty("activeFlag", activeFlag);
		return obj;
	}

	@Override
	public void fromJson(JsonObject obj) {
		this.linkedStage = obj.get("linkedStage").getAsInt();
		this.activeFlag = obj.get("activeFlag").getAsBoolean();
		super.fromJson(obj);
	}

	@Override
	public Map<String, Float> getAttributes() {
		Map<String, Float> attr = super.getAttributes();
		attr.put("link", (float) this.linkedStage);
		return attr;
	}

	@Override
	public void setAttributes(Map<String, Float> map) {
		super.setAttributes(map);
		linkedStage = (int) Math.floor(map.get("link"));
	}
}