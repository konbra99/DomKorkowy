package logic;

public class Obstacle extends Entity {

	public Obstacle() {
		super();
	}
	public Obstacle(float posX, float posY, float width, float height, String textureName) {
		super(posX, posY, width, height, textureName);
		init();
	}

	@Override
	public void init() {
		this.groups |= GROUP_OBSTACLES;
		this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
	}
}
