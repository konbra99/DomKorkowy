package entity.entities_tree;
import graphics.Rectangle;

import static constants.ItemNames.ENTITY;

public abstract class Entity {

	protected int id = -1;
	protected String type = ENTITY;
	protected Rectangle rectangle;

	abstract public void move(float x, float y);
	abstract public void draw();
	abstract public void update();

	public String toString() {
		return String.format("Entity(%d %s)", id, type);
	}
}