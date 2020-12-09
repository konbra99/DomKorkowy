package logic;

public class StaticObject extends Entity {

	public StaticObject() {
		super();
	}

	public StaticObject(float posX, float posY, float width, float height, String textureName) {
		super(posX, posY, width, height, textureName);
		init();
	}

	@Override
	public void init() {
		this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
	}
}
