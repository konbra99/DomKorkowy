package map.json;

import com.google.gson.JsonObject;

public interface JsonSerializable {

	/**
	 * Zamienia element na obiekt klasy JsonObject.
	 * @return      JsonObject lub null w przypadku bledu.
	 */
	JsonObject toJson();

	/**
	 * Odtwarza element z podanego obiektu JsonObjet.
	 */
	void fromJson(JsonObject obj);
}
