package map;

import com.google.gson.JsonObject;
import logic.TestRect;
import map.json.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class StageSerializationTest {

	@Test
	public void EmptyStageToJsonTest() {
		String str = """
				{"backgroundId":0,"entities":[]}""";
		Stage stage = new Stage();
		JsonObject obj = stage.toJson();

		assertEquals(str, obj.toString());
	}

	@Test
	public void StageToJsonTest() {
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		String s = "abc";
		String str = """
				{"backgroundId":0,"entities":[{"posX":0.0,"posY":0.0,"width":0.0,"height":0.0,"textureName":null,\
				"boolTest":false,"intTest":10,"floatTest":99.99,"stringTest":"abc","id":0,"type":"TestRect"}]}""";
		Stage stage = new Stage();
		stage.addEntity(0, new TestRect(b, i, f, s));
		JsonObject obj = stage.toJson();

		assertEquals(str, obj.toString());
	}

	//@Test
	public void EmptyStageFromJsonTest() {
		try {
			String str = """
				{"backgroundId":0,"entities":[]}""";
			JsonObject obj = JsonUtils.fromString(str);
			Stage stage = new Stage();
			stage.fromJson(obj);

			assertEquals(stage.all.size(), 0);
			assertTrue(true);

		} catch (Exception e) {
			fail();
		}
	}

	//@Test
	public void StageFromJsonTest() {
		try {
			int backgroundId = 10;
			int rectId = 20;
			boolean b = false;
			int i = 10;
			float f = 99.99f;
			String s = "abc";
			String str = """
				{"backgroundId":10,"entities":[{"textureName":"test", "boolTest":false,"intTest":10,"floatTest":99.99,"stringTest":"abc",\
				"id":20,"type":"TestRect"}]}""";

			JsonObject obj = JsonUtils.fromString(str);
			Stage stage = new Stage();
			stage.fromJson(obj);

			TestRect rect = (TestRect)stage.all.get(rectId);

			assertEquals(rect.boolTest, b);
			assertEquals(rect.intTest, i);
			assertEquals(rect.floatTest, f, Float.MIN_VALUE);
			assertEquals(rect.stringTest, s);
			assertTrue(true);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void NoexistentStagePropertyTest() {
		try {
			String str = """
				{"bad_property":10,"entities":[{"type":"TestRect","boolTest":false,"intTest":10,\
				"floatTest":99.99,"stringTest":"abc","id":20}]}""";
			JsonObject obj = JsonUtils.fromString(str);
			Stage stage = new Stage();
			stage.fromJson(obj);
			fail();

		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	//@Test
	public void AmbigiousStagePropertyTest() {
		try {
			String str = """
				{"backgroundId":10, "backgroundId":20, "entities":[{"type":"TestRect","boolTest":false,"intTest":10,\
				"floatTest":99.99,"stringTest":"abc","id":20}]}""";
			JsonObject obj = JsonUtils.fromString(str);
			Stage stage = new Stage();
			stage.fromJson(obj);

			assertNotNull(stage.all.get(20));

		} catch (NullPointerException e) {
			fail();
		}
	}
}
