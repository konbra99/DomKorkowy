package map;

import com.google.gson.JsonObject;
import graphics.Config;
import logic.*;
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
		System.out.println(JsonUtils.toStringPretty(obj));
	}

	@Test
	public void FullMapToJsonTest() {
		MapManager map = new MapManager();
		Stage stage;

		/////////////////////////
		// STAGE 1
		stage = new Stage("background/sky.png", -0.7f, -0.45f);

		// obiekty statyczne
		stage.addMapEntity(new StaticObject(0.1f, -0.96f, 0.13f, 0.6f, "trees/tree4.png"));
		stage.addMapEntity(new StaticObject(0.7f, -0.95f, 0.06f, 0.3f, "trees/tree4.png"));
		stage.addMapEntity(new StaticObject(-0.65f, -0.11f, 0.12f, 0.2f, "trees/tree1.png"));

		// drzwi
		stage.addMapEntity(new Door(-0.7f, -0.9f, 0.10f, 0.25f, "door.png", false));
		stage.addMapEntity(new Door(0.8f, 0.3f, 0.10f, 0.25f, "door.png", true));

		// przeszkody
		stage.addMapEntity(new Obstacle(-0.8f, -0.52f, 0.13f, 0.08f, "obstacles/spikes_2.png"));
		stage.addMapEntity(new Obstacle(0.5f, -0.52f, 0.13f, 0.08f, "obstacles/spikes_2.png"));
		stage.addMapEntity(new Obstacle(-0.4f, -0.10f, 0.11f, 0.17f, "obstacles/swinging_spike_block.png"));
		stage.addMapEntity(new WheelObstacle(0.4f, 0.4f, 0.11f, 0.11f* Config.RESOLUTION, "obstacles/blade_2.png", 1.0f));

		// planformy
		stage.addMapEntity(new Platform(-0.2f, -0.6f, 1.0f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(0.0f, 0.2f, 1.0f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(-1.0f, -0.6f, 0.5f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(-0.75f, -0.2f, 0.8f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "platforma.png"));

		// moby
		stage.addMapEntity(new Mob(0.5f, -0.5f, 0.08f, 0.18f,"koniec.png",-0.2f,0.7f,0.01f, Entity.RIGHT));
		stage.buildHashMap();
		map.addStage(stage);

		/////////////////////////
		// STAGE 2
		stage = new Stage("background/back.png", 0.8f, -0.45f);

		// drzwi
		stage.addMapEntity(new Door(0.8f, -0.9f, 0.10f, 0.25f, "door.png", false));
		stage.addMapEntity(new Door(0.8f, 0.6f, 0.10f, 0.25f, "door.png", true));

		// przeszkody
		stage.addMapEntity(new WheelObstacle(-1.0f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
		stage.addMapEntity(new WheelObstacle(-0.8f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
		stage.addMapEntity(new WheelObstacle(-0.6f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
		stage.addMapEntity(new WheelObstacle(-0.4f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
		stage.addMapEntity(new WheelObstacle(0.0f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
		stage.addMapEntity(new WheelObstacle(0.2f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
		stage.addMapEntity(new WheelObstacle(0.4f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
		stage.addMapEntity(new WheelObstacle(0.6f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
		stage.addMapEntity(new WheelObstacle(0.8f, -0.6f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));

		stage.addMapEntity(new WheelObstacle(-0.8f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
		stage.addMapEntity(new WheelObstacle(-0.6f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", 1.0f));
		stage.addMapEntity(new WheelObstacle(-0.4f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
		stage.addMapEntity(new WheelObstacle(-0.2f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 1.0f));
		stage.addMapEntity(new WheelObstacle(0.0f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
		stage.addMapEntity(new WheelObstacle(0.2f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 1.0f));
		stage.addMapEntity(new WheelObstacle(0.4f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));
		stage.addMapEntity(new WheelObstacle(0.6f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 1.0f));
		stage.addMapEntity(new WheelObstacle(0.8f, 0.15f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 3.0f));

		// platformy
		stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(-1.0f, -0.25f, 0.8f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(0.0f, -0.25f, 1.0f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(-0.8f, 0.5f, 2.0f, 0.1f, "platforma.png"));

		stage.buildHashMap();
		map.addStage(stage);

		/////////////////////////
		// STAGE 3
		stage = new Stage("background/back.png", 0.8f, 0.34f);

		// drzwi
		stage.addMapEntity(new Door(0.8f, 0.6f, 0.10f, 0.25f, "door.png", false));
		stage.addMapEntity(new Door(0.8f, -0.9f, 0.10f, 0.25f, "door.png", true));

		// przeszkody
		stage.addMapEntity(new WheelObstacle(-1f, -1.1f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", -1.0f));
		stage.addMapEntity(new WheelObstacle(-0.7f, -0.38f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
		stage.addMapEntity(new WheelObstacle(-0.4f, -1.1f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
		stage.addMapEntity(new WheelObstacle(0.0f, -0.38f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -3.0f));
		stage.addMapEntity(new WheelObstacle(0.3f, -1.1f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", -1.0f));
		stage.addMapEntity(new WheelObstacle(0.6f, -0.38f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", -3.0f));

		stage.addMapEntity(new WheelObstacle(-0.35f, 0.37f, 0.20f, 0.20f*Config.RESOLUTION,  "obstacles/blade_2.png", 2.0f));
		stage.addMapEntity(new WheelObstacle(0.3f, 0.37f, 0.20f, 0.20f*Config.RESOLUTION, "obstacles/blade_2.png", 2.0f));

		// platformy
		stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(-1.0f, -0.25f, 0.8f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(0.0f, -0.25f, 1.0f, 0.1f, "platforma.png"));
		stage.addMapEntity(new Platform(-0.8f, 0.5f, 2.0f, 0.1f, "platforma.png"));

		stage.buildHashMap();
		map.addStage(stage);

		/////////////////////////
		// Credits
		stage = new Stage("background/finalStage.png", 0.0f, -0.5f);
		stage.addMapEntity(new Platform(-1.0f, -1.0f, 2.0f, 0.1f, "plank_platform_brick1.png"));

		stage.buildHashMap();

		map.addStage(stage);

		JsonObject obj = map.toJson();
		try {
			JsonUtils.toFilePretty(obj, "Mapka5.json");
			System.out.println(JsonUtils.toStringPretty(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void FullMapFromJsonTest() {
		MapManager map = new MapManager();
		try {
			JsonObject obj = JsonUtils.fromFile("Mapka5.json");
			map.fromJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
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
