package map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import map.json.JsonSerializable;

import java.util.ArrayList;
import java.util.List;

import static constants.JsonSerializationStatus.*;

/** Przechowuje liste scen oraz numer aktualnej sceny. */
public class MapManager implements JsonSerializable {

	// zmienne
	public String mapName;
	public String author;
	public int maxPlayers;
	public int time;

	// sceny
	private List<Stage> stages;
	int currentStage;

	public MapManager() {
		stages = new ArrayList<>();
		currentStage = 0;
	}

	/** Move bierzacej sceny. */
	public void move() {
	}

	/** Update bierzacej sceny. */
	public void update() {
		stages.get(currentStage).update();
	}

	/** Draw bierzacej sceny. */
	public void draw() {
		stages.get(currentStage).draw();
	}

	/** Dodaje nowa scene do mapy.*/
	public void addStage(Stage stage) {
		stages.add(stage);
	}

	/** Zwraca bierzaca scene. */
	public Stage getCurrentStage() {
		return stages.get(currentStage);
	}

	/** Przechodzi do nastepnej sceny, zwraca false, jesli nastepna scena nie istnieje. */
	public boolean nextStage() {
		return false;
	}

	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		JsonArray json_stages = new JsonArray();
		obj.addProperty("mapName", mapName);
		obj.addProperty("maxPlayers", maxPlayers);
		obj.addProperty("author", author);
		obj.addProperty("time", time);
		obj.add("stages", json_stages);

		for(Stage stage: stages) {
			JsonObject temp = stage.toJson();
			json_stages.add(temp);
		}
		return obj;
	}

	public void fromJson(JsonObject obj) {
		mapName = obj.get("mapName").getAsString();
		author = obj.get("author").getAsString();
		maxPlayers = obj.get("maxPlayers").getAsInt();
		time = obj.get("time").getAsInt();

		JsonArray json_stages = obj.getAsJsonArray("stages");
		for(JsonElement element: json_stages) {
			JsonObject temp = (JsonObject) element;

			Stage stage = new Stage();
			stage.fromJson(temp);
			addStage(stage);
		}
	}
}
