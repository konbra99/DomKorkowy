package graphics.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import graphics.Config;
import map.MapManager;
import map.json.JsonUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
//    private FileUtils() {
//    }

    public static String loadFile(String filename) {
        File file = new File(Config.SHADERS_PATH + filename);
        StringBuilder builder = new StringBuilder();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static void deleteFile(String filename) {
        File myObj = new File(System.getProperty("user.dir") +"\\src\\main\\resources\\Maps\\"+filename);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
