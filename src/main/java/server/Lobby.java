package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import map.json.JsonSerializable;
import java.util.ArrayList;
import java.util.Collections;

import static logic.constants.MultiModes.*;
import static server.Protocol.*;

public class Lobby extends Thread implements JsonSerializable {

	// zmienne
	public int id;
	public String lobby_name;
	public String admin_name;
	public String map_name;
	public String map_context;
	public int game_mode;
	public int game_time;
	public int max_players;
	public boolean prev_status;
	public boolean is_active;
	private long start;
	private int time;
	private ClientThread admin;
	ArrayList<ClientThread> clients;
	ArrayList<ClientThread> team1;
	ArrayList<ClientThread> team2;

	// stale
	public final static int MAX_COLORS = 8;
	public final static int TEAM1 = 0;
	public final static int TEAM2 = 1;

	public Lobby() {
		this.id = -1;
		this.lobby_name = "default";
		this.admin_name = "default";
		this.map_name = "default";
		this.max_players = -1;
		this.map_context = "default";
		this.is_active = true;
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

		if (clients.size() == 0) {
			// puste lobby, usuwamy
			is_active = false;
			Server.lobbies.remove(id);
		}
		else {
			// niepuste lobby, sprawdzamy admina i status
			checkAdmin();
			checkStatus();
		}
	}

	public synchronized void start() {
		for (ClientThread c : clients)
			c.writeInt(LOBBY_START);
		super.start();
	}

	public synchronized void init() {
		for(ClientThread c: clients)
			c.writeInt(LOBBY_INIT);
	}

	public void end() {
		Collections.sort(clients);
		JsonObject obj = clientsToJson();
		for (ClientThread c: clients) {
			c.writeInt(MULTI_END);
			c.writeUTF(obj.toString());
		}
	}

	public synchronized void teams() {
		int color = 0;
		int team = 0;
		for(ClientThread c1: clients) {
			for(ClientThread c2: clients) {
				c2.writeInt(LOBBY_PLAYER);
				c2.writeInt(c1.id);
				c2.writeInt(color % MAX_COLORS);
				c2.writeInt(team);

			}
			c1.team = team;
			c1.color = color;
			color = nextColor(color);
			team = nextTeam(team);
		}
	}

	public void stats() {
		for(ClientThread c: clients) {
			System.out.printf("CLI[%d] %d %d\n", c.id, c.deaths, c.kills);
		}
	}

	public synchronized int nextTeam(int team) {
		return switch (game_mode) {
			case DEATHMATCH -> team+1;
			case TEAM, COOPERATION -> (team + 1) % 2;
			default -> 0;
		};
	}

	public synchronized int nextColor(int color) {
		return switch (game_mode) {
			case DEATHMATCH, COOPERATION -> color + 1;
			case TEAM  -> (color + 1) % 2;
			default -> 0;
		};
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
				c.kills++;
				client.deaths++;
				break;
			}
		}
	}

	public synchronized void message(ClientThread client, String message) {
		for(ClientThread c: clients) {
			c.writeInt(MULTI_MESSAGE);
			c.writeInt(client.id);
			c.writeUTF(message);
		}
	}

	public void run() {
		start = System.currentTimeMillis();
		while (is_active) {
			long end = System.currentTimeMillis();
			time = (int)(end - start)/1000;

			if (time >= game_time) {
				end();
				return;
			}

			for(ClientThread c: clients) {
				c.writeInt(MULTI_TIME);
				c.writeInt(game_time - time);
			}
			try {
				sleep(1000 - end % 1000);
			} catch (Exception e) {
				e.printStackTrace();
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
		obj.addProperty("game_mode", game_mode);
		obj.addProperty("game_time", game_time);
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
		game_mode = obj.get("game_mode").getAsInt();
		game_time = obj.get("game_time").getAsInt();

		this.prev_status = false;
		this.clients = new ArrayList<>();
		this.admin = null;
	}

	public JsonObject clientsToJson() {
		JsonObject obj = new JsonObject();
		obj.addProperty("game_mode", game_mode);
		obj.addProperty("total_players", clients.size());

		if (game_mode == DEATHMATCH) {
			// DEATH MATCH
			JsonArray array = new JsonArray();
			for(ClientThread c: clients) array.add(c.toJson());
			obj.addProperty("time", game_time);
			obj.addProperty("winner", clients.get(0).id);
			obj.add("clients", array);
		}
		else if (game_mode == TEAM) {
			// TEAM
			int team1_points = 0;
			int team2_points = 0;

			JsonArray team1 = new JsonArray();
			JsonArray team2 = new JsonArray();
			for(ClientThread c: clients) {
				if (c.team == TEAM1) {
					// team1 member
					team1.add(c.toJson());
					team1_points += c.getPoints();
				}
				else {
					// team2 member
					team2.add(c.toJson());
					team2_points += c.getPoints();
				}
			}
			obj.addProperty("time", game_time);
			obj.addProperty("winner", (team1_points > team2_points) ? TEAM1 : TEAM2);
			obj.add("team1", team1);
			obj.add("team2", team2);
		}
		else {
			// COOPERATION
			JsonArray team1 = new JsonArray();
			JsonArray team2 = new JsonArray();
			for(ClientThread c: clients) {
				if (c.team == TEAM1) {
					// team1 member
					team1.add(c.toJson());
				}
				else {
					// team2 member
					team2.add(c.toJson());
				}
			}
			obj.addProperty("time", game_time);
			obj.add("team1", team1);
			obj.add("team2", team2);
		}
		return obj;
	}

	public void clientsFromJson(JsonObject obj) {
		game_mode = obj.get("game_mode").getAsInt();

		if (game_mode == DEATHMATCH) {
			// DEATH MATCH
			clients = new ArrayList<>();
			JsonArray array = obj.get("clients").getAsJsonArray();
			for(JsonElement o: array) clients.add(new ClientThread(o.getAsJsonObject()));
		}
		else if (game_mode == TEAM) {
			// TEAM
		}
		else {
			// COOPERATION
		}
	}

	public void setId(int id) {
		this.id = id;
	}
}
