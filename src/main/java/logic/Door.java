package logic;

import graphics.Rectangle;

public class Door extends Entity {

	public Door() {
		super();
	}

	public Door(float posX, float posY, float width, float height, String textureName, boolean activeFlag) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.activeFlag = activeFlag;
		init();
	}

	@Override
	public void init() {
		super.init();
		this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
		this.groups |= GROUP_DOORS;
	}
}