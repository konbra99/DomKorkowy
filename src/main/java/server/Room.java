package server;

import com.google.gson.JsonObject;
import map.json.JsonSerializable;

public class Room implements JsonSerializable {
	public int id;
	public String room_name;
	public String admin_name;
	public String map_name;
	public int max_players;

	public Room() {

	}

	public Room(int id, String room_name, String admin_name, String map_name, int max_players) {
		this.id = id;
		this.room_name = room_name;
		this.admin_name = admin_name;
		this.map_name = map_name;
		this.max_players = max_players;
	}

	@Override
	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", id);
		obj.addProperty("room_name", room_name);
		obj.addProperty("admin_name", admin_name);
		obj.addProperty("map_name", map_name);
		obj.addProperty("max_players", max_players);
		return obj;
	}

	@Override
	public void fromJson(JsonObject obj) {
		id = obj.get("id").getAsInt();
		room_name = obj.get("room_name").getAsString();
		admin_name = obj.get("admin_name").getAsString();
		map_name = obj.get("map_name").getAsString();
		max_players = obj.get("max_players").getAsInt();
	}
}
