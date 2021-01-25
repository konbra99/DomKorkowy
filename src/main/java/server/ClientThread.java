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
	public int lobbyId;
	public boolean status;
	private DataInputStream input;
	private DataOutputStream output;

	public ClientThread(Socket socket, int id) throws IOException {
		this.id = id;
		this.lobbyId = -1;
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
						int tempLobbyId = input.readInt();
						Lobby lobby = Server.lobbies.get(tempLobbyId);
						if (lobby == null) {
							// lobby nie istnieje
							output.writeInt(LOBBY_MY_JOIN);
							output.writeInt(LOBBY_NOT_EXIST);
						}
						else {
							// lobby istnieje
							lobby.join(this);
						}
						break;

					case LOBBY_MY_EXIT:
						System.out.printf("Client [%d] LOBBY_MY_EXIT\n", id);
						Server.lobbies.get(lobbyId).exit(this);
						lobbyId = -1;
						break;

					case LOBBY_MY_STATUS:
						System.out.printf("Client [%d] LOBBY_MY_STATUS\n", id);
						boolean tempStatus = input.readBoolean();
						Server.lobbies.get(lobbyId).status(this, tempStatus);
						break;

					case LOBBY_CREATE:
						System.out.printf("Client [%d] LOBBY_CREATE\n", id);
						String lobbyStr = input.readUTF();
						Server.addLobby(lobbyStr);
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
				if (lobbyId != -1) writeInt(PING);
			}
			catch (IOException e) {
				// klient nie zyje, trzeba go usunac
				if (lobbyId != -1)
					Server.lobbies.get(lobbyId).exit(this);
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

	public void writeBoolean(boolean v) {
		try {
			output.writeBoolean(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
