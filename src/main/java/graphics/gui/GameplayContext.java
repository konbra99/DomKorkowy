package graphics.gui;

import graphics.Engine;
import graphics.Input;
import graphics.Rectangle;
import logic.Entity;
import logic.Player;
import map.MapManager;
import map.json.JsonUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameplayContext extends Context {
    public static MapManager map;
    public Rectangle HEALTHBAR;
    public boolean refresh;
    static public Player KORKOWY;
    public static Map<Integer, Player> players;
    private final ClassPathXmlApplicationContext context;

    public GameplayContext() {
        context = new ClassPathXmlApplicationContext("application_context.xml");
        map = (MapManager) context.getBean("map");
        HEALTHBAR = new Rectangle(-1.0f, 0.9f, 0.18f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
        refresh = false;

    }

    @Override
    public void refreshContext() {
        KORKOWY = (Player) context.getBean("player");
        players = new HashMap<>();
        map.buildStages();

        // init map
        try {
            map.setStage(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // refresh
        if (refresh) {
            map = (MapManager) context.getBean("map");
            map.fromJson(JsonUtils.fromString(Engine.browser.browser.roomActive.lobby.map_context));
            refreshContext();

            for (DataField d : Engine.browser.browser.dataFields) {
                int id = d.getAsInteger();
                if (id != -1 && id != Engine.client.id)
                    players.put(id, new Player(id));
            }
            refresh = false;
            System.out.println("length: " + players.size());
            Engine.client.spawnMultiReader();
        }

        // update
        map.move();
        map.update();
        if (KORKOWY.getRectangle() == null) {
            System.out.println("jest null");
        }
        KORKOWY.move();
        KORKOWY.update();
        KORKOWY.doActions();
        for (Player p : players.values()) {
            p.doActions();
            p.updateWeapon();
        }

        Input.resetInputs();
    }

    @Override
    public void draw() {
        map.draw();
        for (Player p : players.values()) {
            if (Arrays.equals(p.getStage(), MapManager.getCurrentStageTab())) {
                p.draw();
            }
        }
        KORKOWY.draw();
        HEALTHBAR.draw();

        Engine.fontLoader.renderText("K:" + KORKOWY.getKills(), "msgothic.bmp",
                -0.7f, 0.87f, 0.06f, 0.1f, 0.0f, 0.0f, 0.0f, 1.0f);

        Engine.fontLoader.renderText("D:" + KORKOWY.getDeaths(), "msgothic.bmp",
                -0.37f, 0.87f, 0.06f, 0.1f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Collection<Entity> getPlatforms() {
        return map.getCurrentStage().getPlatforms().values();
    }

    public Collection<Entity> getMobs() {
        return map.getCurrentStage().getMobs().values();
    }

    public Collection<Entity> getObstacles() {
        return map.getCurrentStage().getObstacles().values();
    }

    public Collection<Entity> getDoors() {
        return map.getCurrentStage().getDoors().values();
    }

    public Collection<Player> getEnemies() {
        return players.values();
    }

    public float[] getStart() {
        return map.getCurrentStage().getStart();
    }
}
