package client;

import graphics.Engine;
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
					case MULTI_OTHER_POSITION -> {
						int tempId = input.readInt();
						float tempX = input.readFloat();
						float tempY = input.readFloat();
						//System.out.printf("Client [%d] MULTI_OTHER_POSITION %f %f\n", tempId, tempX, tempY);
						GameplayContext.players.get(tempId).addAction(() -> GameplayContext.players.get(tempId).moveTo(tempX, tempY));
					}
					case MULTI_OTHER_WEAPON -> {
						id = input.readInt();
						w = input.readInt();
						GameplayContext.players.get(id).setActiveWeapon(w);
						//System.out.printf("Client [%d] MULTI_OTHER_WEAPON %d\n", id, w);
					}
					case MULTI_OTHER_STAGE -> {
						id = input.readInt();
						s = input.readInt();
						GameplayContext.players.get(id).setStage(s);
						//System.out.printf("Client [%d] MULTI_OTHER_STAGE %d\n", id, s);
					}
					case MULTI_OTHER_ATTACK -> {
						id = input.readInt();
						//System.out.printf("Client [%d] MULTI_OTHER_ATTACK\n", id);
					}
					case MULTI_OTHER_REMOVE -> {
						int stageId = input.readInt();
						int entityId = input.readInt();
						//System.out.printf("Client [] MULTI_OTHER_REMOVE %d %d\n", stageId, entityId);
						GameplayContext.KORKOWY.addAction(() -> GameplayContext.map.removeEntityFromStage(stageId, entityId));
					}

					case PING -> {
						//System.out.println("Lobby [] PING ");
						output.writeInt(PING);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
