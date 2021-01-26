package client;

import graphics.gui.GameplayContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static server.Protocol.*;

public class MultiReader extends Thread{

	private DataInputStream input;
	private DataOutputStream output;

	public MultiReader(DataInputStream input, DataOutputStream output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		int id, w, s, hp;
		float x, y;

		while (true) {
			try {
				int num = input.readInt();
				switch (num) {

					case MULTI_OTHER_POSITION:
						id = input.readInt();
						x = input.readFloat();
						y = input.readFloat();
						System.out.printf("Client [%d] MULTI_OTHER_POSITION %f %f\n", id, x, y);
						break;

					case MULTI_OTHER_WEAPON:
						id = input.readInt();
						w = input.readInt();
						GameplayContext.players.get(id).setActiveWeapon(w);
						System.out.printf("Client [%d] MULTI_OTHER_WEAPON %d\n", id, w);
						break;

					case MULTI_OTHER_STAGE:
						id = input.readInt();
						s = input.readInt();
						GameplayContext.players.get(id).setStage(s);
						System.out.printf("Client [%d] MULTI_OTHER_STAGE %d\n", id, s);
						break;

					case MULTI_OTHER_ATTACK:
						id = input.readInt();
						System.out.printf("Client [%d] MULTI_OTHER_ATTACK\n", id);
						break;

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
