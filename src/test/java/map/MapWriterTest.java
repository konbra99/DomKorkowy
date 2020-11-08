package map;

import entity.entities_tree.TestRect;
import org.junit.jupiter.api.Test;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapWriterTest {

	//@Test
	public void MapEmptyTest() {
		StringWriter writer = new StringWriter();
		String results =
				"""
				{"properties":null,"stages":[]}""";
		MapManager map = new MapManager();
		MapWriter.write(map, writer);
		assertEquals(writer.toString(), results);
	}

	//@Test
	public void MapDefaultPropertiesTest() {
		StringWriter writer = new StringWriter();
		String results =
                """
				{"properties":{"name":"none","author":"none","players":10,"time":120,"date":-1},"stages":[]}""";
		MapManager map = new MapManager();
		MapProperties properties = new MapProperties();
		map.addProperties(properties);
		MapWriter.write(map, writer);
		assertEquals(writer.toString(), results);
	}

	//@Test
	public void MapStagesTest() {
		StringWriter writer = new StringWriter();
		String results =
				"""
				{"properties":null,"stages":[{"properties":{"backgrounId":0},"entities":[{"z":0.0,"x":0.0,"y":0.0,\
				"id":-1,"type":"TestRect"},{"z":0.0,"x":0.0,"y":0.0,"id":-1,"type":"TestRect"}]}]}""";
		MapManager map = new MapManager();
		Stage stage1 = new Stage()
				.addEntity(new TestRect())
				.addEntity(new TestRect());
		map.addStage(stage1);
		MapWriter.write(map, writer);
		assertEquals(writer.toString(), results);
	}

	//@Test
	public void MapPropertiesStagesTest() {
		StringWriter writer = new StringWriter();
		String results =
				"""
				{"properties":{"name":"none","author":"none","players":10,"time":120,"date":-1},"stages":\
				[{"properties":{"backgrounId":0},"entities":[{"z":0.0,"x":0.0,"y":0.0,"id":-1,"type":"TestRect"},\
				{"z":0.0,"x":0.0,"y":0.0,"id":-1,"type":"TestRect"}]}]}""";
		MapManager map = new MapManager();
		map.addProperties(new MapProperties());
		Stage stage1 = new Stage()
				.addEntity(new TestRect())
				.addEntity(new TestRect());
		map.addStage(stage1);
		MapWriter.write(map, writer);
		assertEquals(writer.toString(), results);
	}
}
