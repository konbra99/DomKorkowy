package graphics;

import com.google.gson.JsonObject;
import graphics.gui.Action;
import graphics.gui.Button;
import map.json.JsonUtils;

public class Menu {
    Button singleplayer, multiplayer, editor, settings, quit;

    public Menu() {
        Engine.background.setTexture("background/menubg.jpg");

        // przyciski
        this.singleplayer = new Button(-0.7f, 0.7f, 1.4f, 0.2f,
                () -> {
                    Engine.browser.createMapButtons();
                    Engine.STATE = Engine.GAME_STATE.BROWSER;
                });
        this.singleplayer.setText("Singleplayer", "msgothic.bmp", 0.05f, 0.1f);
        this.multiplayer = new Button(-0.7f, 0.4f, 1.4f, 0.2f,
                () -> Engine.STATE = Engine.GAME_STATE.MULTIPLAYER);
        this.multiplayer.setText("Multiplayer", "msgothic.bmp", 0.05f, 0.1f);
        this.editor = new Button(-0.7f, 0.1f, 1.4f, 0.2f,
                () -> Engine.STATE = Engine.GAME_STATE.EDITOR);
        this.editor.setText("Edytor map", "msgothic.bmp", 0.05f, 0.1f);
        this.settings = new Button(-0.7f, -0.2f, 1.4f, 0.2f,
                () -> Engine.STATE = Engine.GAME_STATE.EDITOR);
        this.settings.setText("Ustawienia", "msgothic.bmp", 0.05f, 0.1f);
        this.quit = new Button(-0.7f, -0.5f, 1.4f, 0.2f,
                () -> Engine.STATE = Engine.GAME_STATE.EXIT);
        this.quit.setText("Wyjdz z gry", "msgothic.bmp", 0.05f, 0.1f);
    }

    public void update() {
        this.singleplayer.update();
        this.multiplayer.update();
        this.editor.update();
        this.settings.update();
        this.quit.update();
    }

    public void draw() {
        this.singleplayer.draw();
        this.multiplayer.draw();
        this.editor.draw();
        this.settings.draw();
        this.quit.draw();
    }
}
