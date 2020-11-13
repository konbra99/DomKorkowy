package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.entities_tree.Entity;
import entity.entities_utils.EntityFactory;
import map.json.JsonSerializable;

import static constants.JsonSerializationStatus.*;

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
public class Stage implements JsonSerializable {

	// zmienne
	public StageProperties properties;
	private int backgroundId;

	// listy
	public List<Entity> allEntities;
	public List<Entity> staticEntities;
	public List<Entity> movingEntities;

	// mapy
	public Map<Integer, Entity> all;

	public Stage() {
		all = new HashMap<>();

		// TODO delete
		allEntities = new ArrayList<>();
		staticEntities = new ArrayList<>();
		movingEntities = new ArrayList<>();
		properties = new StageProperties();
	}

	/** Dodaje element do listy glownej oraz do odpowiednich list pomocniczych. */
	// TODO delete
	public Stage addEntity(Entity entity) {
		allEntities.add(entity);
		return this;
	}

	/** Dodaje element do listy glownej oraz do odpowiednich list pomocniczych. */
	public void addEntity(int id, Entity entity) {
		all.put(id, entity);
	}

	/** Usuwa element ze z listy glownej oraz ze wszystkich list pomocniczych. */
	public void removeEntity(Entity entity) {
		allEntities.remove(entity);
	}

	/**
	 * Czysci listy pomocnicze oraz ponownie grupuje wszystkie elementy,
	 * nalezy wywolac po wczytaniu mapy z jsona.
	 */
	public void buildStage() {
	}

	/**
	 * Ustawia wlasciwosci pojedynczej planszy.
	 */
	public void addProperties(StageProperties properties) {
		this.properties = properties;
	}

	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		JsonArray entities = new JsonArray();
		obj.addProperty("backgroundId", backgroundId);

		for (Map.Entry<Integer,Entity> element : all.entrySet()) {
			JsonObject temp = element.getValue().toJson();
			temp.addProperty("id", element.getKey());
			entities.add(temp);
		}
		obj.add("entities", entities);
		return obj;
	};

	public int fromJson(JsonObject obj) {
		try {
			backgroundId = obj.get("backgroundIs").getAsInt();
			JsonArray entities = obj.getAsJsonArray("entities");

			for(JsonElement element : entities) {
				JsonObject temp = element.getAsJsonObject();
				int id = temp.get("id").getAsInt();
				String type = temp.get("type").getAsString();
				Entity entity = EntityFactory.fromName(type);

				if (entity == null)
					return NONEXISTENT_NAME;
				if (entity.fromJson(temp) != ENTITY_OK)
					return NONEXISTENT_PROPERTY;
			}
			return STAGE_OK;

		} catch (NullPointerException e) {
			return NONEXISTENT_PROPERTY;
		}
	}
}
