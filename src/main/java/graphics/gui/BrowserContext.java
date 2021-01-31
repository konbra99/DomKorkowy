package graphics.gui;

import graphics.Engine;
import graphics.gui_enums.MenuButtonNames;
import map.MapManager;
import server.Lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static graphics.gui_enums.MenuButtonNames.*;
import static graphics.gui_enums.NewLobbyButtonNames.*;
import static server.Protocol.*;

public class BrowserContext extends Context {



    // ZMIENNE
    public MapBrowser browser;
    ArrayList<Button> buttonList;
    public Map<MenuButtonNames, Button> buttonMap;
    Warning warning;

    public BrowserContext(String background) {
        super(background);
        super.init();
        this.browser = new MapBrowser();
        this.buttonList = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.warning = new Warning();
        addStartButton();
        addNewLobbyButton();
        addCreateLobbyButton();
        addJoinButton();
    }

    public void refreshList() {
        buttonList = new ArrayList<>();
        this.browser.createMapButtons();
        this.warning = new Warning();
    }

    @Override
    public void refreshContext() {
        this.buttonList.clear();
        for(Button b: buttonMap.values())
            b.setActiveVisible(false, false);
    }

    public void addButton(Button button) {
        buttonList.add(button);
    }

    public void addPlayButton() {
        Button button = new Button(0.58f, -0.85f, 0.25f, 0.5f, null, Button.RIGHT_ARROW);
        button.setText("PLAY", "msgothic.bmp", 0.05f, 0.12f);
        button.action = () -> {
            if (browser.active != null) {
                GameplayContext.map = browser.active.map;
                Engine.gameplay.refreshContext();
                Engine.activeContext = Engine.gameplay;
                Engine.STATE = Engine.GAME_STATE.GAMEPLAY;
            }

        };
        buttonList.add(button);
    }

    public void addJoinButton() {
        Button button = new Button(0.58f, -0.85f, 0.25f, 0.5f, null, Button.RIGHT_ARROW);
        button.setText("JOIN", "msgothic.bmp", 0.05f, 0.12f);
        button.setActiveVisible(false, false);
        button.action = () -> {
            if (browser.roomActive != null) {
                int status = Engine.client.lobbyJoin(browser.roomActive.lobby.id);
                if (status == LOBBY_NOT_EXIST) {
                    System.out.println("lobby nie istnieje");
                }
                else if (status == LOBBY_IS_FULL) {
                    System.out.println("lobby nie ma miejsc");
                }
                else if (status == LOBBY_JOINED) {
                    System.out.println("dolaczono do lobby");
                    Engine.browser.refreshContext();
                    Engine.browser.addExitButton();
                    Engine.browser.addReadyButton();
                    Engine.browser.browser.removeAll();
                    Engine.activeContext = Engine.browser;
                    Engine.browser.browser.createLobbyButtons();
                    Engine.STATE = Engine.GAME_STATE.BROWSER;
                    Engine.browser.browser.join(Engine.client.id);
                    Engine.client.spawnLobbyReader();
                }
            }
        };
        buttonMap.put(JOIN_LOBBY, button);
    }

    public void addCreateLobbyButton() {
        Button button = new Button(0.58f, -0.85f, 0.25f, 0.5f, null, Button.RIGHT_ARROW);
        button.setText("CREATE", "msgothic.bmp", 0.05f, 0.12f);
        button.setActiveVisible(false, false);
        button.action = () -> {
            String lobby_name = browser.dataFieldsMap.get(LOBBY_NAME).getAsNonemptyString();
            Integer max_players = browser.dataFieldsMap.get(LOBBY_MAX_PLAYERS).getAsPositiveInteger();
            String map_context = browser.dataFieldsMap.get(LOBBY_MAP).getAsString();
            Integer game_mode = browser.dataFieldsMap.get(LOBBY_MODE).getAsInteger();
            System.out.println(map_context);

            if (lobby_name != null && max_players != null && map_context != null && game_mode != null) {
                Lobby lobby = new Lobby();
                lobby.lobby_name = lobby_name;
                lobby.max_players = max_players;
                lobby.map_context = map_context;
                lobby.game_mode = game_mode;
                Engine.client.lobbyCreate(lobby.toJson().toString());
            }
        };
        buttonMap.put(CREATE_LOBBY, button);
    }

