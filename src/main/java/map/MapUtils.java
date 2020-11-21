package map;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import map.exceptions.NoexistentType;
import java.io.IOException;
import map.json.JsonUtils;

public class MapUtils {

	private final static boolean LOAD = true;
	private final static boolean SAVE = false;
	private final static boolean STRING = true;
	private final static boolean FILE = false;

	/** Wczytuje mape ze stringa */
	public static MapManager fromString(String str) {
		return (MapManager)mapManage(null, str, STRING, LOAD);
	}

	/** Zapisuje mape do Stringa */
	public static String toString(MapManager map) {
		return (String)mapManage(map, null, STRING, SAVE);
	}

	/** Wczytuje mape z pliku */
	public static MapManager fromFile(String str) {
		return (MapManager)mapManage(null, str, FILE, LOAD);
	}

	/** Zapisuje mape do pliku */
	public static void mapToFile(MapManager map, String filename) {
		mapManage(map, filename, FILE, SAVE);
	}

	private static Object mapManage(MapManager mapManager, String str, boolean isString, boolean isLoad) {
		try {
			if (isString && isLoad) {
				// LOAD FROM STRING
				MapManager map = new MapManager();
				JsonObject obj = JsonUtils.fromString(str);
				map.fromJson(obj);
				return map;
			}
			else if (isString && !isLoad) {
				// SAVE TO STRING
				JsonObject obj = mapManager.toJson();
				return JsonUtils.toString(obj);
			}
			else if (!isString && isLoad) {
				// LOAD FROM FILE
				MapManager map = new MapManager();
				JsonObject obj = JsonUtils.fromFile(str);
				map.fromJson(obj);
				return map;
			}
			else if (!isString && isLoad) {
				// SAVE TO FILE
				JsonObject obj = mapManager.toJson();
				JsonUtils.toFile(obj, str);
				return null;
			}
		}
		catch (NullPointerException e) {
			// brakujace pole
			System.err.println(e);
		}
		catch (UnsupportedOperationException e) {
			// pole nieprawidlowego typu
			System.err.println(e);
		}
		catch (NoexistentType e) {
			// nieistniejaca klasa
			System.err.println(e);
		}
		catch (JsonSyntaxException e) {
			// blad parsera jsona
			System.err.println(e);
		}
		catch (IOException e) {
			// problem z otwarciem pliku
			System.err.println(e);
		}

		return null;
	}




}
