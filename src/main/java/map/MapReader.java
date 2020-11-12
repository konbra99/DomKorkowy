package map;

import com.google.gson.*;
import entity.entities_utils.EntityFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static constants.JsonSerializationStatus.*;

public class MapReader {

	public static int read(MapManager map, String filepath) {
		try (FileReader reader = new FileReader(filepath)) {
			return read(map, reader);
		} catch (IOException e) {
			e.printStackTrace();
			return MAP_READ_ERROR;
		}
	}

	public static int read(MapManager map, Reader reader) {

		// json data
		JsonElement map_properties;
		JsonElement stage_properties;
		JsonObject root;
		JsonArray stages;
		JsonArray items;

		// map data
		Stage currentStage;

		// gson
		Gson gson = new GsonBuilder().serializeNulls().create();
		JsonParser parser = new JsonParser();
		root = parser.parse(reader).getAsJsonObject();

		// map properties
		if (root.get("properties").isJsonNull()) {
			return MAP_PROPERTIES_NULL;
		}
		map_properties = root.getAsJsonObject("properties");
		map.addProperties(gson.fromJson(map_properties, MapProperties.class));

		// map stages
		stages = root.getAsJsonArray("stages");
		if (stages.size() == 0) {
			return MAP_STAGES_NULL;
		}
		for(JsonElement stage: stages) {
			currentStage = new Stage();

			// stage properties
			stage_properties = stage.getAsJsonObject().getAsJsonObject("properties");
			currentStage.addProperties(gson.fromJson(stage_properties, StageProperties.class));
			map.addStage(currentStage);

			// items
			items = stage.getAsJsonObject().getAsJsonArray("entities");
			for (JsonElement item : items) {
				currentStage.addEntity(EntityFactory.getFromJson(gson, item));
			}
			currentStage.buildStage();
		}

		return MAP_OK;
	}
}
