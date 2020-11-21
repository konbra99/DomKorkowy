package graphics.utils;

import graphics.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ImageUtils {
    public static HashMap<String, int[]> sizeMap = new HashMap<>(); // mapa wymiarow zdjec
    private static int width, height;

    private ImageUtils() {
    }

    public static int[] load(String path) {
        if (sizeMap.containsKey(path)) {
            return sizeMap.get(path);
        }

        int[] pixels = null;
        try {
            BufferedImage image = ImageIO.read(
                    new FileInputStream(Config.TEX_PATH + path));
            width = image.getWidth();
            height = image.getHeight();
            sizeMap.put(path, new int[]{width, height});

            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        return data;
    }
}
