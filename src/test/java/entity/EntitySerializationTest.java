package entity;

import com.google.gson.JsonObject;
import logic.TestRect;
import map.json.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntitySerializationTest {

	// @Test
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

	// @Test
	public void EntityFromJsonTest() {
		try {
			boolean b = false;
			int i = 10;
			float f = 99.99f;
			String s = "abc";
			String str = """
	                {"type":"TestRect","intTest":10,"floatTest":99.99,"boolTest":false,"stringTest":"abc","id":20}""";
			JsonObject obj = JsonUtils.fromString(str);
			TestRect rect = new TestRect();
			rect.fromJson(obj);

			assertEquals(rect.boolTest, b);
			assertEquals(rect.intTest, i);
			assertEquals(rect.floatTest, f, Float.MIN_VALUE);
			assertEquals(rect.stringTest, s);
			assertTrue(true);

		} catch (Exception e) {
			fail();
		}
	}

	// @Test
	public void EntityFromJsonNonexistentPropertyTest() {
		try {
			String str = """
                {"type":"TestRect","intTest":10,"floatTest":99.99, "stringTest":"abc","id":20}""";
			JsonObject obj = JsonUtils.fromString(str);
			TestRect rect = new TestRect();
			rect.fromJson(obj);
			fail();

		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
}
