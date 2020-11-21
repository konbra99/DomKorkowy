package graphics;

public class Main {
    public static int WIDTH = 1700, HEIGHT = 1000;
    public static float RESOLUTION = (float) WIDTH / HEIGHT;
    public static String SHADERS_PATH = "src/main/resources/Shaders/";
    public static String TEX_PATH = "src/main/resources/Textures/";

    public static void main(String[] args) {
        new Engine().run();
    }
}
