package graphics.gui;

import graphics.Input;
import graphics.Rectangle;
import logic.Entity;
import logic.Player;
import map.MapManager;

import java.util.Collection;

public class GameplayContext extends Context {
    public MapManager map;
    Player KORKOWY;
    public Rectangle HEALTHBAR;

    public GameplayContext() {
        map = new MapManager();
        HEALTHBAR = new Rectangle(-1.0f, 0.9f, 0.18f, 0.08f);
        HEALTHBAR.initGL("3hp.png", "rectangle.vert.glsl", "rectangle.frag");
    }

    @Override
    public void refreshContext() {
        // init temp map
        try {
            map.nextStage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        KORKOWY = new Player(-0.7f, -0.8f, 0.08f, 0.18f, "korkowa_postac.png");
    }

    @Override
    public void update() {
        //    update
        map.move();
        map.update();
        KORKOWY.move();
        KORKOWY.update();
        Input.resetInputs();
    }

    @Override
    public void draw() {
        map.draw();
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

    public float[] getStart() { return map.getCurrentStage().start; }
}
