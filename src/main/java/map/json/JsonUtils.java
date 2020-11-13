package map.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import static constants.JsonSerializationStatus.*;

public class JsonUtils {

	/** Parsuje str na obiekt klasy JsonObject. Zwraca nowo
	 * utorzony obiekt lub null w przypadku bledu. */
	public static JsonObject fromString(String str) {
		try {
			JsonParser parser = new JsonParser();
			JsonElement obj = parser.parse(str);
			return (JsonObject)obj;
		} catch(JsonSyntaxException e) {
			return null;
		}
	}

	/** Parsuje plik na obiekt klasy JsonObject. Zwraca nowo
	 * utworzony obiekt lub null w przypadku bledu. */
	public static JsonObject fromFile(String filepath) {
		try {
			Reader reader = new FileReader(filepath);
			JsonParser parser = new JsonParser();
			JsonElement obj = parser.parse(reader);
			return (JsonObject)obj;
		} catch (JsonSyntaxException e) {
			return null;
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/** Konwertuje obiekt klsay JsonObject na String. Zwraca
	 * nowo powstaly obiekt. */
	public static String toString(JsonObject obj) {
		return obj.toString();
	}

	/** Konwertuje obiekt klsay JsonObject do podanego pliku.
	 * Nie nadpisuje istniejacego pliku. Zwraca status bledu. */
	public static int toFile(JsonObject obj, String filepath) {
		return toFile(obj, filepath, false);
	}

	/** Konwertuje obiekt klsay JsonObject do podanego pliku.
	 * W zaleznosci od flagi force wymusza nadpisanie pliku.
	 * Zwraca status bledu. */
	public static int toFile(JsonObject obj, String filepath, boolean force) {
		try {
			File file = new File(filepath);
			if (!force && file.exists())
				return EXISTENT_FILE;

			Writer writer = new FileWriter(file);
			writer.write(obj.toString());
			writer.flush();
			writer.close();
			return FILE_OK;

		} catch (IOException e) {
			return IO_ERROR;
		}
	}
}
