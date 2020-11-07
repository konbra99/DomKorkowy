package map;

import items.items_tree.TestRect;

public class MapWriterTest {

	//@Test
	public void MapEmptyTest() {
		String filepath = "src/main/resources/Maps/test_map.json";
		MapManager map = new MapManager();
		MapWriter.write(map, filepath);
	}

	//@Test
	public void MapPropertiesTest() {
		String filepath = "src/main/resources/Maps/test_map.json";
		MapManager map = new MapManager();
		MapProperties properties = new MapProperties();
		map.addProperties(properties);
		MapWriter.write(map, filepath);
	}

	//@Test
	public void MapStagesTest() {
		String filepath = "src/main/resources/Maps/test_map.json";
		MapManager map = new MapManager();
		Stage stage1 = new Stage()
				.addItem(new TestRect())
				.addItem(new TestRect());
		map.addStage(stage1);

		MapWriter.write(map, filepath);
	}

	//@Test
	public void MapPropertiesStagesTest() {
		String filepath = "src/main/resources/Maps/test_map.json";
		MapManager map = new MapManager();
		map.addProperties(new MapProperties());
		Stage stage1 = new Stage()
				.addItem(new TestRect())
				.addItem(new TestRect());
		map.addStage(stage1);

		MapWriter.write(map, filepath);
	}
}
