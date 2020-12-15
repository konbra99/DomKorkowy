package graphics.gui;

import com.google.gson.JsonObject;
import graphics.Config;
import graphics.Engine;
import graphics.Input;
import graphics.Rectangle;
import logic.Entity;
import map.MapManager;
import map.json.JsonUtils;
import server.TempRoom;
import server.TempServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static graphics.Config.MOUSE_SCROLL_SPEED;


public class MapBrowser extends Entity {
    // searchRect to pasek na ktorym beda wyswietlane mapy
    Rectangle searchRect;
    ArrayList<DataField> dataFields;
    public MapField active = null;
    float y_min = Config.SEARCH_Y;
    float y_max = 0.0f;
    float y_curr = 0.0f;
    boolean is_new_map = false;

    public void createMapButtons() {
        File mapDirectory = new File(Config.MAP_PATH);
        File[] mapFiles = mapDirectory.listFiles();
        is_new_map = false;
        dataFields = new ArrayList<>();
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
        if (Engine.client.isNotConnected()) {
            System.out.println("Nie poloczono z serwerem.");
            return;
        }

        List<String> maps = Engine.client.getMaps();

        dataFields = new ArrayList<>();
        is_new_map = false;
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
        dataFields = new ArrayList<>();
        y_min = Config.SEARCH_Y;
        y_curr = 0.0f;
        is_new_map = true;

        DataField button;

        button = new TextField(this, TextField.Type.NAME, Button.LONG_BUTTON);
        button.setText("nazwa:", "msgothic.bmp", 0.05f, 0.08f);
        addButton(button);


        button = new TextField(this, TextField.Type.AUTHOR, Button.LONG_BUTTON);
        button.setText("autor:", "msgothic.bmp", 0.05f, 0.08f);
        addButton(button);

        button = new TextField(this, TextField.Type.DESCRIPTION, Button.LONG_BUTTON);
        button.setText("opis:", "msgothic.bmp", 0.05f, 0.08f);
        addButton(button);

        button = new TextField(this, TextField.Type.TIME, Button.LONG_BUTTON);
        button.setText("czas:", "msgothic.bmp", 0.05f, 0.08f);
        addButton(button);

        String[] option_strings = new String[]{"EASY", "MEDIUM", "HARD"};
        Object[] option_values = new Object[] {0.0f, 0.5f, 1.0f};
        button = new OptionField(this, Button.LONG_BUTTON, option_strings, option_values);
        button.setText("trud:", "msgothic.bmp", 0.05f, 0.08f);
        addButton(button);
    }

    public void createServerRoomsButtons() {
        TempRoom[] rooms = TempServer.getRooms();
        dataFields = new ArrayList<>();
        is_new_map = false;
        y_min = Config.SEARCH_Y;
        y_curr = 0.0f;

        for (TempRoom room : rooms) {
            RoomField roomField = new RoomField(room, this, Button.LONG_BUTTON);
            roomField.setText(room.room_name, "msgothic.bmp", 0.05f, 0.08f);
            addButton(roomField);
        }
    }

    public MapBrowser() {
        this.searchRect = new Rectangle(Config.SEARCH_X, -1.1f, 1.0f, 2.2f);
        this.searchRect.initGL("gui/blue.png",
                "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void update() {
        for (Button button : dataFields) {
            button.move(0.0f, -Input.MOUSE_SCROLL_Y*MOUSE_SCROLL_SPEED);
            if (searchRect.collidesWith(button.getRectangle())) {
                button.update();
            }
        }
    }

    @Override
    public void draw() {
        searchRect.setAlpha(Config.SEARCH_RECT_ALPH);
        searchRect.draw();
        for (Button button : dataFields) {
            if (searchRect.collidesWith(button.getRectangle())) {
                button.draw();
            }
        }
    }

    public void deselectAll() {
        for(Button button: dataFields) {
            button.is_selected = false;
        }
    }

    public void addMapButton(MapManager mapManager) {
        MapField button = new MapField(mapManager, this, Button.LONG_BUTTON);
        button.move(Config.SEARCH_X, y_min / Config.RESOLUTION);
        button.setText(button.map.mapName, "msgothic.bmp", 0.05f, 0.08f);
        dataFields.add(button);
        y_min -= Config.MAP_BUTTON_HEIGHT + 0.1f;
    }

    public void addButton(DataField button) {
        button.move(Config.SEARCH_X, y_min / Config.RESOLUTION);
        dataFields.add(button);
        y_min -= Config.MAP_BUTTON_HEIGHT + 0.1f;
    }
}
