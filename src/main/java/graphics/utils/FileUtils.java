package graphics.utils;

import graphics.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtils {
    private FileUtils() {
    }

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
