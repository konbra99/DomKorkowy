package graphics.gui;

import graphics.Config;
import graphics.Engine;
import graphics.Input;
import map.MapManager;


public class TextField extends DataField {
    public enum Type {NAME, AUTHOR, DESCRIPTION, TIME, DIFFICULTY}
    public MapManager map;
    MapBrowser browser;
    TextArea textArea;
    Type type;

    public TextField(MapBrowser browser, Type type, String [] textures) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
        this.browser = browser;
        this.type = type;

        textArea = new TextArea(0.44f, 0.07f, Config.MAP_BUTTON_WIDTH/2, Config.MAP_BUTTON_HEIGHT/2,
                0.05f, 0.12f, "msgothic.bmp",
                0.0f, 0.0f, 0.0f, 1.0f);
        this.action = () -> {
            this.is_invalid = false;
            textArea.getRectangle().setTexture("gui/cyan.png");
            this.browser.deselectAll();
            this.is_selected = true;
            this.textArea.Clear();
            Engine.active = textArea;
        };
    }

    public TextField(MapBrowser browser, String [] textures) {
        super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
        this.browser = browser;

        textArea = new TextArea(0.44f, 0.07f, Config.MAP_BUTTON_WIDTH/2, Config.MAP_BUTTON_HEIGHT/2,
                0.05f, 0.12f, "msgothic.bmp",
                0.0f, 0.0f, 0.0f, 1.0f);
        this.action = () -> {
            this.is_invalid = false;
            textArea.getRectangle().setTexture("gui/cyan.png");
            this.browser.deselectAll();
            this.is_selected = true;
            this.textArea.Clear();
            Engine.active = textArea;
        };
    }

    @Override
    public void update() {
        this.text_x = this.rectangle.posX + 0.1f;
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;

        if (is_active && this.rectangle.hasPoint(Input.MOUSE_X, Input.MOUSE_Y)) {
            this.action.action();
        }
        this.textArea.update();
    }

    @Override
    public void draw() {
        super.draw();
        this.textArea.draw();
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);
        textArea.getRectangle().move(dx, dy);
        this.text_x = this.rectangle.posX + 0.1f;
        this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;
    }

    public boolean getValue(MapManager map) {
        try {
            if (textArea.text.length() == 0)
                throw new Exception();

            switch (type) {
                case NAME -> map.mapName = textArea.text;
                case AUTHOR -> map.author = textArea.text;
                case DESCRIPTION -> map.description = textArea.text;
                case TIME -> map.time = Integer.parseInt(textArea.text);
                case DIFFICULTY -> map.difficulty = Float.parseFloat(textArea.text);
            }
            return true;
        } catch (Exception e) {
            is_invalid = true;
            textArea.getRectangle().setTexture("gui/red.png");
            return false;
        }
    }

    public Float getAsFloat() {
        // TODO
        return null;
    }

    public Integer getAsInteger() {
        // TODO
        return null;
    }

    @Override
    public Integer getAsPositiveInteger() {
        try {
            int i = Integer.parseInt(textArea.text);
            if (i <= 0) throw new Exception();
            return i;
        } catch (Exception e) {
            is_invalid = true;
            textArea.getRectangle().setTexture("gui/red.png");
            return null;
        }
    }

    @Override
    public String getAsString() {
        return textArea.text;
    }

    @Override
    public String getAsNonemptyString() {
        if (textArea.text.isEmpty()) {
            is_invalid = true;
            textArea.getRectangle().setTexture("gui/red.png");
            return null;
        }
        else
            return textArea.text;
    }
}
