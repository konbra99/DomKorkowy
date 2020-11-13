package entity.entities_tree;
import entity.EntityProperties;
import graphics.Rectangle;

import static constants.ItemNames.ENTITY;

public abstract class Entity {
	protected int id = -1;
	protected String type = ENTITY;
	protected Rectangle rectangle;
	protected boolean gravityFlag;

	public Entity() {

	}

	public Entity(EntityProperties properties) {
		id = properties.id;

		// rectangle = new Rectangle(properties.width, properties.height) {}
	}

	abstract public void move();

	abstract public void draw();

	abstract public void update();

	public Rectangle getRectangle() {
		return rectangle;
	}

	public String toString() {
		return String.format("Entity(%d %s)", id, type);
	}
}
