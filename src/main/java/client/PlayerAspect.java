package client;

import graphics.Engine;
import logic.Player;

public class PlayerAspect {
	float posX;
	float posY;
	Player player;

	public PlayerAspect(Player player) {
		this.posX = -100f;
		this.posY = -100f;
		this.player = player;
	}

	public void afterMove() {
		float new_posX = player.getRectangle().posX;
		float new_posY = player.getRectangle().posY;
		if (posX != new_posX || posY != new_posY) {
			//System.out.println("Position has changed!");
			Engine.client.updatePosition(new_posX, new_posY);
		}
		posX = new_posX;
		posY = new_posY;
	}

	public void afterStage() {
		System.out.println("Stage has changes");
		Engine.client.updateStage(player.getStage());
	}
}
