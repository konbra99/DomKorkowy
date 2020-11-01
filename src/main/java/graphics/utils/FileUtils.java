package graphics.utils;

import graphics.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtils {
    private FileUtils() {}

    public static String loadFile(String filename) {
        File file = new File(Main.SHADERS_PATH + filename);
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
}
