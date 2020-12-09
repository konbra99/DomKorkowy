package logic;

public class WheelObstacle extends Obstacle {

	public WheelObstacle() {
		super();
	}

	public WheelObstacle(float posX, float posY, float width, float height, float vel_angle, String textureName) {
		super(posX, posY, width, height, textureName);
		init();
	}

	@Override
	public void update() {
		angle += vel_angle;
		angle %= 360f;
		rectangle.rotate(angle);
	};

	@Override
	public void init() {
		this.groups |= GROUP_OBSTACLES;
		this.vel_angle = vel_angle;
		this.rectangle.ROTATEABLE = true;
		this.rectangle.initGL(this.textureName, "wheel.vert.glsl", "wheel.frag");
	}
}
