package map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import graphics.Engine;
import graphics.gui.GameplayContext;
import logic.Door;
import logic.Entity;
import map.json.JsonSerializable;

import java.util.HashMap;
import java.util.Map;

class Key {
	private final int x;
	private final int y;

	public Key(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Key)) {
			return false;
		}

		Key key = (Key) obj;
		return (x == key.x) && (y == key.y);
	}

	@Override
	public int hashCode() {
		return (x << 16) + y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}

public class MapManager implements JsonSerializable {
	// zmienne
	public String mapName = "default";
	public String author = "default";
	public String description = "default";
	public int time = 120;
	public float difficulty = 0.5f;
	public JsonObject mapObject;
	private final Map<Key, Stage> stages;
	protected final Map<Integer, Entity> doorMap;

	public static Key getCurrentStageKey() {
		return currentStageKey;
	}

	private static Key currentStageKey;

	public int getDoorCounter() {
		return doorCounter;
	}

	private int doorCounter;

	public MapManager() {
		stages = new HashMap<>();
		doorMap = new HashMap<>();
		doorCounter = 0;
		currentStageKey = new Key(0, 0);
	}

	public void addStage(int lr, int ud) {
		stages.put(new Key(lr, ud), new Stage(this, lr, ud));
	}

	public void addDirStage(int lr, int ud) {
		int x = currentStageKey.getX() + lr;
		int y = currentStageKey.getY() + ud;
		stages.put(new Key(x, y), new Stage(this, x, y));
	}

	public boolean hasStage(int lr, int ud) {
		return stages.containsKey(new Key(lr, ud));
	}

	public boolean hasDirStage(int lr, int ud) {
		int x = currentStageKey.getX() + lr;
		int y = currentStageKey.getY() + ud;
		return stages.containsKey(new Key(x, y));
	}

	public Stage getStage(int x, int y) {
		return stages.get(new Key(x, y));
	}

	public static int[] getCurrentStageTab() {
		return new int[]{currentStageKey.getX(), currentStageKey.getY()};
	}

	public void setStage(int x, int y) {
		currentStageKey = new Key(x, y);
		if (Engine.activeContext == Engine.gameplay) {
			GameplayContext.KORKOWY.setStage(x, y);
		}
	}

	public void setDirStage(int lr, int ud) {
		int x = currentStageKey.getX() + lr;
		int y = currentStageKey.getY() + ud;
		currentStageKey = new Key(x, y);
		System.out.println("ustawiam na " + x + ", " + y);
	}

	public Entity getEntity(int key) {
		return getCurrentStage().getEntity(key);
	}

	public Entity getDoor(int number) {
		for (Stage s : stages.values()) {
			if (s.getAll().containsKey(number)) {
				return s.getEntity(number);
			}
		}

		return null;
	}

	public void removeEntity(int id) {
		getCurrentStage().removeEntity(id);
	}

	public void removeEntity(int stageX, int stageY, int id) {
		stages.get(new Key(stageX, stageY)).removeEntity(id);
	}

	public Stage getCurrentStage() {
		return stages.get(currentStageKey);
	}

	public void addDoor(Entity entity) {
		Door door = (Door) entity;
		door.doorNumber = doorCounter;
		doorMap.put(doorCounter, door);
		doorCounter++;
	}

	public void removeDoor(Entity entity) {
		Door door = (Door) entity;
		doorMap.remove(door.doorNumber);
	}

	public boolean isNew() {
		return (stages.size() == 0);
	}

	public void drawStatic() {
		stages.get(currentStageKey).drawStatic();
	}

	public void update() {
		stages.get(currentStageKey).update();
	}

	public void move() {
		stages.get(currentStageKey).move();
	}

	public void draw() {
		stages.get(currentStageKey).draw();
	}

	public void buildStages() {
		for (Stage s : stages.values()) {
			s.buildAll();
			s.initAll();
		}
	}

	public void initMaps() {
		for (Stage s : stages.values()) {
			s.initAllMap();
		}
	}

	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		JsonArray json_stages = new JsonArray();
		obj.addProperty("mapName", mapName);
		obj.addProperty("author", author);
		obj.addProperty("time", time);
		obj.addProperty("description", description);
		obj.add("stages", json_stages);

		for (Stage stage : stages.values()) {
			JsonObject temp = stage.toJson();
			json_stages.add(temp);
		}
		return obj;
	}

	public void fromJson(JsonObject obj) {
		System.out.println("from json map");
		mapObject = obj;
		mapName = obj.get("mapName").getAsString();
		System.out.println("mam map name " + mapName);
		author = obj.get("author").getAsString();
		time = obj.get("time").getAsInt();
		if (obj.has("description"))
			description = obj.get("description").getAsString();

		stages.clear();
		System.out.println("pobieram stage");
		JsonArray json_stages = obj.getAsJsonArray("stages");
		for (JsonElement element : json_stages) {
			JsonObject temp = (JsonObject) element;
			Stage stage = new Stage(this);
			stage.fromJson(temp);
			stages.put(new Key(stage.getxIndex(), stage.getyIndex()), stage);
			System.out.println("dodany");
		}
		System.out.println("koniec");
	}
}


