package server;

import database.MapsConnector;
import org.lwjgl.system.CallbackI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

import static server.Protocol.*;
import static server.Protocol.CREATE_MAP;

public class ClientThread extends Thread{

	public int id;
	public boolean status;
	private Lobby lobby;
	private DataInputStream input;
	private DataOutputStream output;

	public ClientThread(Socket socket, int id) throws IOException {
		this.id = id;
		socket.setSoTimeout(10*1000);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {

		while (true) {
			try {
				int num = input.readInt();
				switch (num) {

					case GET_MAPS:
						System.out.printf("Client [%d] GET_MAPS\n", id);
						List<String> maps = MapsConnector.getMaps();
						output.writeInt(GET_MAPS);
						output.writeInt(maps.size());
						for (String map : maps)
							output.writeUTF(map);
						break;

					case GET_ROOMS:
						System.out.printf("Client [%d] GET_ROOMS\n", id);
						output.writeInt(GET_ROOMS);
						output.writeInt(Server.lobbies.size());
						for (Lobby lobby : Server.lobbies.values())
							output.writeUTF(lobby.toJson().toString());
						break;

					case CREATE_MAP:
						System.out.printf("Client [%d] CREATE_MAP\n", id);
						String src = input.readUTF();
						output.writeInt(CREATE_MAP);
						boolean status = MapsConnector.addMap(src);
						output.writeBoolean(status);
						break;

					case GET_CLIENT_ID:
						System.out.printf("Client [%d] GET_CLIENT_ID\n", id);
						output.writeInt(GET_CLIENT_ID);
						output.writeInt(id);
						break;

					case LOBBY_MY_JOIN:
						System.out.printf("Client [%d] LOBBY_MY_JOIN\n", id);
						int lobbyId = input.readInt();
						Lobby tempLobby = Server.lobbies.get(lobbyId);
						if (tempLobby == null) {
							// lobby nie istnieje
							output.writeInt(LOBBY_MY_JOIN);
							output.writeInt(LOBBY_NOT_EXIST);
						}
						else {
							// lobby istnieje
							tempLobby.join(this);
						}
						break;

					case LOBBY_MY_EXIT:
						System.out.printf("Client [%d] LOBBY_MY_EXIT\n", id);
						lobby.exit(this);
						lobby = null;
						break;

					case LOBBY_MY_STATUS:
						System.out.printf("Client [%d] LOBBY_MY_STATUS\n", id);
						boolean tempStatus = input.readBoolean();
						lobby.status(this, tempStatus);
						break;

					case LOBBY_CREATE:
						System.out.printf("Client [%d] LOBBY_CREATE\n", id);
						String lobbyStr = input.readUTF();
						Server.addLobby(lobbyStr);
						break;

					case LOBBY_START:
						System.out.printf("Client [%d] LOBBY_START\n", id);
						lobby.start();
						break;

					case MULTI_MY_POSITION:
						float x = input.readFloat();
						float y = input.readFloat();
						System.out.printf("Client [%d] MULTI_MY_POSITION %f %f\n", id, x, y);
						lobby.updatePosition(this, x, y);
						break;

					case MULTI_MY_STAGE:
						int stage = input.readInt();
						System.out.printf("Client [%d] MULTI_MY_STAGE %d\n", id, stage);
						lobby.updateStage(this, stage);
						break;

					case MULTI_MY_WEAPON:
						int weapon = input.readInt();
						System.out.printf("Client [%d] MULTI_MY_WEAPON %d\n", id, weapon);
						lobby.updateWeapon(this, weapon);
						break;

					case MULTI_MY_ATTACK:
						int tempId = input.readInt();
						System.out.printf("Client [%d] MULTI_MY_ATTACK %d\n", id, tempId);
						lobby.updateAttack(this, id);
						break;

					case MULTI_MY_REMOVE:
						int stageId = input.readInt();
						int entityId = input.readInt();
						lobby.removeEntity(this, stageId, entityId);
						System.out.printf("Client [%d] MULTI_MY_REMOVE %d %d\n", id, stageId, entityId);
						break;

					case PING:
						System.out.printf("Client [%d] PING\n", id);
						break;

					default:
						System.out.println("ClientThread: nierozpoznany komunikat");

				}
			}
			catch (SocketTimeoutException e) {
				// czas minal, sprawdzamy czy klient jeszcze zyje
				if (lobby != null) writeInt(PING);
			}
			catch (IOException e) {
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

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
}
