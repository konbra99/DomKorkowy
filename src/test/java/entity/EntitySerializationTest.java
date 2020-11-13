package entity;

import com.google.gson.JsonObject;
import entity.entities_tree.TestRect;
import map.json.JsonUtils;
import org.junit.Test;

import static constants.JsonSerializationStatus.*;
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
		String str = """
                {"type":"TestRect","intTest":10,"floatTest":99.99,"boolTest":false,"stringTest":"abc","id":20}""";
		JsonObject obj = JsonUtils.fromString(str);
		TestRect rect = new TestRect();
		int status = rect.fromJson(obj);

		assertEquals(status, ENTITY_OK);
		assertEquals(rect.boolTest, b);
		assertEquals(rect.intTest, i);
		assertEquals(rect.floatTest, f, Float.MIN_VALUE);
		assertEquals(rect.stringTest, s);
	}

	//@Test
	public void EntityFromJsonNonexistentPropertyTest() {
		String str = """
                {"type":"TestRect","intTest":10,"floatTest":99.99, "stringTest":"abc","id":20}""";
		JsonObject obj = JsonUtils.fromString(str);
		TestRect rect = new TestRect();
		int status = rect.fromJson(obj);

		assertEquals(status, NONEXISTENT_PROPERTY);
	}

	//@Test
	public void EntityFromJsonInvalidPropertyTest() {
		// TODO
	}
}
