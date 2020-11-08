package entity.entities_tree;

import static constants.ItemNames.TEST_RECT;

public class TestRect extends Entity {

	public TestRect() {
		this.type = TEST_RECT;
	}

	public TestRect(int id) {
		this.id = id;
		this.type = TEST_RECT;
	}

	@Override
	public void move(float x, float y) {}

	@Override
	public void draw() {}

	@Override
	public void update() {};
}
