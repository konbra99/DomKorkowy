package map;

import items.items_utils.GameItemManager;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapReaderTest {

	//@Test
	public void ReadMapTest() {
		// map properties
		MapProperties mapProperties = new MapProperties();
		String filepath = "src/main/resources/Maps/test_map.json";

		try {
			// read
			Reader reader = new FileReader(filepath);
			MapReader.readMap(reader, mapProperties);

			// assert
			assertEquals(mapProperties.name, "empty");
			assertEquals(mapProperties.author, "empty");
			assertEquals(mapProperties.maxPlayers, 10);
			assertEquals(mapProperties.background, 0);
			assertEquals(mapProperties.creationDate, -1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void ReadItemsTest() {
		// item manager
		GameItemManager itemManager = new GameItemManager();
		String filepath = "src/main/resources/Maps/test_map.json";

		try {
			// read
			Reader reader = new FileReader(filepath);
			MapReader.readItems(reader, itemManager);

			System.out.println(itemManager.allItems);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
