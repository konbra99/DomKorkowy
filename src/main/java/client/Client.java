package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static server.Protocol.*;

public class Client{

	private DataInputStream input;
	private DataOutputStream output;
	private boolean isConnected;
	public boolean isReady;
	public int id;

	public Client() {
		isConnected = false;
	}

	public boolean isConnected() {
		return this.isConnected;
	}

	public boolean isNotConnected() {
		return !this.isConnected;
	}

	public void connect() {
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(), 7117);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			isConnected = true;
			isReady = false;
			System.out.println("Client polaczony z serwerem");
			getId();
		} catch (IOException e) {
			isConnected = false;
			System.out.println("Nieudana proba polaczenia z serwerem");
		}
	}

	public List<String> getMaps() {
		List<String> maps = new ArrayList<>();
		try {
			output.writeInt(GET_MAPS);
			input.readInt();
			int num = input.readInt();
			for(int i = 0; i < num; i++)
				maps.add(input.readUTF());
		} catch (IOException ignored) {}

		return maps;
	}

	public List<String> getRooms() {
		List<String> rooms = new ArrayList<>();
		try {
			output.writeInt(GET_ROOMS);
			input.readInt();
			int num = input.readInt();
			for(int i = 0; i < num; i++)
				rooms.add(input.readUTF());
		} catch (IOException ignored) {}

		return rooms;
	}

	public boolean addMap(String map) {
		try {
			output.writeInt(CREATE_MAP);
			output.writeUTF(map);
			input.readInt();
			return input.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void getId() {
		try {
			output.writeInt(GET_CLIENT_ID);
			input.readInt();
			id = input.readInt();
		} catch (IOException ignored) {}
	}

	public int lobbyJoin(int lobbyId) {
		try {
			System.out.println("proba dolaczenia do pokoju");

			output.writeInt(LOBBY_MY_JOIN);
			output.writeInt(lobbyId);
			input.readInt();
			return input.readInt();

		} catch (IOException ignored) {}
		return -1;
	}

	public void lobbyExit() {
		try {
			output.writeInt(LOBBY_MY_EXIT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void lobbyStatus() {
		try {
			output.writeInt(LOBBY_MY_STATUS);
			output.writeBoolean(isReady);
		} catch( IOException e) {
			e.printStackTrace();
		}
	}

	public void lobbyCreate(String lobby) {
		try {
			output.writeInt(LOBBY_CREATE);
			output.writeUTF(lobby);
		} catch( IOException e) {
			e.printStackTrace();
		}
	}

	public void lobbyStart() {
		try {
			output.writeInt(LOBBY_START);
		} catch( IOException e) {
			e.printStackTrace();
		}
	}

	public void updatePosition(float x, float y) {
		try {
			output.writeInt(MULTI_MY_POSITION);
			output.writeFloat(x);
			output.writeFloat(y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateStage(int stage) {
		try {
			output.writeInt(MULTI_MY_STAGE);
			output.writeInt(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateWeapon(int weapon) {
		try {
			output.writeInt(MULTI_MY_WEAPON);
			output.writeInt(weapon);
		} catch (IOException ignored) {}
	}

	public void updateAttack(int id) {
		try {
			output.writeInt(MULTI_MY_ATTACK);
			output.writeInt(id);
		} catch (IOException ignored) {}
	}

	public void updateDirection(int direction) {
		try {
			output.writeInt(MULTI_MY_DIRECTION);
			output.writeInt(direction);
		} catch (IOException ignored) {}
	}

	public void removeEntity(int stage, int id) {
		try {
			output.writeInt(MULTI_MY_REMOVE);
			output.writeInt(stage);
			output.writeInt(id);
		} catch (IOException ignored) {}
	}

	public void spawnLobbyReader() {
		new LobbyReader(input, output).start();
	}

	public void spawnMultiReader() { new MultiReader(input, output).start(); }
}