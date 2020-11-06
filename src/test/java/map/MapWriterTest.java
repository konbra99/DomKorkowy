package map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import items.items_tree.GameItem;
import items.items_tree.TestRect;
import items.items_utils.GameItemManager;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.GL;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWriterTest {

	//@Test
	public void WriteMapTest() {
		try {
			// open file
			FileWriter writer = new FileWriter("src/main/resources/Maps/test_map.json");
			MapProperties mapProperties = new MapProperties();

			// write test
			MapWriter.writeMap(writer, mapProperties);

			// close file
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void WriteItemsTest() {
		try {
			// open file
			FileWriter writer = new FileWriter("src/main/resources/Maps/test_map.json");
			GameItemManager itemManager = new GameItemManager()
					.addItem(new TestRect(0, 0f, 0f))
					.addItem(new TestRect(1, 1f, 1f))
					.addItem(new TestRect(2, 2f, 2f));

			// write test
			MapWriter.writeItems(writer, itemManager);

			// close file
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void WriteAllTest() {
		// filepath
		String filepath = "src/main/resources/Maps/test_map.json";
		MapProperties mapProperties = new MapProperties();
		GameItemManager itemManager = new GameItemManager()
				.addItem(new TestRect(0, 0f, 0f))
				.addItem(new TestRect(1, 1f, 1f))
				.addItem(new TestRect(2, 2f, 2f));

		// write test
		MapWriter.write(mapProperties, itemManager, filepath);
	}
}
