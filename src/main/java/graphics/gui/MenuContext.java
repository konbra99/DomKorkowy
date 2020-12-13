package graphics.gui;

import graphics.Engine;

public class MenuContext extends Context {
    Button singleplayer, multiplayer, editor, settings, quit;

    public MenuContext(String background) {
        super(background);
        super.init();

        // przyciski
        this.singleplayer = new Button(-0.7f, 0.7f, 1.4f, 0.2f,
                () -> {
                    Engine.browser.refreshList();
                    Engine.browser.addPlayButton();
                    Engine.browser.addBackButton();
                    Engine.browser.addLocalMapsButton();
                    Engine.browser.addServerMapsButton();
                    Engine.activeContext = Engine.browser;
                    Engine.STATE = Engine.GAME_STATE.BROWSER;
                },
                Button.LONG_BUTTON);
        this.singleplayer.setText("Singleplayer", "msgothic.bmp", 0.05f, 0.1f);
        this.multiplayer = new Button(-0.7f, 0.4f, 1.4f, 0.2f,
                () -> Engine.STATE = Engine.GAME_STATE.MULTIPLAYER,
                Button.LONG_BUTTON);
        this.multiplayer.setText("Multiplayer", "msgothic.bmp", 0.05f, 0.1f);
        this.editor = new Button(-0.7f, 0.1f, 1.4f, 0.2f,
                () ->   {
                    Engine.activeContext = Engine.editor;
                    Engine.STATE = Engine.GAME_STATE.EDITOR; },
                Button.LONG_BUTTON);
        this.editor.setText("Edytor map", "msgothic.bmp", 0.05f, 0.1f);
        this.settings = new Button(-0.7f, -0.2f, 1.4f, 0.2f,
                () -> Engine.STATE = Engine.GAME_STATE.EDITOR,
                Button.LONG_BUTTON);
        this.settings.setText("Ustawienia", "msgothic.bmp", 0.05f, 0.1f);
        this.quit = new Button(-0.7f, -0.5f, 1.4f, 0.2f,
                () -> Engine.STATE = Engine.GAME_STATE.EXIT,
                Button.LONG_BUTTON);
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
        super.draw();
        this.singleplayer.draw();
        this.multiplayer.draw();
        this.editor.draw();
        this.settings.draw();
        this.quit.draw();
    }
}
