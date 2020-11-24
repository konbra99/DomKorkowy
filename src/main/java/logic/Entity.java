package logic;
import com.google.gson.JsonObject;
import graphics.Engine;
import graphics.Rectangle;
import map.json.JsonSerializable;

public abstract class Entity implements JsonSerializable {

	// GROUPS
	public final static int GROUP_DEFAULT   = 0b00000001;
	public final static int GROUP_PLATFORMS = 0b00000010;
	public final static int GROUP_MOBS      = 0b00000100;
	public final static int GROUP_OBSTACLES = 0b00001000;

	// DIRECTIONS
	public final static int LEFT = -1;
	public final static int RIGHT = 1;

	protected Rectangle rectangle;      /* SERIALIZED */
	protected boolean gravityFlag;      /* NOT SERIALIZED */
	protected String textureName;       /* SERIALIZED */
	protected int groups;               /* NOT SERIALIZED */
	protected float angle;
	private int collision_counter = 0;  /* NOT SERIALIZED */
	protected float vel_y = 0.0f;
	protected float vel_x = 0.0f;

	public Entity() {
		rectangle = new Rectangle();
		groups |= GROUP_DEFAULT;
	}

	public void move() {
	};

	public void draw() {
		rectangle.draw();
	};

	public void update() {
		if (collision_counter > 0)
			collision_counter--;
	};

	public boolean isCollideable() {
		if (collision_counter > 0)
			return false;
		else {
			collision_counter = 20;
			return true;
		}
	}

	protected void gravity() {
		for (Entity p : Engine.map.getCurrentStage().platforms.values()) {
			if (this.rectangle.willCollide(p.getRectangle(), vel_x, vel_y) && vel_y < 0.0f) {
				// zignoruj jesli nie jest wyzej
				if (!(this.rectangle.posY + 0.01f > p.rectangle.posY + p.rectangle.height))
					continue;

				vel_y -= this.rectangle.posY + vel_y - (p.rectangle.posY + p.rectangle.height);
				vel_y = 0.0f;
				break;
			}
		}
	}

	protected void gravity_vel_dec() {
		vel_y -= 0.002f;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public JsonObject toJson() {
		JsonObject obj = rectangle.toJson();
		obj.addProperty("textureName", textureName);
		return obj;
	}

	public void fromJson(JsonObject obj) {
		textureName = obj.get("textureName").getAsString();
		rectangle.fromJson(obj);
	}

	/** Sprawdza, czy encja znajduje sie w podanej grupie. */
	public boolean isInGroup(int group) {
		return (groups & group) != 0;
	}
}
