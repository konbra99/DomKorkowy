package map;

import com.google.gson.JsonObject;
import entity.entities_tree.TestRect;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
	public void NotEmptyStageToJsonTest() {
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		String s = "abc";
		String str = """
				{"backgroundId":0,"entities":[{"type":"TestRect","boolTest":false,"intTest":10,"floatTest":99.99,"stringTest":"abc","id":0}]}""";
		Stage stage = new Stage();
		stage.addEntity(0, new TestRect(b, i, f, s));
		JsonObject obj = stage.toJson();

		assertEquals(str, obj.toString());
	}

	//@Test
	public void EmptyStageFromJsonTest() {
	}

	//@Test
	public void NotEmptyStageFromJsonTest() {
		boolean b = false;
		int i = 10;
		float f = 99.99f;
		String s = "abc";
		String str = """
				{"backgroundId":0,"entities":[{"type":"TestRect","boolTest":false,"intTest":10,"floatTest":99.99,"stringTest":"abc","id":0}]}""";
		Stage stage = new Stage();
		stage.addEntity(0, new TestRect(b, i, f, s));
		JsonObject obj = stage.toJson();

		assertEquals(str, obj.toString());
	}
}
