package items.items_utils;

import static constants.ItemNames.*;

import items.items_tree.GameItem;
import items.items_tree.*;

/**
 * Zwraca obiekt, ktorego typ podano w formie stringa, poniewaz
 * kazdy obiekt jest jednoznacznie reprezentowany przez id, jest
 * to jeden z parametrow wywolania.
 */
public class GameItemFactory {

	private GameItemFactory() {}

	public static GameItem get(String name, int id, float x, float y) {

		switch(name) {
			case TEST_RECT : return new TestRect(id, x, y);
			// TODO more GameItems

			default:
				System.err.println("GameItemFactory: Nierozpoznana nazwa obiektu");
				System.exit(-1);
		}

		return null;
	}
}
