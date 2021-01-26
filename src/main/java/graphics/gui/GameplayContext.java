package graphics.gui;

import client.PlayerAspect;
import graphics.Engine;
import graphics.Input;
import graphics.Rectangle;
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
    public MapManager map;
    public Rectangle HEALTHBAR;
    public HealthPotionSmall HP_S;
    public boolean refresh;
    static public Player KORKOWY;
    public static Map<Integer, Player> players;

    public GameplayContext() {
        map = new MapManager();
        HEALTHBAR = new Rectangle(-1.0f, 0.9f, 0.18f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
        refresh = false;
    }

    @Override
    public void refreshContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application_context.xml");
        KORKOWY = (Player) context.getBean("player");
        players = new HashMap<>();

        // init map
        try {
            map.nextStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // refresh
        if (refresh) {
            map = new MapManager();
            map.fromJson(JsonUtils.fromString(Engine.browser.browser.roomActive.lobby.map_context));
            refreshContext();

            for (DataField d: Engine.browser.browser.dataFields) {
                int id = d.getAsInteger();
                if (id != Engine.client.id)
                    players.put(id, new Player());
            }
            refresh = false;
            Engine.client.spawnMultiReader();
        }

        // update
        map.move();
        map.update();
        KORKOWY.move();
        KORKOWY.update();
        for (Player p: players.values()) p.doActions();

        Input.resetInputs();
    }

    @Override
    public void draw() {
        map.draw();
        for(Player p: players.values())
            if (p.getStage() == MapManager.currentStage) p.draw();
        KORKOWY.draw();
        HEALTHBAR.draw();
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
}
