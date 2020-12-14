package graphics.gui;

import graphics.Engine;

class ElementButton extends Button {
    EditorContext editor;

    public ElementButton(EditorContext.CHOSEN state, String[] textures, EditorContext editor) {
        super(0.0f, 0.0f, 0.3f, 0.1f, null, textures);
        this.editor = editor;
        this.action = () -> {
            editor.state = state;
        };
    }
}

public class EditorContext extends Context {
    enum CHOSEN {
        SELECT,
        PLATFORM,
        OBSTACLE,
        MOB
    }

    CHOSEN state;

    ElementButton newElement = null;


    public EditorContext() {
        super("background/sky.png");
        super.init();
    }
}
