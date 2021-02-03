package map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import graphics.Config;
import graphics.Engine;
import graphics.Rectangle;
import logic.Door;
import logic.Entity;
import logic.EntityFactory;
import map.json.JsonSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static logic.Entity.*;

public class Stage implements JsonSerializable {
	private int xIndex;
	private int yIndex;
	private Key key;
	private final Rectangle background;
	private String backgroundName;
	private float[] start;
	private final MapManager map;
	private int bgCounter;

	private final ArrayList<Entity> allMap;

	private final Map<Integer, Entity> all;

	private final Map<Integer, Entity> platforms;
	private final Map<Integer, Entity> mobs;

	private final Map<Integer, Entity> obstacles;
	private final Map<Integer, Entity> doors;
	//private Map<Integer, Entity> checkpoints;
	//private Map<Integer, Entity> collectibles;

	public Stage(MapManager map) {
		this.map = map;
		this.background = new Rectangle(-1.0f, -1.0f, 2.0f, 2.0f);
		this.bgCounter = 0;
		this.backgroundName = Config.backgrounds[bgCounter];
		this.background.initGL(backgroundName, "rectangle.vert.glsl", "rectangle.frag");

		this.allMap = new ArrayList<>();
		this.all = new HashMap<>();
		this.platforms = new HashMap<>();
		this.mobs = new HashMap<>();
		this.obstacles = new HashMap<>();
		this.doors = new HashMap<>();
		this.start = new float[2];

		start = new float[]{0.0f, 0.0f};
		//this.checkpoints = new HashMap<>();
		//this.collectibles = new HashMap<>();
	}

	public Stage(MapManager map, int x, int y) {
		this.map = map;
		this.background = new Rectangle(-1.0f, -1.0f, 2.0f, 2.0f);
		this.bgCounter = 0;
		this.backgroundName = Config.backgrounds[bgCounter];
		this.background.initGL(backgroundName, "rectangle.vert.glsl", "rectangle.frag");

		this.allMap = new ArrayList<>();
		this.all = new HashMap<>();
		this.platforms = new HashMap<>();
		this.mobs = new HashMap<>();
		this.obstacles = new HashMap<>();
		this.doors = new HashMap<>();

		start = new float[]{0.0f, 0.0f};
		//this.checkpoints = new HashMap<>();
		//this.collectibles = new HashMap<>();

		this.xIndex = MapManager.getCurrentStageKey().getX() + x;
		this.yIndex = MapManager.getCurrentStageKey().getX() + y;
		this.key = new Key(xIndex, yIndex);
	}

