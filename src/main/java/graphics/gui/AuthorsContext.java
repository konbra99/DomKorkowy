package graphics.gui;

import graphics.Engine;

public class AuthorsContext extends Context {

    protected Button backButton;

    public AuthorsContext() {
        super("background/authors.png");

        backButton = new Button(-0.95f, -0.95f, 0.20f, 0.4f, null, Button.RIGHT_ARROW);
        backButton.setText("BACK", "msgothic.bmp", 0.04f, 0.1f);
        backButton.action = () -> {
            Engine.activeContext = Engine.menu;
            Engine.STATE = Engine.GAME_STATE.MENU;
        };
        super.init();
    }

    @Override
    public void draw() {
        super.draw();
        backButton.draw();
    }

    @Override
    public void update() {
        super.update();
        backButton.update();
    }
}
