package map.json;

import com.google.gson.*;

import java.io.*;

public class JsonUtils {

	/** Parsuje str na obiekt klasy JsonObject. */
	public static JsonObject fromString(String str){
		JsonParser parser = new JsonParser();
		JsonElement obj = parser.parse(str);
		return (JsonObject)obj;
	}

	/** Parsuje plik na obiekt klasy JsonObject. */
	public static JsonObject fromFile(String filepath) throws IOException {
		Reader reader = new FileReader(filepath);
		JsonParser parser = new JsonParser();
		JsonElement obj = parser.parse(reader);
		return (JsonObject)obj;
	}

	/** Konwertuje obiekt klsay JsonObject na String. */
	public static String toString(JsonObject obj) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(obj);
	}

	/** Konwertuje obiekt klsay JsonObject na ladnie wygladajacy String. */
	public static String toStringPretty(JsonObject obj) {
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		return gson.toJson(obj);
	}

	/** Konwertuje obiekt klsay JsonObject do pliku. */
	public static void toFile(JsonObject obj, String filepath) throws IOException {
		Writer writer = new FileWriter(filepath);
		Gson gson = new GsonBuilder().serializeNulls().create();
		writer.write(gson.toJson(obj));
		writer.flush();
		writer.close();
	}

	/** Konwertuje obiekt klsay JsonObject do ladnie wygladajacego pliku. */
	public static void toFilePretty(JsonObject obj, String filepath) throws IOException {
		Writer writer = new FileWriter(filepath);
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		writer.write(gson.toJson(obj));
		writer.flush();
		writer.close();
	}
}
