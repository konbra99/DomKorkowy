package map;

/**
 * Przechowuje podstawowe parametry mapy.
 */
public class MapProperties {

	public String name;
	public String author;
	public int maxPlayers;
	public int time;
	public int creationDate;

	// domysle wartosci parametrow
	public MapProperties() {
		name = "empty";
		author = "empty";
		maxPlayers = 10;
		time = 120;
		creationDate = -1;
	}
}
