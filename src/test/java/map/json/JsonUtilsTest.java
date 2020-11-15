package map.json;

import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.File;

import static constants.JsonSerializationStatus.*;
import static org.junit.Assert.*;

public class JsonUtilsTest {

	//@Test
	public void fromStringTest() {
		String str = """
				{"backgroundId":0,"entities":[]}""";
		JsonObject obj = JsonUtils.fromString(str);
		assertNotNull(obj);
	}

	//@Test
	public void fromBadStringTest() {
		String str = """
				{"backgroundId":0,"entities":[][}""";
		JsonObject obj = JsonUtils.fromString(str);
		assertNull(obj);
	}

	//@Test
	public void fromFileTest() {
		String filepath = "src/test/java/map/json/ok_file_test.json";
		JsonObject obj = JsonUtils.fromFile(filepath);
		assertNotNull(obj);
	}

	//@Test
	public void fromBadFileTest() {
		String filepath = "src/test/java/map/json/bad_file_test.json";
		JsonObject obj = JsonUtils.fromFile(filepath);
		assertNull(obj);
	}

	//@Test
	public void fromNotExistingFileTest() {
		String filepath = "src/test/java/map/json/existent_file_test.json";
		JsonObject obj = JsonUtils.fromFile(filepath);
		assertNull(obj);
	}

	//@Test
	public void toStringTest() {
		JsonObject obj = new JsonObject();
		obj.addProperty("bool", false);
		obj.addProperty("int", 10);
		obj.addProperty("float", 99.99f);
		obj.addProperty("string", "abc");
		String str = JsonUtils.toString(obj);
		System.out.println(str);
	}

	//@Test
	public void toNonexistentFileTest() {
		String filepath = "src/test/java/map/json/nonexistent_file_test.json";
		File file = new File(filepath);
		file.delete();
		JsonObject obj = new JsonObject();
		obj.addProperty("bool", false);
		obj.addProperty("int", 10);
		obj.addProperty("float", 99.99f);
		obj.addProperty("string", "abc");

		int status = JsonUtils.toFile(obj, filepath);
		assertEquals(status, FILE_OK);
	}

	//@Test
	public void toExistentFileTest() {
		String filepath = "src/test/java/map/json/existent_file_test.json";
		JsonObject obj = new JsonObject();
		obj.addProperty("bool", false);
		obj.addProperty("int", 10);
		obj.addProperty("float", 99.99f);
		obj.addProperty("string", "abc");

		int status = JsonUtils.toFile(obj, filepath);
		assertEquals(status, EXISTENT_FILE);
	}

	//@Test
	public void toNonexistentFileForceTest() {
		String filepath = "src/test/java/map/json/nonexistent_file_test.json";
		File file = new File(filepath);
		file.delete();
		JsonObject obj = new JsonObject();
		obj.addProperty("bool", false);
		obj.addProperty("int", 10);
		obj.addProperty("float", 99.99f);
		obj.addProperty("string", "abc");

		int status = JsonUtils.toFile(obj, filepath, true);
		assertEquals(status, FILE_OK);
	}

	//@Test
	public void toExistentFileForceTest() {
		String filepath = "src/test/java/map/json/existent_file_test.json";
		JsonObject obj = new JsonObject();
		obj.addProperty("bool", false);
		obj.addProperty("int", 10);
		obj.addProperty("float", 99.99f);
		obj.addProperty("string", "abc");

		int status = JsonUtils.toFile(obj, filepath, true);
		assertEquals(status, FILE_OK);
	}


}
