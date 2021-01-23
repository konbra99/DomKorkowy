package client;

import java.io.DataInputStream;
import java.io.IOException;

import static server.Protocol.*;

public class LobbyReader extends Thread {

	private DataInputStream input;
	public LobbyReader(DataInputStream input) {
		this.input = input;
	}

	@Override
	public void run() {
		int id;
		boolean status;
		while (true) {
			try {
				int num = input.readInt();
				switch (num) {
					case LOBBY_OTHER_JOIN:
						id = input.readInt();
						System.out.println("Lobby [] LOBBY_OTHER_JOIN " + id);
						break;

					case LOBBY_OTHER_EXIT:
						id = input.readInt();
						System.out.println("Lobby [] LOBBY_OTHER_EXIT " + id);
						break;

					case LOBBY_OTHER_STATUS:
						id = input.readInt();
						status = input.readBoolean();
						System.out.println("Lobby [] LOBBY_OTHER_STATUS " + id + " " + status);
						break;

					case LOBBY_ADMIN:
						System.out.println("Lobby [] LOBBY_ADMIN ");
						break;

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