	public int getxIndex() {
		return xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public Rectangle getBackground() {
		return background;
	}

	public Map<Integer, Entity> getAll() {
		return all;
	}

	public Map<Integer, Entity> getObstacles() {
		return obstacles;
	}

	public Map<Integer, Entity> getPlatforms() {
		return platforms;
	}

	Entity getEntity(int key) {
		return all.get(key);
	}


	public Map<Integer, Entity> getMobs() {
		return mobs;
	}

	public Map<Integer, Entity> getDoors() {
		return doors;
	}

	public void incrementBG() {
		bgCounter++;
		this.background.setTexture(Config.backgrounds[bgCounter % 7]);
	}

	public void buildAll() {
		int i = 0;
		for (Entity entity : map.doorMap.values()) {
			Door door = (Door) entity;
			if (containsDoor(entity)) {
				entity.setId(door.doorNumber);
				all.put(door.doorNumber, entity);
				doors.put(door.doorNumber, entity);
			}

			i = Math.max(i, door.doorNumber);
		}
		i++;

		for (Entity entity : allMap) {
			entity.setId(i);
			all.put(i, entity);
			if (entity.isInGroup(GROUP_MOBS)) {
				mobs.put(i, entity);
			} else if (entity.isInGroup(GROUP_PLATFORMS)) {
				platforms.put(i, entity);
			} else if (entity.isInGroup(GROUP_OBSTACLES)) {
				obstacles.put(i, entity);
			}

			i++;
		}
	}

	public void initAll() {
		for (Entity e : all.values()) {
			e.init();
		}
	}

	public void initAllMap() {
		for (Entity e : allMap) {
			e.init();
		}

		for (Entity door : map.doorMap.values()) {
			if (containsDoor(door)) {
				door.init();
			}
		}
	}

	public Entity getMapEntity(float x, float y) {
		for (Entity door : map.doorMap.values()) {
			if (!containsDoor(door))
				continue;
			if (door.getRectangle().hasPoint(x, y))
				return door;
		}
		for (Entity entity : allMap) {
			if (entity.getRectangle().hasPoint(x, y))
				return entity;
		}
		return null;
	}

	public float[] getStart() {
		return start;
	}

	public void removeMapEntity(Entity entity) {
		if (entity.isInGroup(GROUP_DOORS)) {
			map.removeDoor(entity);
			return;
		}

		allMap.remove(entity);
	}

	public void removeEntity(int id) {
		all.remove(id);
	}

	public void addMapEntity(Entity entity) {
		if (entity.isInGroup(GROUP_DOORS)) {
			map.addDoor(entity);
			return;
		}

		allMap.add(entity);
	}

	public boolean containsDoor(Entity e) {
		Door door = (Door) e;
		return (new Key(door.stageX, door.stageY)).equals(key);
	}

	void drawStatic() {
		background.draw();
		for (Entity e : allMap) {
			e.draw();
		}

		float x, y;
		for (Entity e : map.doorMap.values()) {
			if (containsDoor(e)) {
				e.draw();
				x = e.getRectangle().posX;
				y = (e.getRectangle().posY + e.getRectangle().height) * Config.RESOLUTION;
				Engine.fontLoader.renderText(" " + ((Door) e).doorNumber, "msgothic.bmp", x, y,
						0.03f, 0.08f,
						1.0f, 1.0f, 1.0f, 1.0f);
			}
		}
	}

	void update() {
		for (Entity e : all.values()) {
			e.update();
		}
	}

	void move() {
		for (Entity e : all.values()) {
			e.draw();
		}
	}

	void draw() {
		background.draw();
		for (Entity e : all.values()) {
			e.draw();
		}
	}

	@Override
	public void fromJson(JsonObject obj) {
		System.out.println("from json stage");
		backgroundName = obj.get("backgroundName").getAsString();

		xIndex = obj.get("xIndex").getAsInt();
		yIndex = obj.get("yIndex").getAsInt();
		System.out.println("mam indeksy " + xIndex + " ," + yIndex);
		start[0] = obj.get("startX").getAsFloat();
		start[1] = obj.get("startY").getAsFloat();
		System.out.println("mam start " + start[0] + " ," + start[1]);
		this.key = new Key(xIndex, yIndex);
		JsonArray entities = obj.getAsJsonArray("entities");
		System.out.println("entities: ");

		for (JsonElement element : entities) {
			JsonObject temp = element.getAsJsonObject();
			String type = temp.get("type").getAsString();

			Entity entity = EntityFactory.fromName(type);
			entity.fromJson(temp);
			if (type.equals("Door")) {
				Door door = (Door) entity;
				map.doorMap.put(door.doorNumber, door);
				continue;
			}
			addMapEntity(entity);
		}
	}

	@Override
	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		JsonArray entities = new JsonArray();
		obj.addProperty("backgroundName", background.getTexture());
		obj.addProperty("xIndex", xIndex);
		obj.addProperty("yIndex", yIndex);
		obj.addProperty("startX", start[0]);
		obj.addProperty("startY", start[1]);
		JsonObject temp;

		for (Entity entity : allMap) {
			temp = entity.toJson();
			String type = entity.getClass().getSimpleName();
			temp.addProperty("type", type);

			entities.add(temp);
		}
		for (Entity door : map.doorMap.values()) {
			if (!containsDoor(door))
				continue;

			temp = door.toJson();
			String type = door.getClass().getSimpleName();
			temp.addProperty("type", type);

			entities.add(temp);
		}
		obj.add("entities", entities);
		return obj;
	}
}
