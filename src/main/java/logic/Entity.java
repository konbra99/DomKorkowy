package logic;
import com.google.gson.JsonObject;
import constants.EntityConstants;
import graphics.Rectangle;
import map.json.JsonSerializable;

import static constants.JsonSerializationStatus.ENTITY_OK;

public abstract class Entity implements JsonSerializable {

	protected Rectangle rectangle;      /* SERIALIZED */
	protected boolean gravityFlag;      /* NOT SERIALIZED */
	protected String textureName;       /* SERIALIZED */
	protected int groups;               /* NOT SERIALIZED */

	public Entity() {
		rectangle = new Rectangle();
		groups |= EntityConstants.GROUP_DEFAULT;
	}

	abstract public void move();
	abstract public void draw();
	abstract public void update();

	public Rectangle getRectangle() {
		return rectangle;
	}

	public JsonObject toJson() {
		JsonObject obj = rectangle.toJson();
		obj.addProperty("textureName", textureName);
		return obj;
	};

	public void fromJson(JsonObject obj) {
		textureName = obj.get("textureName").getAsString();
		rectangle.fromJson(obj);
	};

	/** Sprawdza, czy encja znajduje sie w podanej grupie. */
	public boolean isInGroup(int group) {
		return (groups & group) != 0;
	}
}
