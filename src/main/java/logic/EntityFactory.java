package logic;

import logic.collectibles.HealthPotion;
import logic.collectibles.HealthPotionLarge;
import logic.collectibles.HealthPotionSmall;
import map.exceptions.NoexistentType;

/**
 * Tworzy obiekt, ktorego typ podano w formie stringa. Zwraca
 * nowo utworzony obiekt lub null, jesli obiekt o podanej nazwie
 * nie istnieje.
 */
public class EntityFactory {

	private EntityFactory() {
	}

	public static Entity fromName(String type) {

		return switch (type) {
			case "Character" -> new Character();
			case "Door" -> new Door();
			case "Mob" -> new Mob();
			case "Obstacle" -> new Obstacle();
			case "Platform" -> new Platform();
			case "StaticObject" -> new StaticObject();
			case "TestRect" -> new TestRect();
			case "WheelObstacle" -> new WheelObstacle();
			case "HealthPotionSmall" -> new HealthPotionSmall();
			case "HealthPotionLarge" -> new HealthPotionLarge();
			case "HealthPotion" -> new HealthPotion();
			case "Checkpoint" -> new Checkpoint();
			default -> throw new NoexistentType(type);
		};
	}
}
