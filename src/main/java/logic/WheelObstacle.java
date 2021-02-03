package logic;

import com.google.gson.JsonObject;

public class WheelObstacle extends Obstacle {

	public WheelObstacle() {
		super();
	}

	public WheelObstacle(float posX, float posY, float width, float height, String textureName, float vel_angle) {
		super(posX, posY, width, height, textureName);
		this.vel_angle = vel_angle;
	}

	@Override
	public void update() {
		angle += vel_angle;
		angle %= 360f;
		rectangle.rotate(angle);
	}

	@Override
	public void init() {
		this.rectangle.ROTATEABLE = true;
		this.rectangle.initGL(this.textureName, "wheel.vert.glsl", "wheel.frag");
	}

	@Override
	public JsonObject toJson() {
		JsonObject obj = super.toJson();
		obj.addProperty("vel_angle", vel_angle);
		return obj;
	}

	@Override
	public void fromJson(JsonObject obj) {
		this.vel_angle = obj.get("vel_angle").getAsFloat();
		super.fromJson(obj);
	}
}
