package map;

import static constants.MapPropertiesDefault.*;

/**
 * Przechowuje podstawowe parametry mapy.
 */
public class MapProperties {

	public String name;
	public String author;
	public int players;
	public int time;
	public int date;

	public MapProperties() {
		name = DEFAULT_NAME;
		author = DEFAULT_AUTHOR;
		players = DEFAULT_PLAYERS;
		time = DEFAULT_TIME;
		date = DEFAULT_DATE;
	}
}
