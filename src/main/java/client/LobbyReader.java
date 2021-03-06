package client;

import graphics.Engine;
import graphics.gui.GameplayContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static graphics.gui_enums.MenuButtonNames.*;
import static server.Protocol.*;

public class LobbyReader extends Thread {

	private final DataInputStream input;
	private final DataOutputStream output;

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
					case LOBBY_OTHER_JOIN -> {
						id = input.readInt();
						System.out.println("Lobby [] LOBBY_OTHER_JOIN " + id);
						Engine.browser.browser.join(id);
					}
					case LOBBY_OTHER_EXIT -> {
						id = input.readInt();
						Engine.browser.browser.exit(id);
						if (id == Engine.client.id) {
							// wyjscie z lobby
							System.out.println("Lobby END []");
							return;
						} else {
							// wyjscie innego gracza
							System.out.println("Lobby [] LOBBY_OTHER_EXIT " + id);
						}
					}
					case LOBBY_OTHER_STATUS -> {
						id = input.readInt();
						s = input.readBoolean();
						Engine.browser.browser.status(id, s);
						System.out.println("Lobby [] LOBBY_OTHER_STATUS " + id + " " + s);
					}
					case LOBBY_ADMIN -> {
						System.out.println("Lobby [] LOBBY_ADMIN ");
						Engine.browser.buttonMap.get(START).setVisible(true);
					}
					case LOBBY_READY -> {
						s = input.readBoolean();
						System.out.println("Lobby [] LOBBY_READY " + s);
						Engine.browser.buttonMap.get(START).setActive(s);
					}
					case LOBBY_INIT -> {
						System.out.println("Lobby [] LOBBY_INIT ");
						Engine.addAction(GameplayContext::initGame);
					}

					case LOBBY_PLAYER -> {
						int tempId = input.readInt();
						int tempColor = input.readInt();
						int tempTeam = input.readInt();
						System.out.printf("Lobby [] LOBBY_PLAYER %d %d %d \n", tempId, tempColor, tempTeam);
						Engine.addAction(()->GameplayContext.addPlayer(tempId, tempColor, tempTeam));
					}
					case LOBBY_START -> {
						System.out.println("Lobby [] LOBBY_START ");
						Engine.addAction(GameplayContext::startGame);
						Engine.addAction(()->Engine.client.spawnMultiReader());
						return;
					}
					case PING -> {
						System.out.println("Lobby [] PING ");
						output.writeInt(PING);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
