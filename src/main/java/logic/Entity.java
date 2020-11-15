package logic;
import com.google.gson.JsonObject;
import constants.EntityConstants;
import graphics.Rectangle;
import map.json.JsonSerializable;

import static constants.JsonSerializationStatus.ENTITY_OK;

public abstract class Entity implements JsonSerializable {

	protected Rectangle rectangle;
	protected boolean gravityFlag;
	public static int groups;

	public Entity() {
		groups |= EntityConstants.GROUP_DEFAULT;
	}

	abstract public void move();
	abstract public void draw();
	abstract public void update();

	/** Poczatek procesu serializacji. */
	public JsonObject toJson() {
		return new JsonObject();
	};

	/** Koniec procesu deserializacji. */
	public int fromJson(JsonObject obj) {
		return ENTITY_OK;
	};

	/** Sprawdza, czy encja znajduje sie w podanej grupie. */
	public boolean isInGroup(int group) {
		return (groups & group) != 0;
	}
}
