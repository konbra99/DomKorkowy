package server;

import database.MapsConnector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static server.Protocol.*;
import static server.Protocol.CREATE_MAP;

public class ClientThread extends Thread{

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private int id;

	public ClientThread(Socket socket, int id) throws IOException {
		this.socket = socket;
		this.id = id;
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
						System.out.println("Client [] GET_MAPS");
						List<String> maps = MapsConnector.getMaps();
						output.writeInt(SET_MAPS);
						output.writeInt(maps.size());
						for (String map : maps)
							output.writeUTF(map);
						break;

					case GET_ROOMS:
						System.out.println("Client [] GET_ROOMS");
						output.writeInt(SET_ROOMS);
						output.writeInt(Server.lobbies.length);
						for (Lobby lobby : Server.lobbies)
							output.writeUTF(lobby.toJson().toString());
						break;

					case CREATE_MAP:
						System.out.println("Client [] CREATE_MAP");
						String src = input.readUTF();
						output.writeInt(CREATE_MAP);
						boolean status = MapsConnector.addMap(src);
						output.writeBoolean(status);
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
