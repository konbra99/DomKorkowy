package logic;

import map.exceptions.NoexistentType;

/**
 * Tworzy obiekt, ktorego typ podano w formie stringa. Zwraca
 * nowo utworzony obiekt lub null, jesli obiekt o podanej nazwie
 * nie istnieje.
 */
public class EntityFactory {

	private EntityFactory() {}

	public static Entity fromName(String type) {

		switch(type) {
			case "TestRect" : return new TestRect();
			default: throw new NoexistentType(type);
		}
	}
}