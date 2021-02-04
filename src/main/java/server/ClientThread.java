package server;

import com.google.gson.JsonObject;
import database.MapsConnector;
import map.json.JsonSerializable;
import org.lwjgl.system.CallbackI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static server.Protocol.*;

public class ClientThread extends Thread implements JsonSerializable, Comparable {

	public int id;
	public boolean status;
	public int color;
	public int team;
	public int kills;
	public int deaths;
	private Lobby lobby;
	private final DataInputStream input;
	private final DataOutputStream output;

	public ClientThread(JsonObject obj) {
		fromJson(obj);
	}

	public ClientThread(Socket socket, int id) throws IOException {
		this.id = id;
		socket.setSoTimeout(10 * 1000);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {

		while (true) {
			try {
				int num = input.readInt();
				switch (num) {
					case GET_MAPS -> {
						System.out.printf("Client [%d] GET_MAPS\n", id);
						List<String> maps = MapsConnector.getMaps();
						output.writeInt(GET_MAPS);
						output.writeInt(maps.size());
						for (String map : maps)
							output.writeUTF(map);
					}
					case GET_ROOMS -> {
						System.out.printf("Client [%d] GET_ROOMS\n", id);
						output.writeInt(GET_ROOMS);
						output.writeInt(Server.lobbies.size());
						for (Lobby lobby : Server.lobbies.values())
							output.writeUTF(lobby.toJson().toString());
					}
					case CREATE_MAP -> {
						System.out.printf("Client [%d] CREATE_MAP\n", id);
						String src = input.readUTF();
						output.writeInt(CREATE_MAP);
						boolean status = MapsConnector.addMap(src);
						output.writeBoolean(status);
					}
					case GET_CLIENT_ID -> {
						System.out.printf("Client [%d] GET_CLIENT_ID\n", id);
						output.writeInt(GET_CLIENT_ID);
						output.writeInt(id);
					}
					case LOBBY_MY_JOIN -> {
						System.out.printf("Client [%d] LOBBY_MY_JOIN\n", id);
						int lobbyId = input.readInt();
						Lobby tempLobby = Server.lobbies.get(lobbyId);
						if (tempLobby == null) {
							// lobby nie istnieje
							output.writeInt(LOBBY_MY_JOIN);
							output.writeInt(LOBBY_NOT_EXIST);
						} else {
							// lobby istnieje
							tempLobby.join(this);
						}
					}
					case LOBBY_MY_EXIT -> {
						System.out.printf("Client [%d] LOBBY_MY_EXIT\n", id);
						lobby.exit(this);
						lobby = null;
					}
					case LOBBY_MY_STATUS -> {
						System.out.printf("Client [%d] LOBBY_MY_STATUS\n", id);
						boolean tempStatus = input.readBoolean();
						lobby.status(this, tempStatus);
					}
					case LOBBY_CREATE -> {
						System.out.printf("Client [%d] LOBBY_CREATE\n", id);
						String lobbyStr = input.readUTF();
						Server.addLobby(lobbyStr);
					}
					case LOBBY_START -> {
						System.out.printf("Client [%d] LOBBY_START\n", id);
						lobby.init();
						lobby.teams();
						lobby.start();
					}
					case MULTI_MY_POSITION -> {
						float x = input.readFloat();
						float y = input.readFloat();
						System.out.printf("Client [%d] MULTI_MY_POSITION %f %f\n", id, x, y);
						lobby.updatePosition(this, x, y);
					}
					case MULTI_MY_STAGE -> {
						int stageX = input.readInt();
						int stageY = input.readInt();
						System.out.printf("Client [%d %d] MULTI_MY_STAGE %d\n", id, stageX, stageY);
						lobby.updateStage(this, stageX, stageY);
					}
					case MULTI_MY_WEAPON -> {
						int weapon = input.readInt();
						System.out.printf("Client [%d] MULTI_MY_WEAPON %d\n", id, weapon);
						lobby.updateWeapon(this, weapon);
					}
					case MULTI_MY_ATTACK -> {
						int tempId = input.readInt();
						System.out.printf("Client [%d] MULTI_MY_ATTACK %d\n", id, tempId);
						lobby.updateAttack(this, tempId);
					}
					case MULTI_MY_REMOVE -> {
						int stageId = input.readInt();
						int entityId = input.readInt();
						lobby.removeEntity(this, stageId, entityId);
						System.out.printf("Client [%d] MULTI_MY_REMOVE %d %d\n", id, stageId, entityId);
					}
					case MULTI_MY_DIRECTION -> {
						int direction = input.readInt();
						lobby.updateDirection(this, direction);
						System.out.printf("Client [%d] MULTI_MY_DIRECTION %d \n", id, direction);
					}
					case MULTI_MY_HIT -> {
						lobby.updateHit(this);
						System.out.printf("Client [%d] MULTI_MY_HIT\n", id);
					}
					case MULTI_MY_DEATH -> {
						int enemyId = input.readInt();
						lobby.updateDeath(this, enemyId);
						System.out.printf("Client [%d] MULTI_MY_HIT %d\n", id, enemyId);
					}
					case MULTI_MESSAGE -> {
						String message = input.readUTF();
						System.out.printf("Client [%d] MULTI_MY_HIT %s\n", id, message);
						lobby.message(this, message);
					}
					case PING -> System.out.printf("Client [%d] PING\n", id);
					default -> System.out.println("ClientThread: nierozpoznany komunikat");
				}
			} catch (SocketTimeoutException e) {
				// czas minal, sprawdzamy czy klient jeszcze zyje
				if (lobby != null) writeInt(PING);
			} catch (IOException e) {
				// klient nie zyje, trzeba go usunac
				if (lobby != null)
					lobby.exit(this);
				return;
			}
		}
	}

	public void writeInt(int v) {
		try {
			output.writeInt(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeFloat(float v) {
		try {
			output.writeFloat(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeBoolean(boolean v) {
		try {
			output.writeBoolean(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeUTF(String v) {
		try {
			output.writeUTF(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	public int getPoints() {
		return kills - deaths;
	}

	@Override
	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", id);
		obj.addProperty("kills", kills);
		obj.addProperty("deaths", deaths);
		obj.addProperty("team", team);
		return obj;
	}

	@Override
	public void fromJson(JsonObject obj) {
		id = obj.get("id").getAsInt();
		kills = obj.get("kills").getAsInt();
		deaths = obj.get("deaths").getAsInt();
		team = obj.get("team").getAsInt();
	}

	@Override
	public int compareTo(Object c) {
		return this.getPoints() - ((ClientThread)c).getPoints();
	}
}
