package map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import entity.entities_tree.Entity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class MapWriter {

	public static void write(MapManager map, String filepath) {
		try (FileWriter writer = new FileWriter(filepath)) {
			write(map, writer);
			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write(MapManager map, Writer writer) {

		Gson gson = new GsonBuilder()
				//.setPrettyPrinting()
				.serializeNulls()
				.create();
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
			stage.add("entities", items);
			for(Entity i: s.allEntities) {
				obj = gson.toJsonTree(i);
				items.add(obj);
			}

			// add stage
			stages.add(stage);
		}

		// results
		gson.toJson(root, writer);
	}
}
