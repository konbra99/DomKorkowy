package entity.entities_tree;
import com.google.gson.JsonObject;
import constants.EntityConstants;
import entity.EntityProperties;
import graphics.Rectangle;
import map.json.JsonSerializable;
import constants.EntityConstants.*;

import static constants.JsonSerializationStatus.ENTITY_OK;

public abstract class Entity implements JsonSerializable {

	protected Rectangle rectangle;
	protected boolean gravityFlag;
	public static int groups;

	public Entity() {
		groups |= EntityConstants.DEFAULT;
	}

//	public Entity(EntityProperties properties) {
//		setProperties(properties);
//	}

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

//	public void setProperties(EntityProperties properties) {
//	}

}
