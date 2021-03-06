package client;

import graphics.Engine;
import graphics.gui.GameplayContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static server.Protocol.*;

public class MultiReader extends Thread {

	private final DataInputStream input;
	private final DataOutputStream output;

	public MultiReader(DataInputStream input, DataOutputStream output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		int id, w, sX, sY, hp;
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
						Engine.addAction(() -> GameplayContext.players.get(tempId).moveTo(tempX, tempY));
					}
					case MULTI_OTHER_WEAPON -> {
						id = input.readInt();
						w = input.readInt();
						GameplayContext.players.get(id).setActiveWeapon(w);
						//System.out.printf("Client [%d] MULTI_OTHER_WEAPON %d\n", id, w);
					}
					case MULTI_OTHER_STAGE -> {
						id = input.readInt();
						sX = input.readInt();
						sY = input.readInt();
						GameplayContext.players.get(id).setStage(sX, sY);
						System.out.printf("Client [%d] MULTI_OTHER_STAGE %d %d\n", id, sX, sY);
					}
					case MULTI_OTHER_ATTACK -> {
						int tempId = input.readInt();
						//System.out.printf("Client [%d] MULTI_OTHER_ATTACK\n", id);
						Engine.addAction(() -> GameplayContext.KORKOWY.getDamage(tempId));
					}
					case MULTI_OTHER_DEATH -> {
						// System.out.printf("Client [] MULTI_OTHER_DEATH\n");
						Engine.addAction(() -> GameplayContext.KORKOWY.incKills());
					}
					case MULTI_OTHER_REMOVE -> {
						int stageX = input.readInt();
						int stageY = input.readInt();
						int entityId = input.readInt();
						//System.out.printf("Client [] MULTI_OTHER_REMOVE %d %d\n", stageId, entityId);
						Engine.addAction(() -> GameplayContext.map.removeEntity(stageX, stageY, entityId));
					}
					case MULTI_OTHER_DIRECTION -> {
						int tempId = input.readInt();
						int direction = input.readInt();
						//System.out.printf("Client [%d] MULTI_OTHER_DIRECTION %d\n", tempId, direction);
						Engine.addAction(() -> GameplayContext.players.get(tempId).setDirection(direction));
					}
					case MULTI_OTHER_HIT -> {
						int tempId = input.readInt();
						//System.out.printf("Client [%d] MULTI_OTHER_HIT\n", tempId);
						Engine.addAction(() -> GameplayContext.players.get(tempId).useWeapon());
					}
					case MULTI_MESSAGE -> {
						int tempId = input.readInt();
						String message = input.readUTF();
						System.out.printf("Client [%d] MULTI_MESSAGE %s\n", tempId, message);
						Engine.addAction(()->GameplayContext.addMessage(tempId, message));
					}
					case MULTI_TIME -> {
						int time = input.readInt();
						System.out.printf("Client [] MULTI_MESSAGE %d\n", time);
						Engine.addAction(()->GameplayContext.setTime(time));
					}
					case MULTI_END -> {
						String lobby = input.readUTF();
						System.out.printf("Client [] MULTI_MESSAGE\n");
						Engine.addAction(()->GameplayContext.endGame(lobby));
						return;
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
