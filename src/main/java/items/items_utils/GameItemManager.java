package items.items_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import items.items_tree.GameItem;
import static constants.ItemTypes.*;

/**
 * Przechowuje wszystkie obiekty mapy, udostepnia ujednolicony interfejs
 * dla wszystkich obiektow. Kazdy obiekt jest jednoznaczenie identyfikowany
 * przez identyfikator, dlatego wszystkie obiekty przechowywane sa w hashMapie.
 * Dodatkowo przechowuje referencje z podzialem na grupy
 *
 * LISTA GLOWNA
 * allItems    - zawiera wszystkie elementy
 *
 *  LISTY POMOCNICZE
 * staticItems - elementy nie poruszajace sie
 * movingItems - elementy poruszajace sie
 * ...
 */
public class GameItemManager {

	public List<GameItem> allItems;
	public List<GameItem> staticItems;
	public List<GameItem> movingItems;

	public GameItemManager() {
		staticItems = new ArrayList<>();
		movingItems = new ArrayList<>();
		allItems = new ArrayList<>();
	}

	/**
	 * Dodaje element do listy glownej oraz do odpowiednich list pomocniczych.
	 */
	public GameItemManager addItem(GameItem item) {
		allItems.add(item);
		// TODO dodac item do list pomocznichych

		return this;
	}

	/** Usuwa element ze z listy glownej oraz ze wszystkich list pomocniczych.
	 */
	public void removeItem(GameItem item) {
		allItems.remove(item);
		// TODO usunac element z list pomocniczych
	}

	/**
	 * Czysci listy pomocnicze oraz ponownie grupuje wszystkie elementy,
	 * nalezy wywolac po wczytaniu mapy z jsona.
	 */
	public void loadItems() {
		// TODO
	}
}
