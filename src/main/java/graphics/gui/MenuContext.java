package graphics.gui;

import graphics.Engine;

import static graphics.gui_enums.MenuButtonNames.*;

public class MenuContext extends Context {
    Button singleplayer, multiplayer, editor, settings, authors, quit;

    public MenuContext(String background) {
        super(background);
        super.init();

        // SINGLEPLAYER
        this.singleplayer = new Button(-0.5f, 0.7f, 1.0f, 0.25f,
                () -> {
                    Engine.browser.refreshList();
                    Engine.browser.addPlayButton();
                    Engine.browser.addBackButton();
                    Engine.browser.addLocalMapsButton();
                    Engine.browser.addServerMapsButton();
                    Engine.activeContext = Engine.browser;
                },
                Button.LONG_BUTTON);
        this.singleplayer.setText("Singleplayer", "msgothic.bmp", 0.05f, 0.1f);

        // MULTIPLAYER
        this.multiplayer = new Button(-0.5f, 0.4f, 1.0f, 0.25f,
                () -> {
                    Engine.browser.refreshContext();
                    Engine.browser.addRefreshButton();
                    Engine.browser.addBackButton();
                    Engine.browser.buttonMap.get(NEW_LOBBY).setActiveVisible(true, true);
                    Engine.browser.buttonMap.get(JOIN_LOBBY).setActiveVisible(true, true);
                    Engine.browser.browser.removeAll();
                    Engine.activeContext = Engine.browser;
                },
                Button.LONG_BUTTON);
        this.multiplayer.setText("Multiplayer", "msgothic.bmp", 0.05f, 0.1f);

        // MAP EDITOR
        this.editor = new Button(-0.5f, 0.1f, 1.0f, 0.25f,
                () -> {
//                  Engine.activeContext = Engine.editor;
//                  Engine.STATE = Engine.GAME_STATE.EDITOR; },
                    Engine.browser.refreshList();
                    Engine.browser.addEditButton();
                    Engine.browser.addBackButton();
                    Engine.browser.addLocalMapsButton();
                    Engine.browser.addServerMapsButton();
                    Engine.browser.addNewMapButton();
                    Engine.activeContext = Engine.browser;
                },
                Button.LONG_BUTTON);
        this.editor.setText("Edytor map", "msgothic.bmp", 0.05f, 0.1f);
        this.settings = new Button(-0.5f, -0.2f, 1.0f, 0.25f,
                () -> Engine.activeContext = Engine.settings,
                Button.LONG_BUTTON);
        this.settings.setText("Ustawienia", "msgothic.bmp", 0.05f, 0.1f);
        this.authors = new Button(-0.5f, -0.5f, 1.0f, 0.25f,
                () -> Engine.activeContext = Engine.authors,
                Button.LONG_BUTTON);
        this.authors.setText("Autorzy", "msgothic.bmp", 0.05f, 0.1f);
        this.quit = new Button(-0.5f, -0.8f, 1.0f, 0.25f, null, Button.LONG_BUTTON);
        this.quit.setText("Wyjdz z gry", "msgothic.bmp", 0.05f, 0.1f);
    }

    public void update() {
        this.singleplayer.update();
        this.multiplayer.update();
        this.editor.update();
        this.settings.update();
        this.authors.update();
        this.quit.update();
    }

    public void draw() {
        super.draw();
        this.singleplayer.draw();
        this.multiplayer.draw();
        this.editor.draw();
        this.settings.draw();
        this.authors.draw();
        this.quit.draw();
    }
}
