package map;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import items.items_tree.GameItem;
import items.items_tree.TestRect;
import items.items_utils.GameItemManager;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapReader {

	private static Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();

	public void read(MapProperties mapProperties, GameItemManager itemManager, String filepath) {
		try {
			Reader reader = new FileReader(filepath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readMap(Reader reader, MapProperties properties) {
		properties = gson.fromJson(reader, MapProperties.class);
		System.out.println(properties);
	}

	public static void readItems(Reader reader, GameItemManager itemManager) {

		ItemDeserializer deserializer = new ItemDeserializer("type");
		deserializer.registerBarnType("GameItem", GameItem.class);
		deserializer.registerBarnType("TestRect", TestRect.class);
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(GameItem.class, deserializer)
				.create();

		itemManager.allItems = gson.fromJson(reader, new TypeToken<List<GameItem>>(){}.getType());
		itemManager.loadItems();
	}
}

class ItemDeserializer implements JsonDeserializer<GameItem> {
	private String typeElementName;
	private Gson gson;
	private Map<String, Class<? extends GameItem>> animalTypeRegistry;

	public ItemDeserializer(String animalTypeElementName) {
		this.typeElementName = animalTypeElementName;
		this.gson = new Gson();
		this.animalTypeRegistry = new HashMap<>();
	}

	public void registerBarnType(String animalTypeName, Class<? extends GameItem> animalType) {
		animalTypeRegistry.put(animalTypeName, animalType);
	}

	public GameItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		JsonObject animalObject = json.getAsJsonObject();
		JsonElement animalTypeElement = animalObject.get(typeElementName);

		Class<? extends GameItem> animalType = animalTypeRegistry.get(animalTypeElement.getAsString());
		return gson.fromJson(animalObject, animalType);
	}
}
