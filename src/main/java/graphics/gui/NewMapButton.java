package graphics.gui;

import graphics.Config;
import graphics.Engine;
import map.MapManager;

public class NewMapButton extends Button {
    public MapManager map;
    MapBrowser browser;
    TextArea textArea;

    public NewMapButton(String [] textures) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
        textArea = new TextArea(-0.5f, 0.8f, 0.5f, 0.2f, 0.05f, 0.05f, "msgothic.bmp",
                1.0f, 1.0f, 1.0f, 0.0f);
        this.action = () -> {
            this.is_selected = true;
            System.out.println("przycisk wcisniety");
        };
    }

    @Override
    public void update() {
        super.update();
        Engine.active = textArea;
        this.textArea.update();
    }

    @Override
    public void draw() {
        super.draw();
        this.textArea.draw();
    }
}
