package items.items_tree;

public class TestRect extends Item {

	protected float z;

	public TestRect() {
		this.x = 0f;
		this.y = 0f;
		this.type = "TestRect";
	}

	public TestRect(int id, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = -10;
		this.type = "TestRect";
	}

	@Override
	public void move(float x, float y) {}

	@Override
	public void draw() {}
}
