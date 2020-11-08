package entity.entities_tree;

import static constants.ItemNames.TEST_RECT;

public class TestRect extends Entity {

	protected float z;

	public TestRect() {
		this.x = 0f;
		this.y = 0f;
		this.type = TEST_RECT;
	}

	public TestRect(int id, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.type = TEST_RECT;
	}

	@Override
	public void move(float x, float y) {}

	@Override
	public void draw() {}
}
