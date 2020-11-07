package map;

import com.google.gson.*;
import items.items_utils.ItemFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class MapReader {

	public static void read(MapManager map, String filepath) {
		try (FileReader reader = new FileReader(filepath)) {

			// json data
			JsonElement map_properties;
			JsonElement stage_properties;
			JsonObject root;
			JsonArray stages;
			JsonArray items;

			// map data
			Stage currentStage;

			// gson
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser parser = new JsonParser();
			root = parser.parse(reader).getAsJsonObject();

			// map properties
			map_properties = root.getAsJsonObject("properties");
			map.addProperties(gson.fromJson(map_properties, MapProperties.class));

			// map stages
			stages = root.getAsJsonArray("stages");

			for(JsonElement stage: stages) {
				currentStage = new Stage();

				// stage properties
				stage_properties = stage.getAsJsonObject().getAsJsonObject("properties");
				currentStage.addProperties(gson.fromJson(stage_properties, StageProperties.class));
				map.addStage(currentStage);

				// items
				items = stage.getAsJsonObject().getAsJsonArray("items");
				for(JsonElement item: items) {
					currentStage.addItem(ItemFactory.getFromJson(gson, item));
				}
				currentStage.loadItems();
			}
		} catch (IOException e) {

		}
	}

	public void read(MapManager map, Writer writer) {
		System.out.println("Hello");
	}
}
