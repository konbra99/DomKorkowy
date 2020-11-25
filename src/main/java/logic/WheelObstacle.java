package logic;

import graphics.Rectangle;

public class WheelObstacle extends Obstacle {

	public WheelObstacle(float posX, float posY, float width, float height, float vel_angle, String textureName) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.rectangle.ROTATEABLE = true;
		this.rectangle.initGL(this.textureName, "wheel.vert.glsl", "wheel.frag");
		this.groups |= GROUP_OBSTACLES;
		this.angle = 0.0f;
		this.vel_angle = vel_angle;
	}

	@Override
	public void update() {
		angle += vel_angle;
		angle %= 360f;
		rectangle.rotate(angle);
	};
}
