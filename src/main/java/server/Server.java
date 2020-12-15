package server;

import database.MapsConnector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static server.Protocol.*;

public class Server {

	DataInputStream input;
	DataOutputStream output;

	public Server() throws IOException {
		ServerSocket serverSocket = new ServerSocket(7117);

		System.out.println("Waiting for client...");
		Socket socket = serverSocket.accept();
		System.out.println("Client connected");

		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}

	public void run() throws IOException{

		while (true) {
			int num = input.readInt();

			switch(num) {
				case GET_MAPS:
					System.out.println("Client [] GET_MAPS");
					List<String> maps = MapsConnector.getMaps();
					output.writeInt(SET_MAPS);
					output.writeInt(maps.size());
					for(String map: maps)
						output.writeUTF(map);
					break;
			}
		}
	}

	public static void main(String[] args) throws IOException {

		Server server = new Server();
		server.run();
	}
}
