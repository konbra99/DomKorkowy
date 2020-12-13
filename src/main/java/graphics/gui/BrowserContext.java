package graphics.gui;

import graphics.Engine;
import map.MapManager;

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
        buttonList = new ArrayList<>();
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
        Button button = new Button(0.58f, -0.85f, 0.25f, 0.5f, null, Button.LEFT_ARROW);
        button.setText("PLAY", "msgothic.bmp", 0.05f, 0.12f);
        button.action = () -> {
            if (browser.active != null) {
                Engine.gameplay.map = browser.active.map;
                Engine.gameplay.refreshContext();
                Engine.activeContext = Engine.gameplay;
                Engine.STATE = Engine.GAME_STATE.GAMEPLAY;
            }

        };
        buttonList.add(button);
    }

    public void addBackButton() {
        Button button = new Button(0.28f, -0.85f, 0.25f, 0.5f, null, Button.RIGHT_ARROW);
        button.setText("BACK", "msgothic.bmp", 0.05f, 0.12f);
        button.action = () -> {
            Engine.activeContext = Engine.menu;
            Engine.STATE = Engine.GAME_STATE.MENU;
        };
        buttonList.add(button);
    }

    public void addEditButton() {
        Button button = new Button(0.58f, -0.85f, 0.25f, 0.5f, null, Button.LEFT_ARROW);
        button.setText("EDIT", "msgothic.bmp", 0.05f, 0.1f);
        button.action = () -> {
            if (browser.is_new_map) {
                System.out.println("New map");
                MapManager map = new MapManager();
                for (Button but: browser.mapButtons)
                    ((NewMapButton)but).getValue(map);
            }
            else {
                System.out.println("NOT new map");
//                Engine.activeContext = Engine.editor;
//                Engine.STATE = Engine.GAME_STATE.EDITOR;
            }

        };
        buttonList.add(button);
    }

    public void addLocalMapsButton() {
        Button button = new Button(0.25f, 0.6f, 0.3f, 0.3f, null, Button.SHORT_BUTTON);
        button.setText("Local", "msgothic.bmp", 0.05f, 0.08f);
        button.action = () -> {
            System.out.println("Local");
            browser.createMapButtons();
        };
        buttonList.add(button);
    }

    public void addServerMapsButton() {
        Button button = new Button(0.58f, 0.6f, 0.3f, 0.3f, null, Button.SHORT_BUTTON);
        button.setText("Server", "msgothic.bmp", 0.05f, 0.08f);
        button.action = () -> {
            System.out.println("Server");
            browser.createServerMapButtons();
        };
        buttonList.add(button);
    }

    public void addNewMapButton() {
        Button button = new Button(0.25f, 0.27f, 0.62f, 0.3f, null, Button.MEDIUM_BUTTON);
        button.setText("New", "msgothic.bmp", 0.05f, 0.08f);
        button.action = () -> {
            System.out.println("Server");
            browser.createNewMapButtons();
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
        }
    }
}
