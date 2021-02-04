package logic;

import graphics.Engine;
import graphics.Input;
import graphics.gui.GameplayContext;
import map.Stage;

import java.util.ArrayList;

import static logic.CharacterState.JUMPING;
import static logic.CharacterState.STANDING;

public class Player extends Character {
    private final Weapon[] weapons;
    private Checkpoint checkpoint;
    private int activeWeapon;
    private int immune;
    public int stageX, stageY;
    private int kills;
    private int deaths;
    private int team;
    public final ArrayList<playerAction> actionList;

    public Player(int id) {
        this();
        this.id = id;
    }

    public Player(int id, int color, int team) {
        this();
        setAttributes(id, color, team);
    }

    public Player() {
        super(0.0f, 0.0f, 0.1f, 0.18f, "korkowy_ludek.png", RIGHT);
        float posX = -0.7f;
        float posY = -0.8f;
        float width = 0.1f;
        float height = 0.18f;

        // Trzy sloty na bronie, dwie przykładowe dodane od początku
        this.weapons = new MeleeWeapon[3];
        this.weapons[0] = new MeleeWeapon(this, 0, posX - width, posY - height / 2, 0.12f,
                0.13f, 0.08f, "sword1.png", 5, 8);
        this.weapons[1] = new MeleeWeapon(this, 1, posX - width, posY - height / 2, 0.075f,
                0.15f, 0.05f, "mace.png", 1, 4);
        this.activeWeapon = 0;
        init();
        this.hp = 3;
        actionList = new ArrayList<>();

        kills = 0;
        deaths = 0;
    }

    public void setAttributes(int id, int color, int team) {
        this.id = id;
        this.team = team;
        this.rectangle.setTexture("korkowy" + color + ".png");
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);
        this.rectangle.setOrientation(direction == RIGHT);
    }

    public void move() {
        float speed = 1;

        if (Input.L_CTRL) {
            speed = 1.5f;
        }
        if (Input.RIGHT) {
            vel_x = 0.01f * speed;
            direction = RIGHT;
        } else if (Input.LEFT) {
            vel_x = -0.01f * speed;
            direction = LEFT;
        }

        if (Input.SPACE && state == STANDING) {
            vel_y = 0.03f;
            if (Input.L_SHIFT) {
                vel_y *= 1.33f;
            }
            state = JUMPING;
        }

        this.gravity();

        if (state != STANDING && vel_y == 0) {
            state = STANDING;
        }
        if (immune < 1) {
            for (Entity mob : Engine.gameplay.getMobs()) {
                if (this.rectangle.collidesWith(mob.rectangle)) {
                    getDamage();
                }
            }
            for (Entity mob : Engine.gameplay.getObstacles()) {
                if (this.rectangle.collidesWith(mob.rectangle)) {
                    getDamage();
                }
            }
        }

        for (Entity entity : Engine.gameplay.getDoors()) {
            if (entity.isActive() && this.rectangle.collidesWith(entity.rectangle)) {
                Door door = (Door) entity;
                Door linked = (Door) GameplayContext.map.getDoor(door.linkedDoor);
                GameplayContext.map.setStage(linked.stageX, linked.stageY);
                moveTo(linked.rectangle.posX+linked.rectangle.width+0.05f, linked.rectangle.posY);
            }
        }

        this.rectangle.setOrientation(direction == RIGHT);
        this.rectangle.move(vel_x, vel_y);

        immune--;
        gravity_vel_dec();
        vel_x = 0.0f;

        if (rectangle.posX <= -1f - rectangle.width)
            reset();
        if (rectangle.posX >= 1f)
            reset();
    }

    @Override
    public void update() {
        if (Input.ATTACK) {
            weapons[activeWeapon].start();
            Engine.client.updateHit();
            Input.ATTACK = false;
        }
        if (Input.ONE_KEY) {
            this.setActiveWeapon(0);
            Input.ONE_KEY = false;
        } else if (Input.TWO_KEY) {
            this.setActiveWeapon(1);
            Input.TWO_KEY = false;
        }

        weapons[activeWeapon].update();
        weapons[activeWeapon].hitUpdate();
    }

    public void updateWeapon() {
        weapons[activeWeapon].update();
    }

    public void useWeapon() {
        weapons[activeWeapon].start();
    }

    public void doActions() {
        synchronized (actionList) {
            for (playerAction action : actionList) action.action();
            actionList.clear();
        }
    }

    public void addAction(playerAction action) {
        synchronized (actionList) {
            actionList.add(action);
        }
    }

    @Override
    public void draw() {
        super.draw();
        weapons[activeWeapon].draw();
    }

    public void setActiveWeapon(int index) {
        this.activeWeapon = index;
    }

    public int getActiveWeapon() {
        return this.activeWeapon;
    }

    public boolean getDamage() {
        hp--;
        immune = 30;
        if (hp == 0) {
            reset();
            return true;
        }
        Engine.gameplay.HEALTHBAR.setTexture(hp + "hp.png");
        vel_y *= 0.9f;
        vel_x *= 0.9f;
        return false;
    }

    public void getDamage(int id) {
        if (getDamage()) Engine.client.updateDeath(id);
    }

    public void heal(int x) {
        hp += x;
        if (hp > 3) {
            hp = 3;
        }
        Engine.gameplay.HEALTHBAR.setTexture(hp + "hp.png");
    }

    public void reset() {
        hp = 3;
        float[] start = Engine.gameplay.getStart();
        if (checkpoint != null) {
            GameplayContext.map.setStage(checkpoint.stageX, checkpoint.stageY);
            moveTo(checkpoint.rectangle.posX, checkpoint.rectangle.posY);
        } else {
            GameplayContext.map.setStage(0, 0);
            moveTo(start[0], start[1]);
            System.out.println(start[0] + ", " + start[1]);
        }
        Engine.gameplay.HEALTHBAR.setTexture(hp + "hp.png");
        state = JUMPING;
        immune = 0;
        vel_x = 0.0f;
        vel_y = 0.0f;
        deaths++;
    }

    public void collideCheckpoint(Checkpoint checkpoint) {
        if (rectangle.collidesWith(checkpoint.rectangle)) {
            setCheckpoint(checkpoint);
        }
    }

    public void setStage(int stageX, int stageY) {
        this.stageX = stageX;
        this.stageY = stageY;
    }

    public int[] getStage() {
        return new int[]{stageX, stageY};
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        if (this.checkpoint != null) {
            this.checkpoint.uncheck();
        }
        checkpoint.check();
        this.checkpoint = checkpoint;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void incKills() { this.kills++; }

    public boolean isInTeam(int team) { return this.team == team; }

    public int getTeam() { return this.team; }
}
