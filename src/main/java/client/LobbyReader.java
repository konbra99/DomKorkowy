package client;

import graphics.Engine;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static graphics.gui_enums.MenuButtonNames.*;
import static server.Protocol.*;

public class LobbyReader extends Thread {

	private DataInputStream input;
	private DataOutputStream output;

	public LobbyReader(DataInputStream input, DataOutputStream output) {
		this.input = input;
		this.output = output;
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
						Engine.browser.buttonMap.get(START).setVisible(true);
						break;

					case LOBBY_READY:
						s = input.readBoolean();
						System.out.println("Lobby [] LOBBY_READY " + s);
						Engine.browser.buttonMap.get(START).setActive(s);
						break;

					case LOBBY_START:
						System.out.println("Lobby [] LOBBY_START ");
						Engine.activeContext = Engine.gameplay;
						Engine.STATE = Engine.GAME_STATE.GAMEPLAY;
						Engine.gameplay.refresh = true;
						return;

					case PING:
						System.out.println("Lobby [] PING ");
						output.writeInt(PING);
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
