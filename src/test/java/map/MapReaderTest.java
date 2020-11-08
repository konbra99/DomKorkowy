package map;

import org.junit.jupiter.api.Test;
import static constants.MapPropertiesDefault.*;
import static constants.MapReadWriteStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Reader;
import java.io.StringReader;

public class MapReaderTest {

	// TESTY NEGATYWNE
	//@Test
	public void MapPropertiesNullTest() {

		int status;
		String source =
				"""
				{"properties":null,"stages":[]}""";
		Reader reader = new StringReader(source);
		MapManager map = new MapManager();

		status = MapReader.read(map, reader);
		assertEquals(status, MAP_PROPERTIES_NULL);
	}

	//@Test
	public void MapStagesNullTest() {
		int status;
		String source =
				"""
				{"properties":{"name":"none","author":"none","maxPlayers":10,"time":120,"creationDate":-1},"stages":[]}""";
		Reader reader = new StringReader(source);
		MapManager map = new MapManager();

		status = MapReader.read(map, reader);
		assertEquals(status, MAP_STAGES_NULL);
	}

	// TESTY POZYTYWNE
	//@Test
	public void MapDefaultPropertiesTest() {
		int status;
		String source =
				"""
				{"properties":{"name":"none","author":"none","maxPlayers":10,"time":120,"creationDate":-1},"stages":\
				[{"properties":{"backgrounId":0},"entities":[{"z":0.0,"x":0.0,"y":0.0,"id":-1,"type":"TestRect"},\
				{"z":0.0,"x":0.0,"y":0.0,"id":-1,"type":"TestRect"}]}]}""";
		Reader reader = new StringReader(source);
		MapManager map = new MapManager();
		status = MapReader.read(map, reader);

		assertEquals(status, MAP_OK);
		assertEquals(map.properties.name, DEFAULT_NAME);
		assertEquals(map.properties.author, DEFAULT_AUTHOR);
		assertEquals(map.properties.players, DEFAULT_PLAYERS);
		assertEquals(map.properties.time, DEFAULT_TIME);
		assertEquals(map.properties.date, DEFAULT_DATE);
	}

	@Test
	public void MapManyStagesTest() {
		int status;
		String source =
				"""
				{"properties":{"name":"none","author":"none","maxPlayers":10,"time":120,"creationDate":-1},"stages":\
				[{"properties":{"backgrounId":0},"entities":[{"z":0.0,"x":0.0,"y":0.0,"id":-1,"type":"TestRect"},\
				{"z":0.0,"x":0.0,"y":0.0,"id":-1,"type":"TestRect"}]}]}""";
		Reader reader = new StringReader(source);
		MapManager map = new MapManager();
		status = MapReader.read(map, reader);

		assertEquals(status, MAP_OK);
		assertEquals(map.stages.size(), 1);
	}
}
