package graphics.gui;

import graphics.Config;
import graphics.Rectangle;
import logic.Entity;

import java.io.File;
import java.util.ArrayList;


public class MapBrowser extends Entity {
    // searchRect to pasek na ktorym beda wyswietlane mapy
    Rectangle searchRect;
    ArrayList<MapButton> mapButtons;
    public MapButton active = null;

    public void createMapButtons() {
        File mapDirectory = new File(Config.MAP_PATH);
        File[] mapFiles = mapDirectory.listFiles();
        MapButton button;
        float y = Config.SEARCH_Y;
        if (mapFiles != null) {
            mapButtons = new ArrayList<>();
            for (File mapFile : mapFiles) {
                if (!mapFile.getName().endsWith("json")) {
                    System.out.println(mapFile.getAbsolutePath());
                    continue;
                }
                button = new MapButton(mapFile.getAbsolutePath(), this);
                button.getRectangle().move(Config.SEARCH_X, y / Config.RESOLUTION);
                button.setText(button.map.mapName, "msgothic.bmp", 0.05f, 0.08f);
                mapButtons.add(button);
                y -= Config.MAP_BUTTON_HEIGHT + 0.1f;
            }
        }
    }

    public MapBrowser() {
        this.searchRect = new Rectangle(Config.SEARCH_X, -1.1f, 1.0f, 2.2f);
        this.searchRect.initGL("gui/white.png",
                "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void update() {
        for (MapButton button : mapButtons) {
            if (searchRect.collidesWith(button.getRectangle())) {
                button.update();
            }
        }
    }

    @Override
    public void draw() {
        searchRect.setAlpha(Config.SEARCH_RECT_ALPH);
        searchRect.draw();
        for (MapButton button : mapButtons) {
            if (searchRect.collidesWith(button.getRectangle())) {
                button.draw();
            }
        }
    }
}
