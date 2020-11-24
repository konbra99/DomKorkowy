package logic;

import graphics.Rectangle;

public class StaticObject extends Entity {

	public StaticObject(float posX, float posY, float width, float height, String textureName) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.rectangle.initGL(this.textureName, "rectangle.vert.glsl", "rectangle.frag");
	}
}
