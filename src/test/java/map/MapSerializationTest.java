package map;

import com.google.gson.JsonObject;
import logic.TestRect;
import map.json.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapSerializationTest {

	//@Test
	public void EmptyMapToJsonTest() {
		MapManager map = new MapManager();

		JsonObject obj = map.toJson();
		System.out.println(obj);
	}

	//@Test
	public void NonemptyMapSingleStageToJsonTest() {
		MapManager map = new MapManager();
		Stage stage = new Stage();
		stage.addEntity(0, new TestRect(false, 10, 10f, "abc"));
		stage.addEntity(1, new TestRect(false, 10, 10f, "abc"));
		map.addStage(stage);

		JsonObject obj = map.toJson();
		System.out.println(obj);
	}

	//@Test
	public void MapFromJsonTest() {
		try {
			String str = """
				{"mapName":"map_name","maxPlayers":0,"author":"author","time":0,"stages":[{"backgroundId":0,"entities":\
				[{"type":"TestRect","boolTest":false,"intTest":10,"floatTest":10.0,"stringTest":"abc","id":0},\
				{"type":"TestRect","boolTest":false,"intTest":10,"floatTest":10.0,"stringTest":"abc","id":1}]}]}""";
			JsonObject obj = JsonUtils.fromString(str);
			MapManager map = new MapManager();
			map.fromJson(obj);
			Stage stage = map.getCurrentStage();

			assertNotNull(stage.all.get(0));
			assertNotNull(stage.all.get(1));
			assertNull(stage.all.get(2));

		} catch (Exception e) {
			fail();
		}

	}

	//@Test
	public void MapFromJsonNoexistentPropertyTest() {
		// TODO
	}

	//@Test
	public void MapFromJsonInvalidPropertyTest() {
		// TODO
	}
}
