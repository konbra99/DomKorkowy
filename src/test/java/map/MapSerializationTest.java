package map;

import com.google.gson.JsonObject;
import logic.TestRect;
import map.json.JsonUtils;
import org.junit.Test;

import static constants.JsonSerializationStatus.MAP_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

	@Test
	public void MapFromJsonTest() {
		String str = """
				{"mapName":"map_name","maxPlayers":0,"author":"author","time":0,"stages":[{"backgroundId":0,"entities":\
				[{"type":"TestRect","boolTest":false,"intTest":10,"floatTest":10.0,"stringTest":"abc","id":0},\
				{"type":"TestRect","boolTest":false,"intTest":10,"floatTest":10.0,"stringTest":"abc","id":1}]}]}""";
		JsonObject obj = JsonUtils.fromString(str);
		MapManager map = new MapManager();
		int status = map.fromJson(obj);

		assertEquals(status, MAP_OK);
		assertEquals(map.stages.size(), 1);
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
