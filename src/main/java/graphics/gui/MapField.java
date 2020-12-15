package graphics.gui;

import graphics.Config;
import graphics.Engine;
import map.MapManager;
import map.json.JsonUtils;

// on powinien miec kilka stringow
public class MapField extends DataField {
    public MapManager map;
    MapBrowser browser;
    String author = "user";
    String rating = "2/10";
    String stages = "5";
    String filepath;

    Button local;
    Button download;
    Button delete;

    boolean download_flag;
    boolean local_flag;
    boolean delete_flag;

    public MapField(MapManager map, MapBrowser browser, boolean is_local, String [] textures) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
        this.browser = browser;
        this.map = map;

        this.action = () -> {
            this.browser.active = this;
            browser.deselectAll();
            this.is_selected = true;
            this.is_invalid = false;
            System.out.println("ustawiona mapka: " + map.mapName);
        };

        delete = new Button(0.83f, 0.1f, 0.1f, 0.1f, null, Button.DELETE);
        local = new Button(0.83f, 0.1f, 0.1f, 0.1f, ()-> System.out.println("local"), Button.LOCAL);
        download = new Button(0.83f, 0.1f, 0.1f, 0.1f, null, Button.DOWNLOAD);

        download.action = () -> {
            try {
                System.out.println(map.mapObject);
                System.out.println(map.mapName);
                System.out.println(Config.MAP_PATH);
                JsonUtils.toFile(map.mapObject, Config.MAP_PATH + map.mapName + ".json");
                download_flag = false;
                local_flag = true;
                System.out.println("saved succesfully");
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.is_selected = true;
            this.is_invalid = false;
        };

        delete.action = () -> {
            System.out.println("delete " + filepath);
            this.is_selected = true;
            this.is_invalid = false;
            JsonUtils.removeFile(filepath);
            is_active = false;
        };

        if (is_local) {
            download_flag = false;
            local_flag = false;
            delete_flag = true;
        } else {
            download_flag = true;
            local_flag = false;
            delete_flag = false;
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (this.author.length() > 0 && this.font.length() > 0) {
            Engine.fontLoader.renderText(author, font, text_x-0.25f, text_y-0.04f, charwidth*0.7f, charheight*0.7f,
                    0.0f, 0.0f, 0.0f, 1.0f);
        }
        if (this.rating.length() > 0 && this.font.length() > 0) {
            Engine.fontLoader.renderText(rating, font, text_x+0.4f, text_y-0.04f, charwidth*0.7f, charheight*0.7f,
                    0.0f, 0.0f, 0.0f, 1.0f);
        }
        if (this.stages.length() > 0 && this.font.length() > 0) {
            Engine.fontLoader.renderText(stages, font, text_x+0.1f, text_y-0.04f, charwidth*0.7f, charheight*0.7f,
                    0.0f, 0.0f, 0.0f, 1.0f);
        }
        if (delete_flag)
            delete.draw();
        if (local_flag)
            local.draw();
        if (download_flag)
            download.draw();
    }

    @Override
    public void update() {
        super.update();
        if (is_active) {
            if (delete_flag)
                delete.update();
            if (local_flag)
                local.update();
            if (download_flag)
                download.update();
        }
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);
        delete.move(dx, dy);
        local.move(dx, dy);
        download.move(dx, dy);
    }
}
