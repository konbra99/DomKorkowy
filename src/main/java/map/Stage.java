package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import graphics.Config;
import graphics.Rectangle;
import logic.Entity;
import logic.EntityFactory;
import map.json.JsonSerializable;
import static logic.Entity.*;

/**
 * Przechowuje wszystkie elementy danej sceny, udestepnia ujednolicony
 * interfejs do obslugi wszystkich elementow. Dodatkowo przechowuje
 * referencje do elementow z podzialem na grupy,
 */
public class Stage implements JsonSerializable {

	// zmienne
	public int backgroundId;
	private Rectangle background;
	private String backgroundName;
	public float[] start;

	// mapy
	public Map<Integer, Entity> all;
	public Map<Integer, Entity> platforms;
	public Map<Integer, Entity> mobs;
	public Map<Integer, Entity> obstacles;
	public Map<Integer, Entity> doors;

	// listy
	public List<Entity> allMap;

	public Stage() {
		this(null, 0.0f, 0.0f);
	}

	public Stage(String backgroundName, float startX, float startY) {
		all = new HashMap<>();
		platforms = new HashMap<>();
		mobs = new HashMap<>();
		obstacles = new HashMap<>();
		doors = new HashMap<>();
		allMap = new ArrayList<>();

		start = new float[2];
		this.backgroundName = backgroundName;
		this.start[0] = startX;
		this.start[1] = startY;
	}

	public void move() {
		for(Entity entity: all.values())
			entity.move();
	}

	public void update() {
		for(Entity entity: all.values())
			entity.update();
	}

	public void draw() {
		background.draw();
		for(Entity entity: all.values())
			entity.draw();
	}

	/** Dodaje element do listy. W trybie edycji elementy nie posiadaja id */
	public void addMapEntity(Entity entity) {
		allMap.add(entity);
	}

	/** Dodaje element do mapy, kluczem jest podane id. */
	public void addEntity(int id, Entity entity) {
		all.put(id, entity);
	}

	/** Usuwa element o podanym id ze wszystkich slownikow.
	 * Zwraca true, jesli element o podanym id istnieje. */
	public boolean removeEntity(int id) {
		Entity entity = all.get(id);
		if (entity == null)
			return false;
		if (entity.isInGroup(GROUP_PLATFORMS))
			platforms.remove(id);
		if (entity.isInGroup(GROUP_MOBS))
			mobs.remove(id);
		if (entity.isInGroup(GROUP_OBSTACLES))
			obstacles.remove(id);
		if (entity.isInGroup(GROUP_DOORS))
			doors.remove(id);
		all.remove(id);
		return true;
	}

	/** Grupuje elementy z all do poszczegolnych map pomocnicznych.
	 * Ustawia tlo, ktoremu odpowiada backgroundId. Nalezy wywolac
	 * po zaladowaniu mapy z jsona. */
	public void buildStage() {
		for (Map.Entry<Integer,Entity> element : all.entrySet()) {

			int id = element.getKey();
			Entity entity = element.getValue();

			all.put(id,entity);
			if (entity.isInGroup(GROUP_PLATFORMS))
				platforms.put(id, entity);
			if (entity.isInGroup(GROUP_MOBS))
				mobs.put(id, entity);
			if (entity.isInGroup(GROUP_OBSTACLES))
				obstacles.put(id, entity);
			if (entity.isInGroup(GROUP_DOORS))
				doors.put(id, entity);
				
		}

		background = new Rectangle(-1.0f, -1.0f, 2.0f, 2.0f);
		//background.X_WRAP = true;
		background.initGL(backgroundName, "rectangle.vert.glsl", "rectangle.frag");
	}

	public void initStage() {
		for(Entity entity: all.values())
			entity.init();
	}

	/** Zamienia ArrayList na HashMap, dodaje indeksy do elementow mapy.
	 * Nalezy wywolac po utworzeniu mapy w trybie edycji. */
	public void buildHashMap() {
		int counter = 0;
		for (Entity entity: allMap) {
			all.put(counter, entity);
			counter++;
		}
		allMap = null;
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
	}

	public void fromJson(JsonObject obj) {
		backgroundId = obj.get("backgroundId").getAsInt();
		JsonArray entities = obj.getAsJsonArray("entities");

		for(JsonElement element : entities) {
			JsonObject temp = element.getAsJsonObject();
			int id = temp.get("id").getAsInt();
			String type = temp.get("type").getAsString();

			Entity entity = EntityFactory.fromName(type);
			entity.fromJson(temp);
			addEntity(id, entity);
		}
	}
}
