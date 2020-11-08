package map;

import java.util.ArrayList;
import java.util.List;

import entity.entities_tree.Entity;

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

	public List<Entity> allEntities;
	public List<Entity> staticEntities;
	public List<Entity> movingEntities;
	StageProperties properties;

	public Stage() {
		allEntities = new ArrayList<>();
		staticEntities = new ArrayList<>();
		movingEntities = new ArrayList<>();
		properties = new StageProperties();
	}

	/**
	 * Dodaje element do listy glownej oraz do odpowiednich list pomocniczych.
	 */
	public Stage addEntity(Entity item) {
		allEntities.add(item);
		// TODO dodac item do list pomoczniczych

		return this;
	}

	/**
	 * Usuwa element ze z listy glownej oraz ze wszystkich list pomocniczych.
	 */
	public void removeEntity(Entity item) {
		allEntities.remove(item);
		// TODO usunac element z list pomocniczych
	}

	/**
	 * Czysci listy pomocnicze oraz ponownie grupuje wszystkie elementy,
	 * nalezy wywolac po wczytaniu mapy z jsona.
	 */
	public void loadEntity() {
		// TODO
	}

	/**
	 * Ustawia wlasciwosci pojedynczej planszy.
	 */
	public void addProperties(StageProperties properties) {
		this.properties = properties;
	}
}
