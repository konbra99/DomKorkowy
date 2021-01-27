package graphics.gui;

import graphics.*;
import logic.*;
import map.MapManager;
import map.Stage;
import map.json.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

class AttributeField {
    String name;
    TextArea area;
    float x, y;

    public AttributeField(float x, float y, String name, Float value) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.area = new TextArea(x + 0.2f, y, 0.4f, 0.1f, 0.03f, 0.08f,
                "msgothic.bmp", 0.0f, 0.0f, 0.0f, 1.0f);
        this.area.text = value.toString();
    }

    public void update() {
        area.update();
    }

    public void draw() {
        Engine.fontLoader.renderText(name, "msgothic.bmp", x, y, 0.03f, 0.08f,
                0.5f, 1.0f, 0.6f, 1.0f);
        area.draw();
    }
}

class Attributes extends Context {
    Button apply, delete;
    Entity entity;
    ArrayList<AttributeField> textFields;
    EditorContext editor;
    Map<String, Float> attr;
    float x, y, width;

    public Attributes(float x, float width, Entity entity, EditorContext editor) {
        this.editor = editor;
        this.entity = entity;
        this.x = x;
        this.width = width;
    }

    @Override
    public void update() {
        editor.editingText = this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y);
        for (AttributeField attributeField : textFields) {
            attributeField.update();
        }
        apply.update();
        delete.update();
    }

    public void setEntity(Entity entity) {
        if (this.entity == entity) {
            return;
        }
        this.entity = entity;
        this.attr = entity.getAttributes();
        float h = (attr.size() + 2) * 0.1f;
        this.y = 1.0f - h;
        this.rectangle = new Rectangle(x, 1.0f - h, width, h);
        this.textureName = "gui/purple.png";
        this.textFields = new ArrayList<>();
        int i = 1;
        for (String name : attr.keySet()) {
            textFields.add(new AttributeField(x + 0.05f, 1.0f - 0.1f * i, name, attr.get(name)));
            ++i;
        }

        ++i;
        apply = new Button(x + 0.02f, 1.0f - 0.1f * i, 0.3f, 0.1f, null, Button.SHORT_BUTTON);
        apply.setText("Zastosuj", "msgothic.bmp", 0.03f, 0.08f);
        apply.action = () -> {
            attr.clear();
            for (AttributeField attributeField : textFields) {
                try {
                    attr.put(attributeField.name, Float.parseFloat(attributeField.area.text));
                } catch (NumberFormatException e) {
                    attr.clear();
                }
            }
            editor.selected.setAttributes(attr);
            refresh();
        };

        delete = new Button(x + 0.33f, 1.0f - 0.1f * i, 0.25f, 0.1f, null, Button.SHORT_BUTTON);
        delete.setText("Usun", "msgothic.bmp", 0.03f, 0.08f);
        delete.action = () -> {
            editor.map.getCurrentStage().removeMapEntity(editor.selected);
            editor.state = EditorContext.EDITING_STATE.NONE;
        };
        init();
        System.out.println("po inicie");
    }

    public void refresh() {
        attr = entity.getAttributes();
        for (AttributeField attributeField : textFields) {
            attributeField.area.text = attr.get(attributeField.name).toString();
        }
        delete.update();
    }

    @Override
    public void draw() {
        this.rectangle.setAlpha(0.7f);
        super.draw();
        for (AttributeField attributeField : textFields) {
            attributeField.draw();
        }
        apply.draw();
        delete.draw();
    }
}

class ElementButton extends Button {
    EditorContext editor;

    public ElementButton(float x, float y, EditorContext.CHOSEN state, String[] textures, EditorContext editor) {
        super(x, y, 0.2f, 0.2f * Config.RESOLUTION, null, textures);
        this.editor = editor;
        this.action = () -> {
            glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            switch (state) {
                case PLATFORM -> {
                    editor.newElement = new Platform(0.0f, 0.0f, 0.3f, 0.1f,
                            "platforms/platforma.png");
                }
                case OBSTACLE -> {
                    editor.newElement = new Obstacle(0.0f, 0.0f, 0.1f, 0.1f,
                            "obstacles/spikes_2.png");
                }
                case MOB -> {
                    editor.newElement = new Mob(0.0f, 0.0f, 0.1f, 0.2f, "mobs/enemy1.png",
                            0.0f, 0.0f, 0.0f, -1);
                }
                case DOOR -> {
                    editor.newElement = new Door(0.0f, 0.0f, 0.1f, 0.2f,
                            "door.png", 0, true);
                }
            }
            editor.newElement.init();
            editor.attributes.setEntity(editor.newElement);
            editor.state = EditorContext.EDITING_STATE.NEW;

            editor.actClick = () -> {
                glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                editor.map.getCurrentStage().addMapEntity(editor.newElement);
                editor.state = EditorContext.EDITING_STATE.NONE;
                editor.actClick = editor.actDefClick;
            };

            Input.MOUSE_X = -2.0f;
            Input.MOUSE_Y = -2.0f;
        };
    }
}

public class EditorContext extends Context {
    enum CHOSEN {
        PLATFORM,
        OBSTACLE,
        MOB,
        DOOR
    }

