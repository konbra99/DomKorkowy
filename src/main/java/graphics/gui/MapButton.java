package graphics.gui;

import graphics.Config;
import graphics.Engine;
import map.MapManager;

// on powinien miec kilka stringow
public class MapButton extends Button {
    public MapManager map;
    MapBrowser browser;
    String author = "user";
    String rating = "2/10";
    String stages = "5";

    public MapButton(MapManager map, MapBrowser browser, String [] textures) {
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
    }
}