    public void addRefreshButton() {
        Button button = new Button(0.25f, 0.6f, 0.6f, 0.3f, null, Button.MEDIUM_BUTTON);
        button.setText("REFRESH", "msgothic.bmp", 0.05f, 0.12f);
        button.action = () -> {
            browser.dataFields = new ArrayList<>();
            if (Engine.client.isNotConnected())
                warning.showWarning("Klient nie zostal polaczony z serwerem");
            else {
                browser.createServerRoomsButtons();
                buttonMap.get(JOIN_LOBBY).setActiveVisible(true, true);
                buttonMap.get(CREATE_LOBBY).setActiveVisible(false, false);
            }


        };
        buttonList.add(button);
    }

    public void addReadyButton() {
        Button button = new Button(0.25f, 0.6f, 0.6f, 0.3f, null, Button.MEDIUM_BUTTON);
        button.setText("NOT READY", "msgothic.bmp", 0.05f, 0.12f);
        button.action = () -> {

            button.text = (Engine.client.isReady) ? "NOT READY" : "READY";
            System.out.println((Engine.client.isReady) ? "NOT READY" : "READY");

            Engine.client.isReady = !Engine.client.isReady;
            Engine.client.lobbyStatus();
        };
        buttonList.add(button);
    }

    public void addStartButton() {
        Button button = new Button(0.25f, 0.2f, 0.6f, 0.3f, null, Button.MEDIUM_BUTTON);
        button.setText("START", "msgothic.bmp", 0.05f, 0.12f);
        button.setActiveVisible(false, false);
        button.action = () -> Engine.client.lobbyStart();
        buttonMap.put(START, button);
    }

    public void addBackButton() {
        Button button = new Button(0.28f, -0.85f, 0.25f, 0.5f, null, Button.LEFT_ARROW);
        button.setText("BACK", "msgothic.bmp", 0.05f, 0.12f);
        button.action = () -> {
            Engine.activeContext = Engine.menu;
            Engine.STATE = Engine.GAME_STATE.MENU;
        };
        buttonList.add(button);
    }

    public void addExitButton() {
        Button button = new Button(0.28f, -0.85f, 0.25f, 0.5f, null, Button.LEFT_ARROW);
        button.setText("EXIT", "msgothic.bmp", 0.05f, 0.12f);
        button.action = () -> {
            System.out.println("BrowserContext -> ROOM EXIT");
            Engine.client.lobbyExit();
            Engine.activeContext = Engine.menu;
            Engine.STATE = Engine.GAME_STATE.MENU;
        };
        buttonList.add(button);
    }

    public void addEditButton() {
        Button button = new Button(0.58f, -0.85f, 0.25f, 0.5f, null, Button.RIGHT_ARROW);
        button.setText("EDIT", "msgothic.bmp", 0.05f, 0.1f);
        button.action = () -> {
            if (browser.is_new_map) {
                MapManager map = new MapManager();
                for (DataField dataField: browser.dataFields)
                    dataField.getValue(map);
                Engine.STATE = Engine.GAME_STATE.EDITOR;
                Engine.editor = new EditorContext(map);
                Engine.activeContext = Engine.editor;
            }
            else {
                Engine.STATE = Engine.GAME_STATE.EDITOR;
                Engine.editor = new EditorContext(browser.active.map);
                Engine.activeContext = Engine.editor;
                //System.out.println("NOT new map");
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
            if (Engine.client.isNotConnected())
                warning.showWarning("Klient nie zostal polaczony z serwerem");
            else
                browser.createServerMapButtons();
        };
        buttonList.add(button);
    }

    public void addNewMapButton() {
        Button button = new Button(0.25f, 0.27f, 0.62f, 0.3f, null, Button.MEDIUM_BUTTON);
        button.setText("New", "msgothic.bmp", 0.05f, 0.08f);
        button.action = () -> browser.createNewMapButtons();
        buttonList.add(button);
    }

    public void addNewLobbyButton() {
        Button button = new Button(0.25f, 0.27f, 0.6f, 0.3f, null, Button.MEDIUM_BUTTON);
        button.setText("New", "msgothic.bmp", 0.05f, 0.08f);
        button.setActiveVisible(false, false);
        button.action = () -> {
            browser.createNewLobbyButtons();
            buttonMap.get(JOIN_LOBBY).setActiveVisible(false, false);
            buttonMap.get(CREATE_LOBBY).setActiveVisible(true, true);
        };
        buttonMap.put(NEW_LOBBY, button);
    }

    @Override
    public void update() {
        if (this.warning.is_visible) {
            this.warning.update();
        } else {
            this.browser.update();
            for (Button b : buttonList)
                b.update();
            for(Button b: buttonMap.values())
                b.update();
        }
    }

    @Override
    public void draw() {
        this.rectangle.draw();
        this.browser.draw();
        for (Button b : buttonList)
            b.draw();
        for(Button b: buttonMap.values())
            b.draw();
        this.warning.draw();
    }
}
