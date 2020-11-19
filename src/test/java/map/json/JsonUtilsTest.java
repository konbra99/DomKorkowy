package map.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static constants.JsonSerializationStatus.*;
import static org.junit.Assert.*;

public class JsonUtilsTest {

	//@Test
	public void fromStringTest() {
		try {
			String str = """
				{"backgroundId":0,"entities":[]}""";
			JsonObject obj = JsonUtils.fromString(str);
			assertTrue(true);
		} catch (JsonSyntaxException e) {
			fail();
		}
	}

	//@Test
	public void fromBadStringTest() {
		try {
			String str = """
				{"backgroundId":0,"entities":[][}""";
			JsonObject obj = JsonUtils.fromString(str);
			fail();
		} catch (JsonSyntaxException e) {
			assertTrue(true);
		}
	}

	//Test
	public void fromFileTest() {
		try {
			String filepath = "src/test/java/map/json/ok_file_test.json";
			JsonObject obj = JsonUtils.fromFile(filepath);
			assertTrue(true);
		} catch (IOException e) {
			fail();
		} catch (JsonSyntaxException e) {
			fail();
		}
	}

	//@Test
	public void fromBadFileTest() {
		try {
			String filepath = "src/test/java/map/json/bad_file_test.json";
			JsonObject obj = JsonUtils.fromFile(filepath);
			fail();
		} catch (IOException e) {
			fail();
		} catch (JsonSyntaxException e) {
			assertTrue(true);
		}

	}

	//@Test
	public void fromNotExistingFileTest() {
		try {
			String filepath = "src/test/java/map/json/notexistent_file_test.json";
			JsonObject obj = JsonUtils.fromFile(filepath);
			fail();
		} catch (IOException e) {
			assertTrue(true);
		} catch (JsonSyntaxException e) {
			fail();
		}
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
		try {
			String filepath = "src/test/java/map/json/nonexistent_file_test.json";
			File file = new File(filepath);
			file.delete();

			JsonObject obj = new JsonObject();
			obj.addProperty("bool", false);
			obj.addProperty("int", 10);
			obj.addProperty("float", 99.99f);
			obj.addProperty("string", "abc");

			JsonUtils.toFile(obj, filepath);
			assertTrue(true);
		} catch (IOException e) {
			fail();
		}

	}

	//@Test
	public void toExistentFileTest() {
		try {
			String filepath = "src/test/java/map/json/existent_file_test.json";
			JsonObject obj = new JsonObject();
			obj.addProperty("bool", false);
			obj.addProperty("int", 10);
			obj.addProperty("float", 99.99f);
			obj.addProperty("string", "abc");

			JsonUtils.toFile(obj, filepath);
			assertTrue(true);
		} catch (IOException e) {
			fail();
		}
	}
}
