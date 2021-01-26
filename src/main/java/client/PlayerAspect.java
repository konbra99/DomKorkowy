package client;

import graphics.Engine;
import logic.Player;

public class PlayerAspect {
	float posX;
	float posY;
	int activeWeapon;
	Player player;

	public PlayerAspect(Player player) {
		this.posX = -100f;
		this.posY = -100f;
		this.activeWeapon = -100;
		this.player = player;
	}

	public void positionChange() {
		float new_posX = player.getRectangle().posX;
		float new_posY = player.getRectangle().posY;
		if (posX != new_posX || posY != new_posY) {
			Engine.client.updatePosition(new_posX, new_posY);
		}
		posX = new_posX;
		posY = new_posY;
	}

	public void stageChange() {
		System.out.println("Stage has changed");
		Engine.client.updateStage(player.getStage());
	}

	public void weaponChange() {
		int newActiveWeapon = player.getActiveWeapon();
		if (activeWeapon != newActiveWeapon) {
			System.out.println("Weapon has changed");
			Engine.client.updateWeapon(newActiveWeapon);
			activeWeapon = newActiveWeapon;
		}
	}
}
