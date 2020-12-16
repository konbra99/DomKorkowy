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

	/*************** TYMCZASOWO *****************/
	Room[] rooms = {
			new Room(0, "aaaa", "aaaaa", "asdasd", 12),
			new Room(1, "bbbb", "ccccc", "asdasd", 111),
			new Room(2, "cccc", "ddddd", "asdasd", 999),
	};
	/********************************************/

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

				case GET_ROOMS:
					System.out.println("Client [] GET_ROOMS");
					output.writeInt(SET_ROOMS);
					output.writeInt(rooms.length);
					for(Room room: rooms)
						output.writeUTF(room.toJson().toString());
					break;

				case CREATE_MAP:
					System.out.println("Client [] CREATE_MAP");
					String src = input.readUTF();
					output.writeInt(CREATE_MAP);
					boolean status = MapsConnector.addMap(src);
					output.writeBoolean(status);
					break;
			}
		}
	}

	public static void main(String[] args) throws IOException {

		Server server = new Server();
		server.run();
	}
}
