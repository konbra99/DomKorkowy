package map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import items.items_tree.Item;

import java.awt.image.ImagingOpException;
import java.io.FileWriter;
import java.io.IOException;

public class MapWriter {

	public static void write(MapManager map, String filepath) {
		try (FileWriter writer = new FileWriter(filepath)) {
			// gson
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonObject root;
			JsonElement obj;
			JsonObject stage;
			JsonArray items;
			JsonArray stages;

			// root
			root = new JsonObject();

			// map properties
			obj = gson.toJsonTree(map.properties);
			root.add("properties", obj);

			// stages
			stages = new JsonArray();
			root.add("stages", stages);

			for(Stage s: map.stages) {
				// new stage
				stage = new JsonObject();

				// stage properties
				obj = gson.toJsonTree(s.properties);
				stage.add("properties", obj);

				// stage items
				items = new JsonArray();
				stage.add("items", items);
				for(Item i: s.allItems) {
					obj = gson.toJsonTree(i);
					items.add(obj);
				}

				// add stage
				stages.add(stage);
			}

			// results
			gson.toJson(root, writer);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
