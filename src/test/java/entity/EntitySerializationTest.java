package entity;

import com.google.gson.JsonObject;
import entity.entities_tree.TestRect;

import static constants.JsonSerializationStatus.NOTEXISTING_PROPERTY;
import static org.junit.Assert.assertEquals;

public class EntitySerializationTest {

	//@Test
	public void EntityToJsonTest() {
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		String s = "abc";
		TestRect rect = new TestRect(b, i, f, s);
		JsonObject obj = rect.toJson();

		assertEquals(obj.get("type").getAsString(), "TestRect");
		assertEquals(obj.get("boolTest").getAsBoolean(), b);
		assertEquals(obj.get("intTest").getAsInt(), i);
		assertEquals(obj.get("floatTest").getAsFloat(), f, Float.MIN_VALUE);
		assertEquals(obj.get("stringTest").getAsString(), s);
	}

	//@Test
	public void EntityFromJsonTest() {
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		String s = "abc";

		JsonObject obj = new JsonObject();
		obj.addProperty("boolTest", b);
		obj.addProperty("intTest", i);
		obj.addProperty("floatTest", f);
		obj.addProperty("stringTest", s);
		TestRect rect = new TestRect();
		rect.fromJson(obj);

		assertEquals(rect.boolTest, b);
		assertEquals(rect.intTest, i);
		assertEquals(rect.floatTest, f, Float.MIN_VALUE);
		assertEquals(rect.stringTest, s);
	}

	//@Test
	public void EntityFromJsonNotExistingTest() {
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		int status;

		JsonObject obj = new JsonObject();
		obj.addProperty("boolTest", b);
		obj.addProperty("intTest", i);
		obj.addProperty("floatTest", f);
		TestRect rect = new TestRect();
		status = rect.fromJson(obj);

		assertEquals(status, NOTEXISTING_PROPERTY);
	}
}
