package map;

import com.google.gson.annotations.SerializedName;

/**
 * Przechowuje podstawowe parametry mapy.
 */
public class MapProperties {

	public String name;
	public String author;
	public int maxPlayers;
	public int background;
	public int time;
	public int creationDate;

	// domysle wartosci parametrow
	public MapProperties() {
		name = "empty";
		author = "empty";
		maxPlayers = 10;
		background = 0;
		time = 120;
		creationDate = -1;
	}
}
