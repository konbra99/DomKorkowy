package server;

import com.google.gson.JsonObject;
import map.json.JsonSerializable;
import java.util.ArrayList;
import static server.Protocol.*;

public class Lobby implements JsonSerializable {

	// zmienne
	public int id;
	public String room_name;
	public String admin_name;
	public String map_name;
	public int max_players;
	ArrayList<ClientThread> clients;

	public Lobby() {

	}

	public Lobby(int id, String room_name, String admin_name, String map_name, int max_players) {
		this.id = id;
		this.room_name = room_name;
		this.admin_name = admin_name;
		this.map_name = map_name;
		this.max_players = max_players;
		clients = new ArrayList<>();
	}

	public synchronized void join(ClientThread client) {
		if (clients.size() == max_players) {
			// brak miejsc
			client.writeInt(LOBBY_MY_JOIN);
			client.writeInt(LOBBY_IS_FULL);
		}
		else {
			// jest wolne miejsce
			client.lobbyId = this.id;
			client.writeInt(LOBBY_MY_JOIN);
			client.writeInt(LOBBY_JOINED);

			for(ClientThread c: clients) {
				c.writeInt(LOBBY_OTHER_JOIN);
				c.writeInt(client.id);
				c.writeInt(LOBBY_OTHER_STATUS);
				c.writeInt(client.id);
				c.writeBoolean(client.status);

				client.writeInt(LOBBY_OTHER_JOIN);
				client.writeInt(c.id);
				client.writeInt(LOBBY_OTHER_STATUS);
				client.writeInt(c.id);
				client.writeBoolean(c.status);
			}
			clients.add(client);
		}
	}

	public void exit(ClientThread client) {
		// komunikat do wszystkich graczy
		for(ClientThread c: clients) {
			c.writeInt(LOBBY_OTHER_EXIT);
			c.writeInt(client.id);
		}
		clients.remove(client);

		// pusty pokoj
		if (clients.size() == 0)
			Server.lobbies.remove(id);
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
