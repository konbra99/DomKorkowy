package server;

public class TempRoom {
	public String room_name;
	public String room_admin;
	public String map_name;
	public String max_players;

	public TempRoom(String room_name, String room_admin, String map_name, String max_players) {
		this.room_name = room_name;
		this.room_admin = room_admin;
		this.map_name = map_name;
		this.max_players = max_players;
	}
}
