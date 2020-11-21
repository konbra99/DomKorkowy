package logic;

import com.google.gson.JsonObject;

public class TestRect extends Entity {

	public boolean boolTest;
	public int intTest;
	public float floatTest;
	public String stringTest;

	public TestRect() {
	}

	public TestRect(boolean boolTest, int intTest, float floatTest, String stringTest) {
		this.boolTest = boolTest;
		this.intTest = intTest;
		this.floatTest = floatTest;
		this.stringTest = stringTest;
	}

	@Override
	public void move() {}

	@Override
	public void draw() {}

	@Override
	public void update() {}

	@Override
	public JsonObject toJson() {
		JsonObject obj = super.toJson();
		obj.addProperty("boolTest", boolTest);
		obj.addProperty("intTest", intTest);
		obj.addProperty("floatTest", floatTest);
		obj.addProperty("stringTest", stringTest);
		return obj;
	};

	@Override
	public void fromJson(JsonObject obj) {
		this.boolTest = obj.get("boolTest").getAsBoolean();
		this.intTest = obj.get("intTest").getAsInt();
		this.floatTest = obj.get("floatTest").getAsFloat();
		this.stringTest = obj.get("stringTest").getAsString();
		super.fromJson(obj);
	};
}
