package server;

import map.json.JsonUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

	private ServerSocket serverSocket;
	private static int clientCounter = 0;
	private static int lobbyCounter = 0;
	private final static int port = 7117;

	public static Map<Integer, Lobby> lobbies;

	public Server() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lobbies = new HashMap<>();
	}

	public void run() throws IOException{
		Socket socket;
		while (true) {
			System.out.println("Waiting for client...");
			socket = serverSocket.accept();
			new ClientThread(socket, clientCounter).start();
			System.out.println("New client " + clientCounter);
			clientCounter++;
		}
	}

	public synchronized static void addLobby(String lobbyStr) {
		Lobby lobby = new Lobby();
		lobby.fromJson(JsonUtils.fromString(lobbyStr));
		lobby.setId(lobbyCounter);
		lobbies.put(lobbyCounter++, lobby);
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.run();
	}
}
