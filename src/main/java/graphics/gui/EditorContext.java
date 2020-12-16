package graphics.gui;

import graphics.*;
import logic.Entity;
import logic.Platform;
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
    float x, y;

    public Attributes(float x, float width, Entity entity, EditorContext editor) {
        this.editor = editor;
        this.entity = entity;
        this.x = x;
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

        init();
    }

    @Override
    public void update() {
        editor.editingText = this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y);
        for (AttributeField attributeField : textFields) {
            attributeField.update();
        }
        apply.update();
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
        super(x, y, 0.3f, 0.3f * Config.RESOLUTION, null, textures);
        this.editor = editor;
        this.action = () -> {
            glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            switch (state) {
                case PLATFORM -> {
                    editor.newElement = new Platform(0.0f, 0.0f, 0.3f, 0.1f,
                            "platforms/platforma.png");
                }
                case OBSTACLE -> {
                }
                case MOB -> {
                }
            }
            editor.newElement.init();
            editor.attributes = new Attributes(0.4f, 0.6f, editor.newElement, editor);
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
        MOB
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
    ElementButton addPlatform, addObstacle, addMob;
    Button saveMap;
    Entity newElement = null;
    Entity selected = null;
    Action actClick, actDefClick, actDrag;
    Attributes attributes;
    boolean editingText = false;

    public EditorContext(MapManager map) {
        this.map = map;
        map.addStage(new Stage("background/sky.png", 0.0f, 0.0f));
        map.nextStage();
        addPlatform = new ElementButton(-0.95f, 0.35f, CHOSEN.PLATFORM, Button.PLAT, this);
        addObstacle = new ElementButton(-0.95f, -0.25f, CHOSEN.OBSTACLE, Button.OBS, this);
        addMob = new ElementButton(-0.95f, -0.85f, CHOSEN.MOB, Button.MOBS, this);
        state = EDITING_STATE.NONE;
        saveMap = new Button(0.7f, -1.0f, 0.25f, 0.5f, null, Button.RIGHT_ARROW);
        saveMap.setText("Zapisz", "msgothic.bmp", 0.03f, 0.08f);
        saveMap.action = () -> {
            try {
                map.getCurrentStage().buildHashMap();
                map.getCurrentStage().buildStage();
                JsonUtils.toFile(map.toJson(), Config.MAP_PATH + map.mapName + ".json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        this.actDefClick = () -> {
            for (Entity e : map.getCurrentStage().allMap) {
                if (e.getRectangle().hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
                    this.selected = e;
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
            this.selected.moveTo(Input.MOUSE_POS_X - xdrag_shift, Input.MOUSE_POS_Y - ydrag_shift);
            this.attributes.entity = this.selected;
        };
    }

    @Override
    public void update() {
        addPlatform.update();
        addObstacle.update();
        addMob.update();
        saveMap.update();

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
        saveMap.draw();

        if (state == EDITING_STATE.NEW) {
            newElement.draw();
        } else if (state == EDITING_STATE.NONE) {
            return;
        }

        attributes.draw();
    }
}