    enum EDITING_STATE {
        NEW,
        DRAGGING,
        EDITING,
        NONE
    }

    EDITING_STATE state;
    private float xdrag_shift, ydrag_shift;
    MapManager map;
    ElementButton addPlatform, addObstacle, addMob, addDoor;
    Button saveMap, backButton;
    Button prevStageButton, nextStageButton;
    Entity newElement = null;
    Entity selected = null;
    Action actClick, actDefClick, actDrag;
    Attributes attributes;
    boolean editingText = false;

    public EditorContext(MapManager map) {
        this.map = map;
        if (!map.nextStage()) {
            System.out.println("dodaje nowy");
            map.addStage(new Stage("background/sky.png", 0.0f, 0.0f));
            map.nextStage();
        } else {
            map.getCurrentStage().buildAllMap();
        }

        addPlatform = new ElementButton(-0.99f, 0.62f, CHOSEN.PLATFORM, Button.PLAT, this);
        addObstacle = new ElementButton(-0.99f, 0.24f, CHOSEN.OBSTACLE, Button.OBS, this);
        addMob = new ElementButton(-0.99f, -0.14f, CHOSEN.MOB, Button.MOBS, this);
        addDoor = new ElementButton(-0.99f, -0.52f, CHOSEN.DOOR, Button.DOORS, this);
        attributes = new Attributes(0.4f, 0.6f, null, this);

        state = EDITING_STATE.NONE;
        saveMap = new Button(0.74f, -1.0f, 0.25f, 0.2f, null, Button.SHORT_BUTTON);
        saveMap.setText("Zapisz", "msgothic.bmp", 0.03f, 0.08f);
        saveMap.action = () -> {
            try {
                map.getCurrentStage().buildHashMap();
                map.getCurrentStage().buildStage();
                JsonUtils.toFile(map.toJson(), Config.MAP_PATH + map.mapName + ".json");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        backButton = new Button(-0.99f, -1.0f, 0.2f, 0.3f, null, Button.LEFT_ARROW);
        backButton.setText("BACK", "msgothic.bmp", 0.03f, 0.08f);
        backButton.action = () -> {
            Engine.activeContext = Engine.menu;
            Engine.STATE = Engine.GAME_STATE.MENU;
        };

        prevStageButton = new Button(0.74f, -0.79f, 0.12f, 0.17f, null, Button.LEFT_ARROW);
        prevStageButton.action = () -> {

        };

        nextStageButton = new Button(0.87f, -0.79f, 0.12f, 0.17f, null, Button.RIGHT_ARROW);
        nextStageButton.setText("", "msgothic.bmp", 0.06f, 0.15f);
        nextStageButton.action = () -> {
            this.map.nextStage();
        };

        this.actDefClick = () -> {
            for (Entity e : map.getCurrentStage().allMap) {
                if (e.getRectangle().hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
                    this.selected = e;
                    attributes.setEntity(e);
                    this.xdrag_shift = Input.MOUSE_X - e.getRectangle().posX;
                    this.ydrag_shift = Input.MOUSE_Y - e.getRectangle().posY;
                    this.state = EDITING_STATE.DRAGGING;
                    return;
                }
            }

            if (!editingText) {
                this.state = EDITING_STATE.NONE;
            }
        };
        this.actClick = this.actDefClick;

        this.actDrag = () -> {
            glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            this.attributes.setEntity(this.selected);
            this.selected.moveTo(Input.MOUSE_POS_X - xdrag_shift, Input.MOUSE_POS_Y - ydrag_shift);
        };
    }

    @Override
    public void update() {
        addPlatform.update();
        addObstacle.update();
        addMob.update();
        addDoor.update();
        saveMap.update();
        backButton.update();
        prevStageButton.update();
        if (map.hasNext()) {
            nextStageButton.setTextures(Button.RIGHT_ARROW);
            nextStageButton.text = "";
            map.nextStage();
        } else {
            nextStageButton.setTextures(Button.SHORT_BUTTON);
            nextStageButton.text = " +";
            nextStageButton.action = () -> {
                map.addStage(new Stage("background/sky.png", 0.0f, 0.0f));
            };
        }
        nextStageButton.update();

        switch (state) {
            case NEW -> {
                newElement.moveTo(Input.MOUSE_POS_X, Input.MOUSE_POS_Y);
                attributes.refresh();
                attributes.update();
            }
            case DRAGGING -> {
                actDrag.action();
                if (!Input.DRAG) {
                    this.state = EDITING_STATE.EDITING;
                    glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                }
                attributes.refresh();
                attributes.update();
            }
            case EDITING -> attributes.update();
        }

        if (Input.MOUSE_X != -2.0f && Input.MOUSE_Y != -2.0f) {
            actClick.action();
        }
    }

    @Override
    public void draw() {
        map.getCurrentStage().drawMap();
        addPlatform.draw();
        addObstacle.draw();
        addMob.draw();
        addDoor.draw();
        saveMap.draw();
        backButton.draw();
        prevStageButton.draw();
        nextStageButton.draw();

        if (state == EDITING_STATE.NEW) {
            newElement.draw();
        } else if (state == EDITING_STATE.NONE) {
            return;
        }

        attributes.draw();
    }
}
