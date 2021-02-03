package graphics;

public class Config {
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    public static float RESOLUTION = (float) WIDTH / HEIGHT;
    public static String SHADERS_PATH = "src/main/resources/Shaders/";
    public static String TEX_PATH = "src/main/resources/Textures/";
    public static String MAP_PATH = "src/main/resources/Maps/";

    public static float[] HIT_ANGLES = {-5.0f, 10.0f, -10.0f, 5.0f};
    public static float[] ATTACK_MAX_ANGLES = {90.0f, 70.0f};
    public static int ATTACK_SPEED_FACTOR = 120;
    public static float MAP_BUTTON_WIDTH = 1.0f, MAP_BUTTON_HEIGHT = 0.3f;
    public static float SEARCH_X = -0.9f, SEARCH_Y = 0.95f - MAP_BUTTON_HEIGHT;
    public static float SEARCH_RECT_ALPH = 0.7f;
    public static float MOUSE_SCROLL_SPEED = 0.05f;

    public static String[] backgrounds = {
            "background/sky.png",
            "background/background2.png",
            "background/background3.png",
            "background/background4.png",
            "background/background5.png",
            "background/background6.png",
            "background/bg.jpg"
    };
}
