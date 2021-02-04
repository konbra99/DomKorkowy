package graphics.gui;

import graphics.Config;
import graphics.Engine;
import server.Lobby;

public class RoomField extends DataField {
    public Lobby lobby;
    MapBrowser browser;
    String str_max_players;

    public RoomField(Lobby lobby, MapBrowser browser, String[] textures) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
        this.browser = browser;
        this.lobby = lobby;
        this.str_max_players = Integer.toString(lobby.max_players);

        this.action = () -> {
            this.browser.roomActive = this;
            browser.deselectAll();
            this.is_selected = true;
            this.is_invalid = false;
            System.out.println("ustawiony pokoj: " + lobby);
        };
    }

    @Override
    public void draw() {
        super.draw();
        Engine.fontLoader.renderText(lobby.map_name, font, text_x - 0.25f, text_y - 0.04f,
                charwidth * 0.7f, charheight * 0.7f, 0.0f, 0.0f, 0.0f, 1.0f);
        Engine.fontLoader.renderText(lobby.admin_name, font, text_x + 0.1f, text_y - 0.04f,
                charwidth * 0.7f, charheight * 0.7f, 0.0f, 0.0f, 0.0f, 1.0f);
        Engine.fontLoader.renderText(str_max_players, font, text_x + 0.4f, text_y - 0.04f,
                charwidth * 0.7f, charheight * 0.7f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);
        this.text_x = this.rectangle.posX + 0.1f;
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2)) * Config.RESOLUTION;
    }

}
