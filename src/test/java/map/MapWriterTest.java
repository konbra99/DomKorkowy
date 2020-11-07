package map;

import items.items_tree.TestRect;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapWriterTest {

	@Test
	public void MapEmptyTest() {
		StringWriter writer = new StringWriter();
		String results = "";
		MapManager map = new MapManager();
		MapWriter.write(map, writer);
		assertEquals(writer.toString(), results);
	}

	//@Test
	public void MapDefaultPropertiesTest() {
		StringWriter writer = new StringWriter();
		String results =
                """
				{"properties":{"name":"none","author":"none","maxPlayers":10,"time":120,"creationDate":-1},"stages":[]}""";
		MapManager map = new MapManager();
		MapProperties properties = new MapProperties();
		map.addProperties(properties);
		MapWriter.write(map, writer);
		assertEquals(writer.toString(), results);
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
