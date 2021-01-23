package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket serverSocket;
	private static int clientCounter = 0;
	private static int lobbyCounter = 0;
	private final static int port = 7117;

	/*************** TYMCZASOWO *****************/
	public static Lobby[] lobbies = {
			new Lobby(lobbyCounter++, "aaaa", "aaaaa", "asdasd", 12),
			new Lobby(lobbyCounter++, "bbbb", "ccccc", "asdasd", 111),
			new Lobby(lobbyCounter++, "cccc", "ddddd", "asdasd", 999),
	};
	/********************************************/

	public Server() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.run();
	}
}
