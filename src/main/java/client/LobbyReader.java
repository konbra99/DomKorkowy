package client;

import graphics.Engine;

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
		boolean s;
		while (true) {
			try {
				int num = input.readInt();
				switch (num) {
					case LOBBY_OTHER_JOIN:
						id = input.readInt();
						System.out.println("Lobby [] LOBBY_OTHER_JOIN " + id);
						Engine.browser.browser.join(id);
						break;

					case LOBBY_OTHER_EXIT:
						id = input.readInt();
						Engine.browser.browser.exit(id);
						if (id == Engine.client.id) {
							// wyjscie z lobby
							System.out.println("Lobby END []");
							return;
						}
						else {
							// wyjscie innego gracza
							System.out.println("Lobby [] LOBBY_OTHER_EXIT " + id);
						}
						break;

					case LOBBY_OTHER_STATUS:
						id = input.readInt();
						s = input.readBoolean();
						Engine.browser.browser.status(id, s);
						System.out.println("Lobby [] LOBBY_OTHER_STATUS " + id + " " + s);
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
