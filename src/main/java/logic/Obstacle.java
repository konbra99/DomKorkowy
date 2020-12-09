package logic;

public class Obstacle extends Entity {

	public Obstacle() {
		super();
		this.groups |= GROUP_OBSTACLES;
	}
	public Obstacle(float posX, float posY, float width, float height, String textureName) {
		super(posX, posY, width, height, textureName);
		this.groups |= GROUP_OBSTACLES;
	}

	@Override
	public void init() {
		this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
	}
}
