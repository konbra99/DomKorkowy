package graphics.gui;

import graphics.Config;
import graphics.Input;
import graphics.Rectangle;
import graphics.Window;
import logic.Entity;
import logic.Platform;
import map.MapManager;
import map.Stage;

import static org.lwjgl.glfw.GLFW.*;

class Attributes extends Context {
    Rectangle rectangle;

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

            editor.actClick = () -> {
                glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                editor.map.getCurrentStage().addMapEntity(editor.newElement);
                editor.newElement = null;
                editor.actClick = editor.actDefClick;
            };
            editor.state = state;

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

    CHOSEN state;
    MapManager map;
    ElementButton addPlatform, addObstacle, addMob;
    Entity newElement = null;
    Entity selected = null;
    Action actClick, actDefClick, actDrag;

    public EditorContext(MapManager map) {
        this.map = map;
        map.addStage(new Stage("background/sky.png", 0.0f, 0.0f));
        map.nextStage();
        addPlatform = new ElementButton(-0.95f, 0.35f, CHOSEN.PLATFORM, Button.PLAT, this);
        addObstacle = new ElementButton(-0.95f, -0.25f, CHOSEN.OBSTACLE, Button.OBS, this);
        addMob = new ElementButton(-0.95f, -0.85f, CHOSEN.MOB, Button.MOBS, this);

        this.actDefClick = () -> {
            for (Entity e : map.getCurrentStage().allMap) {
                if (e.getRectangle().hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
                    this.selected = e;
                    return;
                }
            }
            this.selected = null;
        };
        this.actClick = this.actDefClick;

        this.actDrag = () -> {
            glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            this.selected.moveTo(Input.MOUSE_POS_X, Input.MOUSE_POS_Y);
        };
    }

    @Override
    public void update() {
        addPlatform.update();
        addObstacle.update();
        addMob.update();

        if (newElement != null) {
            newElement.moveTo(Input.MOUSE_POS_X, Input.MOUSE_POS_Y);
        } else if (selected != null) {
            if (Input.DRAG) {
                actDrag.action();
            } else {
                glfwSetInputMode(Window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            }
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
        if (newElement != null) {
            newElement.draw();
        }
    }
}
