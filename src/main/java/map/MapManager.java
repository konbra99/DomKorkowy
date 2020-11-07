package map;

import java.util.ArrayList;
import java.util.List;

/**
 * Przechowuje liste scen oraz numer aktualnej sceny. Mapa sklada sie
 * z co najmniej jednej sceny.
 */
public class MapManager {

	MapProperties properties;
	List<Stage> stages;
	int currentStage;

	public MapManager() {
		stages = new ArrayList<>();
		currentStage = 0;
	}

	/**
	 * Dodaje scene do mapy oraz grupuje elementy.
	 */
	public void addStage(Stage stage) {
		stages.add(stage);
		stage.loadItems();
	}

	/**
	 * Ustawia wlasciwosci mapy.
	 */
	public void addProperties(MapProperties properties) {
		this.properties = properties;
	}
}
