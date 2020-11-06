package map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import items.items_utils.GameItemManager;

import java.io.FileWriter;
import java.io.IOException;

public class MapWriter {

	private static Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();

	public static void write(MapProperties mapProperties, GameItemManager itemManager, String filepath) {

		try {
			FileWriter writer = new FileWriter(filepath);

			writeMap(writer, mapProperties);
			writeItems(writer, itemManager);

			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void writeMap(FileWriter writer, MapProperties mapProperties) {
		if (mapProperties == null) {
			System.err.println("MapWriter.writeMap(): mapProperties can't be null!");
			System.exit(-1);
		}
		gson.toJson(mapProperties, writer);
	}

	public static void writeItems(FileWriter writer, GameItemManager itemManager) {
		if (itemManager == null) {
			System.err.println("MapWriter.writeItems(): itemManager can't be null!");
			System.exit(-1);
		}
		gson.toJson(itemManager.allItems, writer);
	}
}
