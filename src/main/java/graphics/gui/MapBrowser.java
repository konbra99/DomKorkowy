package graphics.gui;

import com.google.gson.JsonObject;
import graphics.Config;
import graphics.Input;
import graphics.Rectangle;
import logic.Entity;
import map.MapManager;
import map.json.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static database.MapsConnector.getMaps;
import static graphics.Config.MOUSE_SCROLL_SPEED;


public class MapBrowser extends Entity {
    // searchRect to pasek na ktorym beda wyswietlane mapy
    Rectangle searchRect;
    ArrayList<Button> mapButtons;
    public MapButton active = null;
    float y_min = Config.SEARCH_Y;
    float y_max = 0.0f;
    float y_curr = 0.0f;

    public void createMapButtons() {
        File mapDirectory = new File(Config.MAP_PATH);
        File[] mapFiles = mapDirectory.listFiles();
        mapButtons = new ArrayList<>();
        y_min = Config.SEARCH_Y;
        y_curr = 0.0f;

        if (mapFiles != null) {
            for (File mapFile : mapFiles) {
                if (!mapFile.getName().endsWith("json")) {
                    System.out.println(mapFile.getAbsolutePath());
                    continue;
                }
                try {
                    MapManager mapManager = new MapManager();
                    JsonObject obj = JsonUtils.fromFile(mapFile.getAbsolutePath());
                    mapManager.fromJson(obj);
                    addMapButton(mapManager);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createServerMapButtons() {
        List<String> maps = getMaps();
        mapButtons = new ArrayList<>();
        y_min = Config.SEARCH_Y;
        y_curr = 0.0f;

        for (String map : maps) {
            MapManager mapManager = new MapManager();
            JsonObject obj = JsonUtils.fromString(map);
            mapManager.fromJson(obj);
            addMapButton(mapManager);
        }
    }

    public void createNewMapButtons() {
        mapButtons = new ArrayList<>();
        y_min = Config.SEARCH_Y;
        y_curr = 0.0f;

        NewMapButton button = new NewMapButton(Button.LONG_BUTTON);
        button.setText("Przycisk 1", "msgothic.bmp", 0.05f, 0.08f);
        addButton(button);
    }

    public MapBrowser() {
        this.searchRect = new Rectangle(Config.SEARCH_X, -1.1f, 1.0f, 2.2f);
        this.searchRect.initGL("gui/blue.png",
                "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void update() {
        for (Button button : mapButtons) {
            button.getRectangle().move(0.0f, -Input.MOUSE_SCROLL_Y*MOUSE_SCROLL_SPEED);
            if (searchRect.collidesWith(button.getRectangle())) {
                button.update();
            }
        }
    }

    @Override
    public void draw() {
        searchRect.setAlpha(Config.SEARCH_RECT_ALPH);
        searchRect.draw();
        for (Button button : mapButtons) {
            if (searchRect.collidesWith(button.getRectangle())) {
                button.draw();
            }
        }
    }

    public void deselectAll() {
        for(Button button: mapButtons) {
            button.is_selected = false;
        }
    }

    public void addMapButton(MapManager mapManager) {
        MapButton button = new MapButton(mapManager, this, Button.LONG_BUTTON);
        button.getRectangle().move(Config.SEARCH_X, y_min / Config.RESOLUTION);
        button.setText(button.map.mapName, "msgothic.bmp", 0.05f, 0.08f);
        mapButtons.add(button);
        y_min -= Config.MAP_BUTTON_HEIGHT + 0.1f;
    }

    public void addButton(Button button) {
        button.getRectangle().move(Config.SEARCH_X, y_min / Config.RESOLUTION);
        mapButtons.add(button);
        y_min -= Config.MAP_BUTTON_HEIGHT + 0.1f;
    }
}
