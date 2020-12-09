package logic;

import com.google.gson.JsonObject;
import graphics.Rectangle;

public class Door extends Entity {

	public Door() {
		super();
		this.groups |= GROUP_DOORS;
	}

	public Door(float posX, float posY, float width, float height, String textureName, boolean activeFlag) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.activeFlag = activeFlag;
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
		obj.addProperty("activeFlag", activeFlag);
		return obj;
	};

	@Override
	public void fromJson(JsonObject obj) {
		this.activeFlag = obj.get("activeFlag").getAsBoolean();
		super.fromJson(obj);
	};
}