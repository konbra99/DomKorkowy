package logic;
import com.google.gson.JsonObject;
import graphics.Config;
import graphics.Engine;
import graphics.Rectangle;
import map.json.JsonSerializable;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity implements JsonSerializable {

	// grupy
	public final static int GROUP_DEFAULT   = 0b00000001;
	public final static int GROUP_PLATFORMS = 0b00000010;
	public final static int GROUP_MOBS      = 0b00000100;
	public final static int GROUP_OBSTACLES = 0b00001000;
	public final static int GROUP_DOORS     = 0b00010000;

	// kierunki
	public final static int LEFT = -1;
	public final static int RIGHT = 1;

	// flagi
	protected boolean gravityFlag = true;   /* NOT SERIALIZED */
	protected boolean activeFlag = false;   /* SERIALIZED BY OTHERS */

	// polozenie / predkosc
	protected int direction = RIGHT;        /* SERIALIZED BY OTHERS */
	protected float angle = 0.0f;           /* SERIALIZED BY OTHERS */
	protected float vel_angle = 1.0f;       /* SERIALIZED BY OTHERS */
	protected float vel_y = 0.0f;           /* SERIALIZED BY OTHERS */
	protected float vel_x = 0.0f;           /* SERIALIZED BY OTHERS */

	protected int id;
	protected int hp;

	protected Rectangle rectangle;          /* SERIALIZED */
	protected String textureName;           /* SERIALIZED */
	protected int groups;                   /* NOT SERIALIZED */

	public Entity() {
		rectangle = new Rectangle();
		groups |= GROUP_DEFAULT;
	}

	public Entity(float posX, float posY, float width, float height, String textureName) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		groups |= GROUP_DEFAULT;
	}

	public void move() {}

	public void moveTo(float x, float y) {
		System.out.println("move " + x + " " + y);
		this.rectangle.move(x - this.rectangle.posX, y - this.rectangle.posY);
	}

	public void draw() { rectangle.draw(); }

	public void update() {}

	public void init() {
		rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
	}

	protected void gravity() {
		for (Entity p : Engine.gameplay.getPlatforms()) {
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

	public void getDamage() {
		this.hp--;
		if (this.hp < 1) {
			Engine.gameplay.map.getCurrentStage().removeEntity(this.id);
		}
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public Map<String, Float> getAttributes() {
		Map<String, Float> attr = new HashMap<>();
		attr.put("posX", rectangle.posX);
		attr.put("posY", rectangle.posY);
		attr.put("width", rectangle.width);
		attr.put("height", rectangle.height);
		return attr;
	}

	public void setAttributes(Map<String, Float> map) {
		if (rectangle.width != map.get("width") || rectangle.height != map.get("height")) {
			rectangle = new Rectangle(map.get("posX"), map.get("posY"), map.get("width"), map.get("height") * Config.RESOLUTION);
			init();
		} else {
			moveTo(map.get("posX"), map.get("posY"));
		}
	}

	public JsonObject toJson() {
		JsonObject obj = rectangle.toJson();
		obj.addProperty("textureName", textureName);
		obj.addProperty("hp", hp);
		obj.addProperty("direction", direction);
		return obj;
	}

	public void fromJson(JsonObject obj) {
		textureName = obj.get("textureName").getAsString();
		rectangle.fromJson(obj);
		this.id = obj.get("id").getAsInt();
	}

	/** Sprawdza, czy encja znajduje sie w podanej grupie. */
	public boolean isInGroup(int group) {
		return (groups & group) != 0;
	}

	/** Sprawdza, czy encja jest aktywna. */
	public boolean isActive() {
		return this.activeFlag;
	}

	public int getDirection() {
		return this.direction;
	}
}
