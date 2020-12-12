package graphics.gui;

import graphics.Engine;

import java.util.ArrayList;

public class BrowserContext extends Context {
    MapBrowser browser;
    ArrayList<Button> buttonList;

    public BrowserContext(String background) {
        super(background);
        super.init();
        this.browser = new MapBrowser();
        this.buttonList = new ArrayList<>();
    }

    public void refreshList() {
        this.browser.createMapButtons();
    }

    @Override
    public void refreshContext() {
        this.buttonList.clear();
    }

    public void addButton(Button button) {
        buttonList.add(button);
    }

    public void addPlayButton() {
        Button button = new Button(0.4f, -0.95f, 0.5f, 0.1f, null);
        button.setText("Graj", "msgothic.bmp", 0.05f, 0.08f);
        button.action = () -> {
            Engine.gameplay.map = browser.active.map;
            Engine.gameplay.init();
            Engine.activeContext = Engine.gameplay;
            Engine.STATE = Engine.GAME_STATE.GAMEPLAY;
        };
        buttonList.add(button);
    }

    public void addEditButton() {
        Button button = new Button(0.4f, 0.0f, 0.5f, 0.1f, null);
        button.setText("Edytuj", "msgothic.bmp", 0.05f, 0.1f);
        button.action = () -> {
            Engine.activeContext = Engine.editor;
            Engine.STATE = Engine.GAME_STATE.EDITOR;
        };
        buttonList.add(button);
    }

    @Override
    public void update() {
        this.browser.update();
        for (Button b : buttonList) {
            b.update();
        }
    }

    @Override
    public void draw() {
        this.rectangle.draw();
        this.browser.draw();
        for (Button b : buttonList) {
            b.draw();
            //System.out.println("rysuje " + b.text);
        }
    }
}
