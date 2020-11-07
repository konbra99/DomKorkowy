package map;

import java.util.ArrayList;
import java.util.List;

import items.items_tree.Item;

/**
 * Przechowuje wszystkie elementy danej sceny, udestepnia ujednolicony
 * interfejs do obslugi wszystkich elementow. Dodatkowo przechowuje
 * referencje do elementow z podzialem na grupy,
 *
 * LISTA GLOWNA
 * allItems    - wszystkie elementy
 *
 * LISTY POMOCNICZE
 * staticItems - elementy nie poruszajace sie
 * movingItems - elementy poruszajace sie
 * ...
 */
public class Stage {

	public List<Item> allItems;
	public List<Item> staticItems;
	public List<Item> movingItems;
	StageProperties properties;

	public Stage() {
		allItems = new ArrayList<>();
		staticItems = new ArrayList<>();
		movingItems = new ArrayList<>();
		properties = new StageProperties();
	}

	/**
	 * Dodaje element do listy glownej oraz do odpowiednich list pomocniczych.
	 */
	public Stage addItem(Item item) {
		allItems.add(item);
		// TODO dodac item do list pomoczniczych

		return this;
	}

	/**
	 * Usuwa element ze z listy glownej oraz ze wszystkich list pomocniczych.
	 */
	public void removeItem(Item item) {
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

	/**
	 * Ustawia wlasciwosci pojedynczej planszy.
	 */
	public void addProperties(StageProperties properties) {
		this.properties = properties;
	}
}
