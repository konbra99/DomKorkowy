package logic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Tworzy obiekt, ktorego typ podano w formie stringa. Zwraca
 * nowo utworzony obiekt lub null, jesli obiekt o podanej nazwie
 * nie istnieje.
 */
public class EntityFactory {

	private EntityFactory() {}

	public static Entity fromName(String type) {

		switch(type) {
			case "TestRect" :   return new TestRect();
		}

		return null;
	}

	// TODO delete getFromJson
	public static Entity getFromJson(Gson gson, JsonElement element) {
		String name = element.getAsJsonObject().getAsJsonPrimitive("type").getAsString();

		switch(name) {
			case "TestRect" : return (TestRect)gson.fromJson(element, TestRect.class);
			// TODO more

			default:
				System.err.println("GameItemFactory.getFromJson(): Nierozpoznana nazwa " + name);
				System.exit(-1);
		}

		return null;
	}
}