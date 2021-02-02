package graphics.gui;

import client.PlayerAspect;
import graphics.Engine;
import graphics.Input;
import graphics.Rectangle;
import graphics.chat.Chat;
import logic.Entity;
import logic.Player;
import logic.collectibles.HealthPotionSmall;
import map.MapManager;
import map.json.JsonUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameplayContext extends Context {
    public static MapManager map;
    public Rectangle HEALTHBAR;
    public HealthPotionSmall HP_S;
    static public Player KORKOWY;
    public static Chat chat;
    public static Map<Integer, Player> players;
    private static ClassPathXmlApplicationContext context;

    public GameplayContext() {
        context = new ClassPathXmlApplicationContext("application_context.xml");
        map = (MapManager) context.getBean("map");
        HEALTHBAR = new Rectangle(-1.0f, 0.9f, 0.18f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
        chat = new Chat();
    }

    @Override
    public void refreshContext() {
        KORKOWY = (Player) context.getBean("player");
        players = new HashMap<>();

        // init map
        try {
            map.nextStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initGame() {
        KORKOWY = (Player) context.getBean("player");
        map = (MapManager) context.getBean("map");
        players = new HashMap<>();

        // init map
        try {
            map.fromJson(JsonUtils.fromString(Engine.browser.browser.roomActive.lobby.map_context));
            map.nextStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startGame() {
        Engine.activeContext = Engine.gameplay;
        Engine.STATE = Engine.GAME_STATE.GAMEPLAY;
    }

    public static void addPlayer(int id, int color, int team) {
        if (id == Engine.client.id) {
            // KORKOWY
            KORKOWY.setAttributes(id, color, team);
        }
        else {
            // inny gracz
            players.put(id, new Player(id, color, team));
        }
    }


    @Override
    public void update() {
        // update
        map.move();
        map.update();
        KORKOWY.move();
        KORKOWY.update();
        KORKOWY.doActions();
        chat.update();
        for (Player p: players.values())  {
            p.doActions();
            p.updateWeapon();
        }

        Input.resetInputs();
    }

    @Override
    public void draw() {
        map.draw();
        for(Player p: players.values())
            if (p.getStage() == MapManager.getCurrentStageNoumber()) p.draw();
        KORKOWY.draw();
        HEALTHBAR.draw();

        Engine.fontLoader.renderText("K:" + KORKOWY.getKills(), "msgothic.bmp",
                -0.7f, 0.87f, 0.06f, 0.1f,0.0f, 0.0f, 0.0f, 1.0f);

        Engine.fontLoader.renderText("D:" + KORKOWY.getDeaths(), "msgothic.bmp",
                -0.37f, 0.87f, 0.06f, 0.1f,0.0f, 0.0f, 0.0f, 1.0f);

        chat.draw();
    }

    public Collection<Entity> getPlatforms() {
        return map.getCurrentStage().platforms.values();
    }

    public Collection<Entity> getMobs() {
        return map.getCurrentStage().mobs.values();
    }

    public Collection<Entity> getObstacles() {
        return map.getCurrentStage().obstacles.values();
    }

    public Collection<Entity> getDoors() {
        return map.getCurrentStage().doors.values();
    }

    public Collection<Entity> getCollectibles() {
        return map.getCurrentStage().collectibles.values();
    }

    public Collection<Player> getEnemies() { return players.values(); }

    public float[] getStart() { return map.getCurrentStage().start; }

    public static void addMessage(int id, String message) {
        chat.addMessage(id, message);
    }
}
