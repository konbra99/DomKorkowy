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
 */
public class Stage implements JsonSerializable {

	// zmienne
	public int backgroundId;

	// mapy
	public Map<Integer, Entity> all;
	public Map<Integer, Entity> platforms;
	public Map<Integer, Entity> enemies;

	// listy
	public List<Entity> allMap;

	public Stage() {
		all = new HashMap<>();
		allMap = new ArrayList<>();
	}

	/** Dodaje element do listy. W trybie edycji elementy nie posiadaja id */
	public void addMapEntity(Entity entity) {
		allMap.add(entity);
	}

	/** Dodaje element do mapy, kluczem jest podane id. */
	public void addEntity(int id, Entity entity) {
		all.put(id, entity);
	}

	/** Usuwa element ze z listy glownej oraz ze wszystkich list pomocniczych. */
	public void removeEntity(Entity entity) {
	}

	/** Grupuje elementy z all to poszczegolnych list pomocnicznych.
	 * Nalezy wywolac po zaladowaniu mapy z jsona. */
	public void buildStage() {
	}

	/** Zamienia ArrayList na HashMap, dodaje indeksy do elementow mapy.
	 * Nalezy wywolac po utworzeniu mapy w trybie edycji. */
	public void buildHashMap() {

	}

	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		JsonArray entities = new JsonArray();
		obj.addProperty("backgroundId", backgroundId);

		for (Map.Entry<Integer,Entity> element : all.entrySet()) {
			JsonObject temp = element.getValue().toJson();
			int id = element.getKey();
			String type = element.getValue().getClass().getSimpleName();
			temp.addProperty("id", id);
			temp.addProperty("type", type);

			entities.add(temp);
		}
		obj.add("entities", entities);
		return obj;
	};

	public int fromJson(JsonObject obj) {
		try {
			backgroundId = obj.get("backgroundId").getAsInt();
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
				addEntity(id, entity);
			}
			return STAGE_OK;

		} catch (NullPointerException e) {
			return NONEXISTENT_PROPERTY;
		} catch (UnsupportedOperationException e) {
			return INVALID_PROPERTY;
		}
	}
}
