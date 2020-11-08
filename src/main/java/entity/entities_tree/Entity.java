package entity.entities_tree;
import static constants.ItemNames.ENTITY;

public abstract class Entity {

	protected float x, y;
	protected int id = -1;
	protected String type = ENTITY;

	abstract public void move(float x, float y);
	abstract public void draw();

	public String toString() {
		return String.format("[(%f,%f) %d %s\n", x, y, id, type);
	}
}
