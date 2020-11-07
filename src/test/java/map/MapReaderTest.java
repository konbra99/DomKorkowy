package map;

import org.junit.jupiter.api.Test;

public class MapReaderTest {

	@Test
	public void MapPropertiesStagesTest() {
		String filepath = "src/main/resources/Maps/test_map.json";
		MapManager map = new MapManager();

		// BEFORE
		System.out.println(map.properties);
		System.out.println(map.stages);

		MapReader.read(map, filepath);

		// AFTER
		System.out.println(map.properties);
		System.out.println(map.stages);
		System.out.println(map.stages.get(0).allItems);
	}
}
