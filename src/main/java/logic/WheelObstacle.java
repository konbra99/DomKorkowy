package logic;

import graphics.Rectangle;

public class WheelObstacle extends Obstacle {

	public WheelObstacle(float posX, float posY, float width, float height, String textureName) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.rectangle.ROTATEABLE = true;
		this.rectangle.initGL(this.textureName, "wheel.vert.glsl", "wheel.frag");
		this.groups |= GROUP_OBSTACLES;
		this.angle = 0.0f;
	}

	@Override
	public void update() {
//		System.out.println("update");
//		System.out.println(angle);
		angle += 1f;
		angle %= 360f;
		rectangle.rotate(angle);
	};
}
