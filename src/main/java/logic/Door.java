package logic;

import com.google.gson.JsonObject;
import graphics.Rectangle;
import map.Stage;

import java.util.Map;

public class Door extends Entity {
	public int stageX, stageY;
	public int doorNumber;
	public int linkedDoor;

	public Door() {
		super();
		this.groups |= GROUP_DOORS;
	}

	public Door(float posX, float posY, float width, float height, String textureName,
				Stage stage, int linkedDoor, boolean activeFlag) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.activeFlag = activeFlag;
		this.stageX = stage.getxIndex();
		this.stageY = stage.getyIndex();
		this.linkedDoor = linkedDoor;
		this.groups |= GROUP_DOORS;
	}

	@Override
	public JsonObject toJson() {
		JsonObject obj = super.toJson();
		obj.addProperty("stageX", stageX);
		obj.addProperty("stageY", stageY);
		obj.addProperty("number", doorNumber);
		obj.addProperty("linkedDoor", linkedDoor);
		obj.addProperty("activeFlag", activeFlag);
		return obj;
	}

	@Override
	public void fromJson(JsonObject obj) {
		this.stageX = obj.get("stageX").getAsInt();
		this.stageY = obj.get("stageY").getAsInt();
		this.doorNumber = obj.get("number").getAsInt();
		this.linkedDoor = obj.get("linkedDoor").getAsInt();
		this.activeFlag = obj.get("activeFlag").getAsBoolean();
		super.fromJson(obj);
	}

	@Override
	public Map<String, Float> getAttributes() {
		Map<String, Float> attr = super.getAttributes();
		attr.put("link", (float) linkedDoor);
		return attr;
	}

	@Override
	public void setAttributes(Map<String, Float> map) {
		super.setAttributes(map);
		linkedDoor = (int) Math.floor(map.get("link"));
	}

	public Door getLinkedDoor(Map<Integer, Entity> doorList) {
		System.out.println("zwracam " + linkedDoor);
		return ((Door) doorList.get(linkedDoor));
	}
}
