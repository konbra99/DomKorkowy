package entity.entities_utils;

import static constants.ItemNames.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import entity.entities_tree.Entity;
import entity.entities_tree.*;

/**
 * Zwraca obiekt, ktorego typ podano w formie stringa, poniewaz
 * kazdy obiekt jest jednoznacznie reprezentowany przez id, jest
 * to jeden z parametrow wywolania.
 */
public class EntityFactory {

	private EntityFactory() {}

	public static Entity getFromName(String name, int id, float x, float y) {

		switch(name) {
			case TEST_RECT : return new TestRect(id, x, y);
			// TODO more

			default:
				System.err.println("GameItemFactory.getFromName(): Nierozpoznana nazwa obiektu");
				System.exit(-1);
		}

		return null;
	}

	public static Entity getFromJson(Gson gson, JsonElement element) {
		System.out.println(element);
		String name = element.getAsJsonObject().getAsJsonPrimitive("type").getAsString();

		switch(name) {
			case TEST_RECT : return (TestRect)gson.fromJson(element, TestRect.class);
			// TODO more

			default:
				System.err.println("GameItemFactory.getFromJson(): Nierozpoznana nazwa " + name);
				System.exit(-1);
		}

		return null;
	}
}
