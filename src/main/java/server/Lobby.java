package server;

import com.google.gson.JsonObject;
import map.json.JsonSerializable;
import java.util.ArrayList;
import static server.Protocol.*;

public class Lobby implements JsonSerializable {

	// zmienne
	public int id;
	public String lobby_name;
	public String admin_name;
	public String map_name;
	public String map_context;
	public int max_players;
	public boolean prev_status;
	private ClientThread admin;
	ArrayList<ClientThread> clients;

	public Lobby() {
		this.id = -1;
		this.lobby_name = "default";
		this.admin_name = "default";
		this.map_name = "default";
		this.max_players = -1;
		this.map_context = "default";
	}

	public Lobby(int id, String lobby_name, String admin_name, String map_name, int max_players, String map_context) {
		this.id = id;
		this.lobby_name = lobby_name;
		this.admin_name = admin_name;
		this.map_name = map_name;
		this.max_players = max_players;
		this.map_context = map_context;

		this.prev_status = false;
		this.clients = new ArrayList<>();
		this.admin = null;
	}

	public synchronized void join(ClientThread client) {
		if (clients.size() == max_players) {
			// brak miejsc
			client.writeInt(LOBBY_MY_JOIN);
			client.writeInt(LOBBY_IS_FULL);
		} else {
			// jest wolne miejsce
			client.setLobby(this);
			client.writeInt(LOBBY_MY_JOIN);
			client.writeInt(LOBBY_JOINED);

			for (ClientThread c : clients) {
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
			checkAdmin();
			checkStatus();
		}
	}

	public synchronized void status(ClientThread client, boolean tempStatus) {
		// komunikat do wszystkich graczy
		for (ClientThread c : clients) {
			c.writeInt(LOBBY_OTHER_STATUS);
			c.writeInt(client.id);
			c.writeBoolean(tempStatus);
		}
		client.status = tempStatus;
		checkStatus();
	}

	public synchronized void exit(ClientThread client) {
		// komunikat do wszystkich graczy
		for (ClientThread c : clients) {
			c.writeInt(LOBBY_OTHER_EXIT);
			c.writeInt(client.id);
		}
		clients.remove(client);
		if (admin == client)
			admin = null;

		if (clients.size() == 0)
			// puste lobby, usuwamy
			Server.lobbies.remove(id);
		else {
			// niepuste lobby, sprawdzamy admina i status
			checkAdmin();
			checkStatus();
		}
	}

	public synchronized void start() {
		for (ClientThread c : clients)
			c.writeInt(LOBBY_START);
	}

	public synchronized void checkAdmin() {
		if (admin == null && clients.size() > 0) {
			admin = clients.get(0);
			clients.get(0).writeInt(LOBBY_ADMIN);
		}
	}

	public synchronized void checkStatus() {
		boolean status = true;
		for (ClientThread c : clients)
			status &= c.status;

		if (status && !prev_status) {
			// zmiana na ready
			admin.writeInt(LOBBY_READY);
			admin.writeBoolean(true);
			prev_status = true;
		} else if (!status && prev_status) {
			// zmiana na not ready
			admin.writeInt(LOBBY_READY);
			admin.writeBoolean(false);
			prev_status = false;
		}
	}

	public synchronized void updatePosition(ClientThread client, float x, float y) {
		for (ClientThread c : clients) {
			if (client != c) {
				c.writeInt(MULTI_OTHER_POSITION);
				c.writeInt(client.id);
				c.writeFloat(x);
				c.writeFloat(y);
			}
		}
	}

	public synchronized void updateWeapon(ClientThread client, int weapon) {
		for (ClientThread c : clients) {
			if (client != c) {
				c.writeInt(MULTI_OTHER_WEAPON);
				c.writeInt(client.id);
				c.writeInt(weapon);
			}
		}
	}

	public synchronized void updateStage(ClientThread client, int stageX, int stageY) {
		for (ClientThread c : clients) {
			if (client != c) {
				c.writeInt(MULTI_OTHER_STAGE);
				c.writeInt(client.id);
				c.writeInt(stageX);
				c.writeInt(stageY);
			}
		}
	}

	public synchronized void updateAttack(ClientThread client, int id) {
		for (ClientThread c : clients) {
			if (c.id == id) {
				c.writeInt(MULTI_OTHER_ATTACK);
				c.writeInt(client.id);
				break;
			}
		}
	}

	public synchronized void updateDirection(ClientThread client, int direction) {
		for (ClientThread c : clients) {
			if (client != c) {
				c.writeInt(MULTI_OTHER_DIRECTION);
				c.writeInt(client.id);
				c.writeInt(direction);
			}
		}
	}

	public synchronized void removeEntity(ClientThread client, int stage, int id) {
		for (ClientThread c : clients) {
			if (c != client) {
				c.writeInt(MULTI_OTHER_REMOVE);
				c.writeInt(stage);
				c.writeInt(id);
			}
		}
	}

	public synchronized void updateHit(ClientThread client) {
		for (ClientThread c : clients) {
			if (c != client) {
				c.writeInt(MULTI_OTHER_HIT);
				c.writeInt(client.id);
			}
		}
	}

	public synchronized void updateDeath(ClientThread client, int id) {
		for (ClientThread c : clients) {
			if (c.id == id) {
				c.writeInt(MULTI_OTHER_DEATH);
				break;
			}
		}
	}

	@Override
	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", id);
		obj.addProperty("lobby_name", lobby_name);
		obj.addProperty("admin_name", admin_name);
		obj.addProperty("map_name", map_name);
		obj.addProperty("max_players", max_players);
		obj.addProperty("map_context", map_context);
		return obj;
	}

	@Override
	public void fromJson(JsonObject obj) {
		id = obj.get("id").getAsInt();
		lobby_name = obj.get("lobby_name").getAsString();
		admin_name = obj.get("admin_name").getAsString();
		map_name = obj.get("map_name").getAsString();
		max_players = obj.get("max_players").getAsInt();
		map_context = obj.get("map_context").getAsString();

		this.prev_status = false;
		this.clients = new ArrayList<>();
		this.admin = null;
	}

	public void setId(int id) {
		this.id = id;
	}
}
