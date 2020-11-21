package logic;
import com.google.gson.JsonObject;
import graphics.Rectangle;
import map.json.JsonSerializable;

public abstract class Entity implements JsonSerializable {

	// GROUPS
	public final static int GROUP_DEFAULT  = 0b00000001;
	public final static int GROUP_PLATFORMS = 0b00000010;
	public final static int GROUP_MOBS      = 0b00000100;

	// DIRECTIONS
	public final static int LEFT = -1;
	public final static int RIGHT = 1;

	protected Rectangle rectangle;      /* SERIALIZED */
	protected boolean gravityFlag;      /* NOT SERIALIZED */
	protected String textureName;       /* SERIALIZED */
	protected int groups;               /* NOT SERIALIZED */

	public Entity() {
		rectangle = new Rectangle();
		groups |= GROUP_DEFAULT;
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
