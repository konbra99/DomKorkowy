package graphics.gui;

import com.google.gson.JsonObject;
import graphics.Config;
import map.MapManager;
import map.json.JsonUtils;

// on powinien miec kilka stringow
public class MapButton extends Button {
    public MapManager map;
    MapBrowser browser;

    public MapButton(String mapFile, MapBrowser browser) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null);
        this.map = new MapManager();
        this.browser = browser;
        try {
            System.out.println(mapFile);
            JsonObject obj = JsonUtils.fromFile(mapFile);
            map.fromJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.action = () -> {
            this.browser.active = this;
            System.out.println("ustawiona mapka: " + map.mapName);
        };
    }
}
