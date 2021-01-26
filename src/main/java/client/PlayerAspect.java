package client;

import graphics.Engine;
import logic.Player;

public class PlayerAspect {
	float posX;
	float posY;
	int direction;
	int activeWeapon;
	Player player;

	public PlayerAspect(Player player) {
		this.posX = -100f;
		this.posY = -100f;
		this.activeWeapon = -100;
		this.direction = -100;
		this.player = player;
	}

	public void positionChange() {
		float newPosX = player.getRectangle().posX;
		float newPosY = player.getRectangle().posY;
		if (posX != newPosX || posY != newPosY) {
			Engine.client.updatePosition(newPosX, newPosY);
		}
		posX = newPosX;
		posY = newPosY;
	}

	public void directionChange() {
		int newDirection = player.getDirection();
		if (direction != newDirection) {
			System.out.println("Direction change");
			Engine.client.updateDirection(newDirection);
			direction = newDirection;
		}
	}

	public void stageChange() {
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
