package map;

import com.google.gson.JsonObject;
import entity.entities_tree.TestRect;
import map.json.JsonUtils;
import org.junit.Test;

import static constants.JsonSerializationStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StageSerializationTest {

	//@Test
	public void EmptyStageToJsonTest() {
		String str = """
				{"backgroundId":0,"entities":[]}""";
		Stage stage = new Stage();
		JsonObject obj = stage.toJson();

		assertEquals(str, obj.toString());
	}

	//@Test
	public void StageToJsonTest() {
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		String s = "abc";
		String str = """
				{"backgroundId":0,"entities":[{"boolTest":false,"intTest":10,"floatTest":99.99,"stringTest":"abc",\
				"id":0,"type":"TestRect"}]}""";
		Stage stage = new Stage();
		stage.addEntity(0, new TestRect(b, i, f, s));
		JsonObject obj = stage.toJson();

		assertEquals(str, obj.toString());
	}

	//@Test
	public void EmptyStageFromJsonTest() {
		String str = """
				{"backgroundId":0,"entities":[]}""";
		JsonObject obj = JsonUtils.fromString(str);
		Stage stage = new Stage();
		int status = stage.fromJson(obj);

		assertEquals(status, STAGE_OK);
		assertEquals(stage.backgroundId, 0);
		assertEquals(stage.all.size(), 0);
	}

	//@Test
	public void StageFromJsonTest() {
		int backgroundId = 10;
		int rectId = 20;
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		String s = "abc";
		String str = """
				{"backgroundId":10,"entities":[{"type":"TestRect","boolTest":false,"intTest":10,\
				"floatTest":99.99,"stringTest":"abc","id":20}]}""";

		JsonObject obj = JsonUtils.fromString(str);
		Stage stage = new Stage();
		int status = stage.fromJson(obj);

		assertEquals(status, STAGE_OK);
		assertEquals(stage.backgroundId, backgroundId);
		TestRect rect = (TestRect)stage.all.get(rectId);
		assertNotNull(rect);

		assertEquals(rect.boolTest, b);
		assertEquals(rect.intTest, i);
		assertEquals(rect.floatTest, f, Float.MIN_VALUE);
		assertEquals(rect.stringTest, s);
	}

	//@Test
	public void StageNoexistentPropertyTest() {
		String str = """
				{"bad_property":10,"entities":[{"type":"TestRect","boolTest":false,"intTest":10,\
				"floatTest":99.99,"stringTest":"abc","id":20}]}""";
		JsonObject obj = JsonUtils.fromString(str);
		Stage stage = new Stage();
		int status = stage.fromJson(obj);

		assertEquals(status, NONEXISTENT_PROPERTY);
	}

	//@Test
	public void StageInvalidPropertyTest() {
		// TODO
	}
}
