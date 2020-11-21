package logic;

import graphics.Rectangle;

public class Obstacle extends Entity {

	public Obstacle(float posX, float posY, float width, float height, String textureName) {
		this.rectangle = new Rectangle(posX, posY, width, height);
		this.textureName = textureName;
		this.rectangle.initGL(this.textureName);
		this.groups |= GROUP_OBSTACLES;
	}

	@Override
	public void move() {
	}

	@Override
	public void draw() {
		rectangle.draw();
	}

	@Override
	public void update() {
	}

}
