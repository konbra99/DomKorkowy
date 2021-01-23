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
	private int id;

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

	public void lobbyJoin(int lobbyId) {
		try {
			System.out.println("proba dolaczenia do pokoju");

			output.writeInt(LOBBY_MY_JOIN);
			output.writeInt(lobbyId);
			input.readInt();

			int status = input.readInt();
			if (status == LOBBY_NOT_EXIST) {
				System.out.println("lobby nie istnieje");
			}
			else if (status == LOBBY_IS_FULL) {
				System.out.println("lobby nie ma miejsc");
			}
			else if (status == LOBBY_JOINED) {
				System.out.println("dolaczono do lobby");
				new LobbyReader(input).start();
			}

		} catch (IOException ignored) {}
	}
}